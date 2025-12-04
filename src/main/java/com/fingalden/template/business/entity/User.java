package com.fingalden.template.business.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * 用户实体类，实现了UserDetails接口，用于Spring Security认证
 */
@Data
@EqualsAndHashCode
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户角色
     */
    private String role;

    /**
     * 用户状态：0-禁用，1-启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 获取用户权限列表
     * Spring Security需要此方法来获取用户的权限信息
     *
     * @return 用户权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将用户角色转换为Spring Security的GrantedAuthority
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    /**
     * 账户是否过期
     *
     * @return 总是返回true，表示账户永不过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否被锁定
     *
     * @return 总是返回true，表示账户永不锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭证是否过期
     *
     * @return 总是返回true，表示凭证永不过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否可用
     * 根据用户状态字段判断，1表示可用
     *
     * @return 账户是否可用
     */
    @Override
    public boolean isEnabled() {
        return status == 1;
    }
}