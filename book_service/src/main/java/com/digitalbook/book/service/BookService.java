package com.digitalbook.book.service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.digitalbook.book.model.BookInfo;
import com.digitalbook.book.repository.BookRepository;

public class BookService {

	@Autowired
	private BookRepository bookRepo;
	
	public void saveBook(BookInfo bookInfo) {
		bookRepo.save(bookInfo);
	}
	
	public ResponseEntity<?> updateBook(int bookId,int authorId,BookInfo bookInfo){
		Optional<BookInfo> existBook = bookRepo.findByBookId(bookId);
		
		if(!existBook.isEmpty()) {
			Blob blob = null;
			try {
				
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
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
