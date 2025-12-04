package com.fingalden.template.business.service.impl;

import com.fingalden.template.business.entity.User;
import com.fingalden.template.business.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService实现类，用于从数据库加载用户信息
 * Spring Security会调用此服务来获取用户详情进行认证
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 用户数据访问接口
     */
    private final UserRepository userRepository;

    /**
     * 构造函数注入UserRepository
     *
     * @param userRepository 用户数据访问接口
     */
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 根据用户名加载用户详情
     * Spring Security会调用此方法来获取用户信息进行认证
     *
     * @param username 用户名
     * @return UserDetails对象，包含用户的用户名、密码、权限等信息
     * @throws UsernameNotFoundException 如果用户名不存在，则抛出此异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中查询用户
        User user = userRepository.findByUsername(username);
        
        // 如果用户不存在，抛出UsernameNotFoundException异常
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        // 返回User对象，它实现了UserDetails接口
        return user;
    }
}