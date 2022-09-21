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

	@Transactional
	public void update(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다. ");
				}); // 영속화 완료 
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수 종료 시 (서비스단에서 서비스가 종료될 때)
		// 트랜잭션이 종료된다 -> 이 때, 더티체킹이 일어남 (영속화 된 데이터가 달라졌기 때문에)
		// -> 자동 업데이트가 됨 (db flush 가 일어남 :: commit된다)
	}

}
