package com.cos.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

// 인증이 안 된 사용자들이 출입할 수 있는 경로를 /auth ~ , / (index.jsp) 만  허용 
@Controller
public class UserController {
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "/user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "/user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "/user/updateForm";
	}

	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code) { 
		// responseBody : data를 리턴해주는 컨트롤러 함수 
		
		// POST 방식으로 key=value 데이터를 요청 (카카오 쪽으로)
		RestTemplate rt = new RestTemplate();	// (비슷) HttpUrlConnection
		
		// HttpHeader 오브젝트 생성 
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 오브젝트 생성 
		MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
		params.add("grant_type","authorization_code");
		params.add("client_id","9cd43aebd3b402e6ba1f1c2ec881a966" );
		params.add("redirect_uri","http://localhost:8000/auth/kakao/callback" );
		params.add("code",code );
		// value 값에 들어간 애들은 변수로 따로 만들어서 사용하는 것이 좋다 
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기 
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
				new HttpEntity<>(params,headers);
		// HttpEntity 는 header 값을 가지고 있는 Entity 가 된다 
		
		// Http 요청하기 - POST 방식으로, response 변수의 응답을 받음 
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
				);
		
		
		return "카카오 토큰 요청 완료  " + response;
	}
}
