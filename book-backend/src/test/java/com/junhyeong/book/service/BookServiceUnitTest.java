package com.junhyeong.book.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.junhyeong.book.domain.BookRepository;

/**
 * 
 * 단위 테스트 (service와 관련된 애들만 메모리에 띄우면 됨)
 *	service 에 관련된 객체가 BookRepository 하나밖에없다.
 *	이를 Bean에 띄워서 테스트하는건 사실 DB를 같이 테스트 하기 때문에 서비스에 테스트하고자하는 취지에 어긋난다.
 *	
 *	해결방법: 
 *	BookRepository => 가짜 객체로 만들 수 있다. (MockitoExtension이 지원해줌)
 *
 *
 */


@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {
	// BookService 객체가 만들어질 때 BookServiceUnitTest 파일에 @Mock로 등록된 모든 애들을 주입받는다.
	// 원래라면 bookRepository는 가상의 Mock에 있는 객체이기 때문에 null값이어야만 하나 주입받는 순간 값이 들어오게 된다.
	@InjectMocks 
	private BookService bookService;

	@Mock
	private BookRepository bookRepository;
	
}
