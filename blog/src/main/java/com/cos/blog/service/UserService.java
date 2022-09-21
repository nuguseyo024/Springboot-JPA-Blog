package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean 에 등록을 해줌 == IoC 를 해준다 
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void join(User user) {
		String rawPassword = user.getPassword();  // 기본 비밀번호 
		String encPassword = encoder.encode(rawPassword); // 해시로 변환된 비밀번호 
		user.setRole(RoleType.USER);
		user.setPassword(encPassword);
		userRepository.save(user);
		
	}

	@Transactional
	public void update(User user) {
		// 수정 시에는 JPA 영속성 컨텍스트에 오브젝트를 영속화 시키고, 
		// 영속화된 오브젝트를 수정하는 방법이 가장 좋다 
		// 영속화 하기 위해 select 해서 오브젝트를 DB 로부터 가져온다 
		// 영속화된 오브젝트를 변경하면 자동으로 DB 에 update 문을 날려주기 때문 
		User persistance = userRepository.findById(user.getId()).
				orElseThrow(()->{
					return new IllegalArgumentException("회원 찾기 실패 ");
				});
		String rawPassword = user.getPassword();
		// 영속화된 유저 오브젝트에서 비밀번호를 가져온다 
		String encPassword = encoder.encode(rawPassword);
		// autowired 된 encoder로 초기 비밀번호를 암호화해준다 
		persistance.setPassword(encPassword);
		// 암호화된 비밀번호로 세팅해준다 
		persistance.setEmail(user.getEmail());
		
	} 	// 회원 수정 함수 종료 == 서비스 종료 == 트랜잭션 종료 == commit 
		// 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 변화된 내용을 update 시켜준다 


	/* 
	@Transactional(readOnly = true) // select 할 때 트랜잭션 시작, 서비스 종료 시 트랜잭션 종료 - 되는 동안 정합성 유지되게 해줌  
	public User login(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	*/
}
