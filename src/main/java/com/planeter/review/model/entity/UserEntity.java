package com.planeter.review.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 用户实体
 * MySQL
 */
@Data
@Entity
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String icon;

    public UserEntity() {
    }

    public UserEntity(Long id, String username, String password, String nickname, String icon) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.icon = icon;
    }
}
