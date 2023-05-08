package com.junhyeong.book.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.junhyeong.book.domain.Book;
import com.junhyeong.book.domain.BookRepository;

import lombok.RequiredArgsConstructor;

//Service로 등록이 되면 기능을 정의할 수 있고, 트랜잭션을 관리할 수 있다.
//주된 업무: 함수 => 송금() -> 레파지토리에 여러개 함수를 실행하고 -> commit or rolback하는 역할

@RequiredArgsConstructor
@Service
public class BookService {

	private final BookRepository bookRepository;
	
	//.save -> 내가 넣은 타입 그대로 return해준다.
	//@Transactional -> 서비스 함수가 종료될 때 commit 할지 rollback할지 트랜잭션 관리 하겠다는 뜻
	@Transactional
	public Book savedBook(Book book) {
		return bookRepository.save(book);
		
	}
	
	//id를 찾는 방식 찾으면 잘 return 이 되나, 못 찾는 경우가 있을 수 있으니 exeption을 달아준다.
	//JPA 변경감지라는 내부 기능 바꼈는지 안바꼈는지 지켜보는데 readonly가 되어있으면 기능 활성화 X, update시의 정합성을 유지해줌, insert의 유령 데이터 현상(팬텀현상) 못막음
	@Transactional(readOnly = true) 
	public Book findBook(Long id) {
		return bookRepository.findById(id).orElseThrow(() ->
				 new IllegalArgumentException("id를 확인해주세요!"));
				
//				.orElseThrow(new Supplier<IllegalArgumentException>() {
//
//					@Override
//					public IllegalArgumentException get() {
//						return new IllegalArgumentException("id를 확인해주세요!");
//					}
//				});
		
	}
	
	public List<Book> findAll() {
		return bookRepository.findAll();
	}
	
	// 더티체킹 update 치기
	@Transactional
	public Book updateBook(Long id, Book book) {
		Book bookEntity = bookRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException()); //영속화가 된다. (book 오브젝트) -> 영속성 컨텍스트 보관, 데이터베이스에서 데이터를 가져오면 그것을 영속화한다고 말함
		bookEntity.setTitle(book.getTitle());
		bookEntity.setAuthor(book.getAuthor());
		return bookEntity;
	} //함수 종료 => 트랜잭션 종료 => 영속화 되어있는 데이터를 DB로 갱신(flush) => 이때 commit이 된다 이를 더티체킹이라고 함
	
	@Transactional
	public String deleteBook(Long id) {
		bookRepository.deleteById(id); // 오류가 나면 exception을 타니까 신경쓰지 않아도 된다. void이기 때문에 return을 따로 해주지 않아도 된다.
		return "ok";
	}
}
