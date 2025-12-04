package com.fingalden.template.business.repository;

import com.fingalden.template.business.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户数据访问接口，用于数据库操作
 * 继承JpaRepository，提供基本的CRUD操作
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名查询用户
     * Spring Data JPA会根据方法名自动生成SQL查询
     *
     * @param username 用户名
     * @return 用户对象，如果不存在则返回null
     */
    User findByUsername(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象，如果不存在则返回null
     */
    User findByEmail(String email);

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户对象，如果不存在则返回null
     */
    User findByPhone(String phone);
}