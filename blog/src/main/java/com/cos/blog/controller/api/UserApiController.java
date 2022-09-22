package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController : save 호출됨 ");
		userService.join(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user)
	{
		userService.update(user);
		// 여기서 트랜잭션이 종료되기 때문에 DB 값은 변경이 되었음
		// 하지만 세션값은 변경되지 않은 상태이기 때문에 직접 세션 값을 변경해준다 
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	
	/* 파라미터로 들어가 있는 HttpSession 을 Autowired 로 빼도 됨 
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){
		System.out.println("UserApiController : login 호출됨 ");
		User principal = userService.login(user);		// principal :  접근 주체 
		if(principal != null) {
			session.setAttribute("principal", principal);
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	*/
	
}
