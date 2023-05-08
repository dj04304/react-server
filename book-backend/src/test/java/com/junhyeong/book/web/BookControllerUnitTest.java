package com.junhyeong.book.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junhyeong.book.config.EnableMockMvcConfig;
import com.junhyeong.book.domain.Book;
import com.junhyeong.book.service.BookService;

import lombok.extern.slf4j.Slf4j;

// 단위 테스트 (통합 테스트와 다르게 controller 관련 로직만 띄우기)
// ex) controller, Filter, ControllerAdvice(exception처리할 때 쓰는 것)
// 장점: 가볍다(통합 테스트에 비해 controller파트만 테스트 하기 때문)
// 단점: 확실하게 돌아간다는 보장이없음, controller부분 만 테스트하기 때문에 합쳐졌을때 시너지가 어떻게 일어날 지 모르기 때문이다.


@Slf4j
@WebMvcTest
@EnableMockMvcConfig
public class BookControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean // BookService가 IoC환경에 Bean에 등록된다.
	private BookService bookService;
	
	@Autowired
	private WebApplicationContext ctx;

	// JUnit5(@BeforeEach), JUnit4(@Before)
//	@BeforeEach
//	public void setup() {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
//				.addFilters(new CharacterEncodingFilter("UTF-8", true))
//				.build();
//	}
	
	// 일반적으로 @Test를 붙이고 실행하면 오류가난다. 그 이유는 controller에 가보면 BookService 인터페이스를 사용하고 있기 때문이다.
	
	//BDDMocito 패턴 given when then
	@Test
	public void saveTest() throws Exception {
//		log.info("saveTest() 시작 =========================");
//		Book book = bookService.savedBook(new Book(null, "제목", "준형"));	
//		log.info("book: "+ book); // null 실제 bookservice가 아닌 가상의 bookservice이기 때문에 당연히 null이다.
		
		//given (테스트를 하기 위한 준비)
		
		Book book = new Book(null, "스프링 따라하기", "준형");
		
		//json 데이터로 바꿔주는 함수이다. gson처럼 json으로 바꿔주는 역할이다.
		String content = new ObjectMapper().writeValueAsString(book);
//		log.info("json: " + content);
		
		// 미리 행동을 지정해준다. 이를 stub이라고 한다.
		when(bookService.savedBook(book)).thenReturn(new Book(1L, "hello spring", "jun"));
		
		// when (테스트 실행) perform 의 return 타입이 ResultActions 이다.
		ResultActions resultActions = mockMvc.perform(
				post("/book")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.characterEncoding("UTF-8")
				.content(content));
		
		//then (when에대한 기댓값, 즉 검증)
		resultActions
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.title").value("hello spring"))
		.andDo(MockMvcResultHandlers.print());
				
	}
}
