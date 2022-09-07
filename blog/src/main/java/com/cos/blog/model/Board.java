package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	@Column(nullable = false, length=100)
	private String title;
	
	@Lob // 대용량 데이터 
	private String content; // 썸머노트 라이브러리 사용 - <html> 태그 섞여서 디자인 됨 
	
	@ColumnDefault("0")
	private int count;
	
	@ManyToOne  // Many = Board, User = One
	@JoinColumn(name="userId")
	private User user; // DB는 오브젝트를 저장할 수 없음, 자바는 오브젝트를 저장할 수 있다 
		
	@CreationTimestamp
	private Timestamp createDate;
}
