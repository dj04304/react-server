package com.junhyeong.book.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.junhyeong.book.domain.Book;
import com.junhyeong.book.service.BookService;
import com.junhyeong.book.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BookConrtroller {
	
	private final BookService bookService;
	
	// security (라이브러리 적용) - CORS정책을 가지고 있음 (그래서 시큐리티가 CORS를 해제해야함 아니면 CrossOrigin이 적용이 안됨)
	@CrossOrigin
	@PostMapping("/book")
	public ResponseEntity<?> save(@RequestBody Book book) {
		
//		return new ResponseEntity<>(bookService.savedBook(book), HttpStatus.CREATED);
		return ResponseEntity.ok().body(new CMRespDto<>(1, "글쓰기 완료", bookService.savedBook(book)));
	}
	
	@CrossOrigin
	@GetMapping("/book")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok().body(new CMRespDto<>(1, "책 모두 찾기", bookService.findAll()));
				
	}
	
	@CrossOrigin
	@GetMapping("/book/{id}")
	public ResponseEntity<?> findByID(@PathVariable Long id) {
		return ResponseEntity.ok().body(new CMRespDto<>(1, "책 찾기 완료", bookService.findBook(id)));
				
	}
	
	@CrossOrigin
	@PutMapping("/book/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book) {
		return ResponseEntity.ok().body(new CMRespDto<>(1, "수정하기 완료", bookService.updateBook(id, book)));
				
	}
	
	@CrossOrigin
	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		return ResponseEntity.ok().body(new CMRespDto<>(1, "삭제 완료", bookService.deleteBook(id)));
				
	}
	
}
