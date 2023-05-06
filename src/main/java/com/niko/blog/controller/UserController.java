package com.niko.blog.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.niko.blog.common.lang.Result;
import com.niko.blog.entiy.pojo.Post;
import com.niko.blog.entiy.pojo.User;
import com.niko.blog.entiy.pojo.UserCollection;
import com.niko.blog.entiy.pojo.UserMessage;
import com.niko.blog.entiy.vo.AccountVo;
import com.niko.blog.entiy.vo.AskMess;
import com.niko.blog.entiy.vo.PublicTotalAndCollectionTotal;
import com.niko.blog.util.UploadUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UserController extends BaseController{

    @Autowired
    private UploadUtil uploadUtil;


    @GetMapping("/user/home")
    public String home(){

        User user = userService.getById(getAccountVoId());
        List<Post> posts = postService.list(new LambdaQueryWrapper<Post>()
                .eq(Post::getUserId,getAccountVoId())
                //作者30天内的文章
                .gt(Post::getCreated,DateUtil.offsetDay(new Date(),-30))
                .orderByDesc(Post::getCreated)
        );

        //相关回答
        List<AskMess> askMessList = commentService.getAskMess(getAccountVoId());

        req.setAttribute("user",user);
        req.setAttribute("posts",posts);
        req.setAttribute("askMessList",askMessList);

        return "/user/home";
    }

    @GetMapping("/user/set")
    public String set(){
        User user = userService.getById(getAccountVoId());
        req.setAttribute("user",user);
        return "/user/set";
    }

    @ResponseBody
    @PostMapping("/user/set")
    public Result doSet(User user){

        //更新头像
        if (StrUtil.isNotBlank(user.getAvatar())){
            User temp = userService.getById(getAccountVoId());
            temp.setAvatar(user.getAvatar());
            userService.updateById(temp);

            AccountVo accountVo = getAccountVo();
            accountVo.setAvatar(user.getAvatar());

            //更新session缓存
            SecurityUtils.getSubject().getSession().setAttribute("accountVo",accountVo);

            return Result.success().action("/user/set#avatar");

        }

        if (StrUtil.isBlank(user.getUsername())){
            return Result.fail("昵称不能为空");
        }

        //更新用户信息
        User temp = userService.getById(getAccountVoId());
        temp.setUsername(user.getUsername());
        temp.setGender(user.getGender());
        temp.setSign(user.getSign());
        boolean b = userService.updateById(temp);
        if (!b){
            return Result.fail("更新失败");
        }

        //同步shiro中保存的用户信息
        AccountVo accountVo = getAccountVo();
        accountVo.setUsername(temp.getUsername());
        accountVo.setSign(temp.getSign());

        SecurityUtils.getSubject().getSession().setAttribute("accountVo",accountVo);

        return Result.success().action("/user/set#info");
    }

    @ResponseBody
    @PostMapping("/user/upload")
    public Result uploadAvatar(@RequestParam("file") MultipartFile file){
        try {
            return uploadUtil.upload(UploadUtil.type_avatar,file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseBody
    @PostMapping("/user/repass")
    public Result repass(String nowpass, String pass, String repass) {
        if(!pass.equals(repass)) {
            return Result.fail("两次密码不相同");
        }

        User user = userService.getById(getAccountVoId());

        String nowPassMd5 = SecureUtil.md5(nowpass);
        if(!nowPassMd5.equals(user.getPassword())) {
            return Result.fail("原密码不正确");
        }

        user.setPassword(SecureUtil.md5(pass));
        userService.updateById(user);

        return Result.success("密码更新成功").action("/user/set#pass");

    }

    @GetMapping("/user/index")
    public String index(){
        User user = userService.getById(getAccountVoId());
        req.setAttribute("user",user);
        return "/user/index";
    }

    /**
     *  发表的文章
     */
    @ResponseBody
    @GetMapping("/user/public")
    public Result userPublic(){
        IPage<Post> page = postService.page(getPage(), new LambdaQueryWrapper<Post>()
                .eq(Post::getUserId, getAccountVoId())
                .orderByDesc(Post::getCreated));
        return Result.success(page);
    }
    /**
     *  发表的文章总数和收藏的文章总数
     */
    @ResponseBody
    @GetMapping("/user/publicTotalAndCollectionTotal")
    public Result publicTotalAndCollectionTotal(){
        long publicCount = postService.count(new LambdaQueryWrapper<Post>().eq(Post::getUserId, getAccountVoId()));
        long collectionCount = userCollectionService.count(new LambdaQueryWrapper<UserCollection>().eq(UserCollection::getUserId, getAccountVoId()));
        PublicTotalAndCollectionTotal publicTotalAndCollectionTotal = new PublicTotalAndCollectionTotal(publicCount, collectionCount);
        return Result.success(publicTotalAndCollectionTotal);
    }

    /**
     * 收藏的文章
     * @return
     */
    @ResponseBody
    @GetMapping("/user/collection")
    public Result collection(){
        IPage<Post> page = postService.page(getPage(), new LambdaQueryWrapper<Post>()
                .inSql(Post::getId, "select post_id from user_collection where user_id = " + getAccountVoId())
        );

        return Result.success(page);
    }


    @GetMapping("/user/message")
    public String message(){

        IPage page = userMessageService.pageing(getPage(), new LambdaQueryWrapper<UserMessage>()
                .eq(UserMessage::getToUserId, getAccountVoId())
                .orderByAsc(UserMessage::getStatus)
                .orderByDesc(UserMessage::getModified)
        );
        req.setAttribute("pageData",page);
        return "/user/message";
    }

    /**
     * 删除消息
     * @param messId
     * @param allRemove
     * @return
     */
    @ResponseBody
    @PostMapping("/message/remove")
    public Result mesRemove(@RequestParam(value = "id") Long messId,
                            @RequestParam(defaultValue = "false") Boolean allRemove){

        boolean remove = userMessageService.remove(new LambdaQueryWrapper<UserMessage>()
                //匹配登录人的id
                .eq(UserMessage::getToUserId, getAccountVoId())
                //allRemove为true表示全部删除
                .eq(!allRemove, UserMessage::getId, messId)
        );
        userMessageService.del(messId,allRemove);

        return remove? Result.success() : Result.fail("删除失败");
    }

    /**
     * 提示未读消息
     * @return
     */
    @ResponseBody
    @RequestMapping("/message/nums")
    public Map mesNums(){

        int count = (int) userMessageService.count(new LambdaQueryWrapper<UserMessage>()
                .eq(UserMessage::getToUserId, getAccountVoId())
                .eq(UserMessage::getStatus,"0")
        );

        return MapUtil.builder("status",0)
                .put("count",count).build();
    }

    /**
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/message/read")
    public Map readMess(){

        return MapUtil.builder("status",0).build();
    }

    /**
     * 标记未读消息为已读
     * @param messId
     * @return
     */
    @ResponseBody
    @GetMapping("/message/readDown")
    public Result readDown(@RequestParam(value = "id") Long messId){
        UserMessage userMessage = userMessageService.getById(messId);
        userMessage.setStatus(1);
        userMessage.setModified(new Date());
        boolean b = userMessageService.updateById(userMessage);
        return b? Result.success():Result.fail("请重试");
    }
}
