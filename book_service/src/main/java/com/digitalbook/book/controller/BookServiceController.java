package com.digitalbook.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.book.response.BookResponse;
import com.digitalbook.book.service.BookService;

@RestController
@RequestMapping("/digitalbook")
public class BookServiceController {
	
	@Autowired
	BookService bookService;
	
	@GetMapping("/getBook/{bookId}")
	public BookResponse getBookId(@PathVariable("bookId") int bookId) {
		return bookService.getBookById(bookId);
	}
	
}
