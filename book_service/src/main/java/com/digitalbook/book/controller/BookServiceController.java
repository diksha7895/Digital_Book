package com.digitalbook.book.controller;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.digitalbook.book.model.BookInfo;
import com.digitalbook.book.response.BookContentResponse;
import com.digitalbook.book.response.BookResponse;
import com.digitalbook.book.response.BookWithByteFile;
import com.digitalbook.book.response.MessageResponse;
import com.digitalbook.book.service.BookService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/digitalbooks")
public class BookServiceController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	BookService bookService;
	
	@GetMapping("/get")
	public List<BookInfo> getBooks() {
		return bookService.findAll();
	}
	
	@GetMapping("/getBook/{bookId}")
	public BookResponse getBookId(@PathVariable("bookId") int bookId) {
		return bookService.getBookById(bookId);
	}
	
	@GetMapping("/searchBook/{category}/{title}/{author}/{price}/{publisher}")
	public BookInfo getBook(@PathVariable("category") String category,@PathVariable("title") String title,
			@PathVariable("author") int authorId,@PathVariable("price") double price,
			@PathVariable("publisher") String publisher) {
		return bookService.getBookForSearch(category, title, authorId, publisher, price);
	}
	
	
	@GetMapping("searchBook/cancel/{user-id}/{book-id}/{block}")
	public ResponseEntity<MessageResponse> blockBook(@PathVariable("user-id") int userId,
			@PathVariable("book-id") int bookId, @PathVariable("block") String block) {
		BookInfo book = bookService.checkBookAvailability(bookId);
		BookInfo bookAndUser = bookService.checkBookUserAvailability(userId, bookId);
		if (book == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("This book doesn't exist!"));
		} else if (bookAndUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new MessageResponse("This book doesn't belong to author!"));

		} else if (block.equalsIgnoreCase("yes") || block.equalsIgnoreCase("no") ) {
			BookInfo savedBook = bookService.blockBook(bookId, block);
			return ResponseEntity.ok(new MessageResponse("Updated book status\n" + savedBook));
		}
		
		else {
			return ResponseEntity.ok(new MessageResponse("Something went wrong, Please try again .."));
		}

	}
	
	@GetMapping("/getBook/subscribed/{book-id}")

	public BookInfo getBookForSubscribedBook(@PathVariable("book-id") int bookId) {
		return bookService.getSubscribedBook(bookId);
	}

	@GetMapping("/getBook/subscribed/content/{book-id}")
	public BookContentResponse getSubscribedBookContent(@PathVariable("book-id") int bookId) {
		return bookService.getSubscribedBookContent(bookId);
	}
	
	@PostMapping("/author/{author-id}")
	public BookInfo saveBook( @RequestBody BookWithByteFile book,
			@PathVariable("author-id") int authorId) {
		Blob blob = null;
		try {
     	blob = new javax.sql.rowset.serial.SerialBlob(book.getFile());
			
		}  catch ( SQLException e) {
			e.printStackTrace();
		}
		return bookService.saveBook(blob, book, authorId);

	}
	
	@PostMapping("/author/{author-id}/{book-id}")
	public BookInfo updateBook(@RequestBody BookWithByteFile book,
			@PathVariable("author-id") int authorId, @PathVariable("book-id") int bookId
			) {
		Blob blob = null;
		try {
     	blob = new javax.sql.rowset.serial.SerialBlob(book.getFile());
			
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return bookService.updateBook(blob, book,authorId,bookId);
	}
	
}
