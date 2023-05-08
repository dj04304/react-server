package com.junhyeong.book.domain;

import org.springframework.data.jpa.repository.JpaRepository;

//@Repository 를 적어야 스프링 IoC에 빈으로 등록이 되는데
// JpaRepsitory를 extends하면 생략 가능하다.
// JpaRepsitory는 CRUD 함수를 들고 있음.

public interface BookRepository extends JpaRepository<Book, Long>{

}
