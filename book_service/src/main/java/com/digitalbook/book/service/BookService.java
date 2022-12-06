package com.digitalbook.book.service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.digitalbook.book.model.BookInfo;
import com.digitalbook.book.repository.BookRepository;
import com.digitalbook.book.response.BookResponse;

public class BookService {

	@Autowired
	private BookRepository bookRepo;
	
	public BookResponse getBookById(int bookId) {
		Optional<BookInfo> book = bookRepo.findById(bookId);
		if(!book.isEmpty()) {
			BookResponse bookResp = new BookResponse();
			bookResp.setCategory(book.get().getCategory());
			bookResp.setTitle(book.get().getTitle());
			bookResp.setPrice(book.get().getPrice());
			bookResp.setAuthorId(book.get().getAuthorId());
			bookResp.setPublisher(book.get().getPublisher());
			bookResp.setPublishedOn(book.get().getPublishedOn());
			bookResp.setActive(book.get().isActive());
			
			return bookResp;
		}
		else
			return null;
	}
	
	public BookInfo getBookForCategoryTitle(String category,String title,int authorId,String publisher,double price) {
		Optional<BookInfo> book = bookRepo.searchBook(category, title, authorId, publisher, price);
		return book.isEmpty() ? null : book.get();
	}
	
	public void saveBook(BookInfo bookInfo) {
		bookRepo.save(bookInfo);
	}
	
	public ResponseEntity<?> updateBook(int bookId,int authorId,BookInfo bookInfo){
		Optional<BookInfo> existBook = bookRepo.findById(bookId);
		
		if(!existBook.isEmpty()) {
			Blob blob = null;
			
			bookInfo.setLogo(blob);
			existBook.get().setLogo(bookInfo.getLogo());
			existBook.get().setTitle(bookInfo.getTitle());
			existBook.get().setCategory(bookInfo.getCategory());
			existBook.get().setPrice(bookInfo.getPrice());
			existBook.get().setPublisher(bookInfo.getPublisher());
			existBook.get().setContent(bookInfo.getContent());
			bookRepo.save(existBook.get());
			return ResponseEntity.ok().body("Book is updated successfully.");
			
		}else {
			return ResponseEntity.badRequest().body("Book does not exist.");
		}
		
	}
}
