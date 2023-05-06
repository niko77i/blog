<#include "/inc/layout.ftl" />
<@layout "我的消息">
  <div class="layui-container fly-marginTop fly-user-main">
    <@centerLeft level=3>

    </@centerLeft>
    <div class="site-tree-mobile layui-hide">
      <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>

    <div class="site-tree-mobile layui-hide">
      <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>


    <div class="fly-panel fly-panel-user" pad20>
      <div class="layui-tab layui-tab-brief" lay-filter="user" id="LAY_msg" style="margin-top: 15px;">
        <button class="layui-btn layui-btn-danger" id="LAY_delallmsg">清空全部消息</button>
        <div  id="LAY_minemsg" style="margin-top: 10px;">
          <!--<div class="fly-none">您暂时没有最新消息</div>-->
          <ul class="mine-msg">
            <#list pageData.records as mess>
              <li data-id=${mess.id} >
                <blockquote class="layui-elem-quote" onclick="turn(${mess.id})">
                  <#if mess.type == 0>
                    ${mess.content}
                  </#if>
                  <#if mess.type == 1>
                    ${mess.fromUserName} 评论了你的文章< <a href="/post/${mess.postId}" target="_blank">${mess.postTitle}</a> >，内容是 (${mess.content})
                  </#if>
                  <#if mess.type == 2>
                    ${mess.fromUserName} 回复了你的评论(${mess.myContent})
                    <br>
                    ${mess.fromUserName} 的回复是(${mess.content})
                    <br>
                    文章是< <a href="/post/${mess.postId}" target="_blank">${mess.postTitle}</a> >
                  </#if>
                </blockquote>
                <p><span id="readOrNotRead">${timeAgo(mess.created)}&nbsp;<#if mess.status==0>未读<#else >已读</#if></span><a href="javascript:;" class="layui-btn layui-btn-small layui-btn-danger fly-delete">删除</a></p>
              </li>
            </#list>
          </ul>
          <@pageing pageData></@pageing>
        </div>
      </div>
    </div>

  </div>

  <script>
    layui.cache.page = 'user';
    function turn(id){
      var texts = $('#readOrNotRead').text();
      var index=texts.indexOf("未读");
      var read = texts.substring(index,texts.length);
      if (read === "未读"){
        $.ajax({
          type:'get',
          url:"/message/readDown?id="+id,
          success:function (res) {
            if (res.status === 0){
              location.href="/user/message";
            }
          }
        });
      }

    }

  </script>

</@layout>
