package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // 스프링부트가 실행될 때 user클래스를 읽어서 자동으로 mysql에 테이블이 생성된다
// @DynamicInsert  // insert 시에 null 인 필드를 제외시켜준다 
public class User {

	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다
	private int id; // 시퀀스(오라클), auto_increment(mysql)

	@Column(nullable = false, length = 30)
	private String username; // id

	@Column(nullable = false, length = 100)
	// 비밀번호를 해쉬값으로 암호화할 것이기 때문에 100으로 넉넉하게 잡아둔다
	private String password;

	@Column(nullable = false, length = 50)
	private String email;

	// @ColumnDefault("'user'")
	// DB 는 RoleType 이라는게 없기 때문에 얘가 String 임을 알려줘야함
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다
							// Enum을 쓰면 도메인을 지정할 수 있음

	@CreationTimestamp // 시간이 자동으로 입력되는 어노테이션
	private Timestamp createDate;
}
