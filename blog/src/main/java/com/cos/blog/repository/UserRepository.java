package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

// @Repository // 생략 가능함 
public interface UserRepository extends JpaRepository<User, Integer>{

}
