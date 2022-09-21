package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean 에 등록을 해줌 == IoC 를 해준다 
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Transactional
	public void write(Board board,User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly=true) // select 만 하니까 readOnly=true
	public Page<Board> boardList(Pageable pageable) {
		// BoardRepository는 비어있지만 상속받은 JpaRepository 가 기능을 다 들고 있기 때문에
		// findAll 함수를 사용할 수 있다 
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly=true)
	public Board boardRead(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 글 번호를 찾을 수 없습니다.");
				});
		
	}

	@Transactional
	public void delete(int id) {
		boardRepository.deleteById(id);		
	}

}
