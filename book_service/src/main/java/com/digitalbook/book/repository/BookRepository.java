package com.digitalbook.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.digitalbook.book.model.BookInfo;

public interface BookRepository extends JpaRepository<BookInfo,Integer> {

	@Query(value="Select ")
	public Optional<BookInfo> searchBook(String category,String title,int authorId,String publisher,double price);
}
