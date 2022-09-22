package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OauthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 인증이 안 된 사용자들이 출입할 수 있는 경로를 /auth ~ , / (index.jsp) 만  허용 
@Controller
public class UserController {

	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
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
	public String kakaoCallback(String code) {
		// responseBody : data를 리턴해주는 컨트롤러 함수

		// POST 방식으로 key=value 데이터를 요청 (카카오 쪽으로)
		RestTemplate rt = new RestTemplate(); // (비슷) HttpUrlConnection

		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "9cd43aebd3b402e6ba1f1c2ec881a966");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		// value 값에 들어간 애들은 변수로 따로 만들어서 사용하는 것이 좋다

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		// HttpEntity 는 header 값을 가지고 있는 Entity 가 된다

		// Http 요청하기 - POST 방식으로, response 변수의 응답을 받음
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token", 
				HttpMethod.POST,
				kakaoTokenRequest, 
				String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		OauthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OauthToken.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("카카오 액세스 토큰 : " + oauthToken.getAccess_token());
		// ----------------------------------------------------

		RestTemplate rt2 = new RestTemplate(); 

		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization","Bearer "+oauthToken.getAccess_token()); 	
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 
					= new HttpEntity<>(headers2);

		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me", 
				HttpMethod.POST,
				kakaoProfileRequest2, 
				String.class);
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 아이디(번호) : "+ kakaoProfile.getId());
		System.out.println("카카오 email : "+ kakaoProfile.getKakao_account().email);
		
		
		System.out.println("블로그 서버 유저네임 : "+ kakaoProfile.getKakao_account().email+"_"+kakaoProfile.getId());
		System.out.println("블로그 서버 email : "+ kakaoProfile.getKakao_account().email);
		//UUID garbagePassword = UUID.randomUUID();		// 랜덤으로 비밀번호 지급해서 넣어준다 
		// UUID란, 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘 
		System.out.println("블로그 서버 password : "+ cosKey);
		

		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().email+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().email)
				.oauth("kakao")
				.build();
		
		// 회원가입 된 사람인지 아닌지 체크해서 회원가입 
		User originUser = userService.findUser(kakaoUser.getUsername());
		if(originUser.getUsername() == null) {
			System.out.println("기존 회원이 아닙니다. 자동 회원가입을 진행합니다. ");
			userService.join(kakaoUser);
		}
		System.out.println("자동 로그인을 진행합니다.  ");
		// 로그인 처리 
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}
}
