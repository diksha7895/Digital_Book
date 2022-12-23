package com.digitalbook.book.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.book.model.Book;
import com.digitalbook.book.repository.BookRepository;
import com.digitalbook.book.request.BookRequest;
import com.digitalbook.book.response.BookResponse;
import com.digitalbook.book.response.MessageResponse;
import com.digitalbook.book.service.BooksService;



@RestController
@RequestMapping("/digitalbooks")
public class BookController {
	
	@Autowired
	BooksService booksService;
	
	@Autowired
	BookRepository bookRespository;
	
	
	
	@PostMapping("/author/{author-id}/createBook")
	public ResponseEntity<?> createBook(@RequestBody BookRequest bookRequest, @PathVariable("author-id") Long id) {
		if(id == null)
			return ResponseEntity.badRequest().body("Invalid author Id");
		bookRequest.setAuthorId(id);
		return booksService.saveBook(bookRequest, id);
	}
	
	
	@GetMapping("/book/{book-id}/getSubscribedBook")
	public ResponseEntity<?> getSubscribedBook(@PathVariable("book-id") Long bookId){
		if(bookId == null)
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid Book id."));
		Book book = booksService.getBook(bookId);
		if(book == null || !book.getActive())
			return ResponseEntity.badRequest().body(new MessageResponse("Book not found!"));
		return ResponseEntity.ok(book);
	}
	
	
	@PostMapping("/book/getSubscribedBooks")
		public ResponseEntity<?> getAllSubscribedBooks(@RequestBody List<Long> bookIds){
		if(bookIds.isEmpty())
			return ResponseEntity.badRequest().body("Invalid books");
		List<Book> book = booksService.getAllSubscribedBooks(bookIds);
	
		return ResponseEntity.ok(book);
	}
	


	@GetMapping("/author/{authorId}/blockBook/{bookId}")
	public ResponseEntity<?> blockBook(@PathVariable("authorId") Long authorId, @PathVariable("bookId") Long bookId, @RequestParam("block") boolean block) {
		if(authorId == null || bookId == null)
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid request"));
		if (booksService.blockBook(authorId, bookId, block)) return ResponseEntity.ok().build();
		return ResponseEntity.internalServerError().body(new MessageResponse("Book is not updated."));
	}
	
	
	
	@PutMapping("/author/{author-id}/updateBook/{book-id}")
	public ResponseEntity<?> updateBook(@RequestBody BookRequest bookRequest, @PathVariable("author-id") Long authorId, @PathVariable("book-id") Long bookId) {
		if(authorId == null)
			return ResponseEntity.badRequest().body("Invalid author id");
		if(bookId == null)
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid Book id"));
		if(booksService.updateBook(bookRequest, bookId, authorId)) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.badRequest().body("Book updation failed");
	}

	
	
	@GetMapping("/book/{book-id}/readBook")
	public MessageResponse readBook(@PathVariable("book-id") Long bookId) {
		
		if(bookId == null)
			return new MessageResponse("Invalid Book id");
		return booksService.readBook(bookId);
	}
	
	
	
	@GetMapping("/book/{book-id}/checkBook")
	public String checkBookExistance(@PathVariable("book-id") Long bookId) {
		
		if(bookId == null)
			return "Invalid BookId";
		return bookRespository.existsById(bookId) ? "BookFound" : "Invalid Book Id";
	}
	
	
	@GetMapping("/book/searchBooks")
	public List<BookResponse> searchBooks(@Nullable @RequestParam("category") String category, @Nullable @RequestParam("title") String title,
			@Nullable @RequestParam("author") String author) {
		
		List<BookResponse> booksList = new ArrayList<>();
		System.out.println("booksList : "+booksList);
		if(ObjectUtils.isEmpty(category) && ObjectUtils.isEmpty(title) && ObjectUtils.isEmpty(author))
			return booksList;
		
		List<Book> books = booksService.searchBooks(category, title, author);
		
		System.out.println("books : "+books);
		return getBookResponses(books);
	}
	
	
	@GetMapping("/author/{author-id}/getAuthorBooks")
	public List<Book> getAllAuthorBooks(@PathVariable("author-id") Long authorId) {
		
		List<Book> booksList = new ArrayList<>();
		if(Objects.isNull(authorId) || authorId == 0)
			return booksList;
		booksList = booksService.getAuthorBooks(authorId);
		return booksList;
	}
	
	
	private List<BookResponse> getBookResponses(List<Book> book) {
		System.out.println("Inside getBookResponses method in BookController start");
		
		return book.stream().filter(Book::getActive).map(bookList -> {
			BookResponse bookResponse = new BookResponse();
			bookResponse.setId(bookList.getId());
				bookResponse.setAuthorName(bookList.getAuthorName());
				bookResponse.setCategory(bookList.getCategory());
				bookResponse.setLogo(bookList.getLogo());
				bookResponse.setPrice(bookList.getPrice());
				bookResponse.setTitle(bookList.getTitle());
				System.out.println("Inside getBookResponses method in BookController BookResponse "+bookResponse);
				return bookResponse;
		}).collect(Collectors.toList());
		
	}
}