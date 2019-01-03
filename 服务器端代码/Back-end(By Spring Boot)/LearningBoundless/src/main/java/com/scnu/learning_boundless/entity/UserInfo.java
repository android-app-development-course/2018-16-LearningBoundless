package com.scnu.learning_boundless.entity;

/*
 * @program: LearningBoundless
 * @description:
 * @author: WuchangI
 * @create: 2018-12-26-22-19
 **/


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * 用户信息实体类
 */

@Table(name = "t_user")
@Entity
public class UserInfo {

    /**
     * 用户名
     */
    @Id
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 用户头像
     */
    @Column(columnDefinition = "mediumtext")
    private String avatar;


    private Integer level;

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
