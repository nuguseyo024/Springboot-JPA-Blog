package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Data;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면
// UserDetails 타입의 오브젝트를 스프링 시큐리티의 고유한 세션 저장소에 저장 해준다 
// 그 때, UserDetails 타입의 PrincipalDetail 이 저장된다 
@Data
public class PrincipalDetail implements UserDetails {

	private User user; // 객체를 품고 있음 :: 컴포지션

	public PrincipalDetail(User user) {
		this.user = user;
	}
	@Override
	public String getPassword() {

		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정이 만료되지 않았는지 리턴 (true: 만료 안됨)
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정이 잠겼는지 아닌지 리턴 (true: 잠기지 않음 )
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 비밀번호가 만료되지 않았는지 리턴 (true: 만료 안됨)
				return true;
	}

	@Override
	public boolean isEnabled() {
		// 계정 활성화(사용가능)인지 리턴 (true: 활성화)
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 계정의 권한을 리턴, GrantedAuthority를 상속한 컬렉션 타입을 리턴한다 
		Collection<GrantedAuthority> collectors = new ArrayList<>();

		collectors.add(()->{
			return "ROLE_"+user.getRole();
		});
		
		return collectors;
	}
	
	
	
}
