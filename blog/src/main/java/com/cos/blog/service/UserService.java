package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean 에 등록을 해줌 == IoC 를 해준다 
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public int join(User user) {
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService : join() : " + e.getMessage());
		}
		return -1;

	}


	/* 
	@Transactional(readOnly = true) // select 할 때 트랜잭션 시작, 서비스 종료 시 트랜잭션 종료 - 되는 동안 정합성 유지되게 해줌  
	public User login(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	*/
}
