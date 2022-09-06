package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답 (HTML 파일)
// :: @Controller
// 사용자가 요청 -> 응답 (Data)
@RestController
public class HttpControllerTest {
	
	public static final String TAG = "HttpControllerTest : ";
	
	@GetMapping("/http/lombok")
	public String lombotTest() {
		Member m = Member.builder().username("ssar").password("1234").email("ssar@naver.com").build();
		
		System.out.println(TAG+"getter : " + m.getUsername());
		m.setUsername("cos");
		System.out.println(TAG+"getter : " + m.getUsername());
		return "lombok test 완료 ";
	}
	
	// http:localhost:8080/http/get
	@GetMapping("/http/get")
	public String getTest(Member m) {
		System.out.println(TAG+"getter : " + m.getId());
		
		return "get 요청 : "+m.getId()+", username : " + m.getUsername()+", password : " + m.getPassword();
		// 인터넷 브라우저 요청은 get 요청만 할 수 있다 (링크를 통해서 들어가는 건 오직 get!)
	}
	
	// http:localhost:8080/http/post
	@PostMapping("/http/post") 
	public String postTest(@RequestBody Member m) {
		return "post 요청 : " +m.getId()+", username : " + m.getUsername()+", password : " + m.getPassword();
	}
	
	// http:localhost:8080/http/put
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청"  +m.getId()+", username : " + m.getUsername()+", password : " + m.getPassword();
	}
	
	// http:localhost:8080/http/delete
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
}
