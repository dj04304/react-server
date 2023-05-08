package com.junhyeong.book.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junhyeong.book.domain.Book;

import lombok.extern.slf4j.Slf4j;

/**
 * 통합 테스트 (모든 Bean들을 똑같이 IoC에 올리고 테스트 하는 것)
 * 	WebEnvironment.MOCK => 실제 톰캣을 올리는 것이 아닌, 다른 톰캣으로 테스트하는 것
 *	WebEnvironment.RANDOM_PORT => 실제 톰캣으로 테스트
 * @AutoConfiguerMockMvc MockMvc를 IoC에 등록해준다. (이는 WebTest에 포함되어 있음)
 *	
 *@Trascational은 각각의 테스트 함수가 종료될 때 마다 트랜잭션을 rollback해주는 어노테이션이다.
 *	가령 test1함수와 test2함수가 있는데 둘 다 DB에 insert를 한다고 가정하면 test1함수를 insert하고 rollback한다음
 *	test2함수가 insert되게 된다. (독립적으로 테스트할 수 있음)
 *
 *
 *
 */


@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) 
public class BookControllerIntegreTest {

	@Autowired
	private MockMvc mockMvc;
	
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
		
		// when (테스트 실행) perform 의 return 타입이 ResultActions 이다.
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.post("/book")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.characterEncoding("UTF-8")
				.content(content));
		
		//then (when에대한 기댓값, 즉 검증)
		resultActions
		.andExpect((ResultMatcher) status().isCreated())
		.andExpect((ResultMatcher) jsonPath("$.title").value("hello spring"))
		.andDo(MockMvcResultHandlers.print());
				
	}
}
