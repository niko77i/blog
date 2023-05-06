package com.niko.blog.entiy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * shiro认证登录后保存的用户信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountVo implements Serializable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;
    /**
     * 邮件
     */
    private String email;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 签名
     */
    private String sign;
    /**
     * 性别
     */
    private String gender;
    /**
     * 创建日期
     */
    private Date created;

    public String getSex() {
        return "0".equals(gender) ? "女" : "男";
    }

}
