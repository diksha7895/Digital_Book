package com.digitalbook.book.service;



import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.digitalbook.book.model.Book;
import com.digitalbook.book.repository.BookRepository;
import com.digitalbook.book.request.BookRequest;
import com.digitalbook.book.response.MessageResponse;


@Service
public class BooksService {

	@Autowired
	BookRepository bookRespository;
	
		public Book getBook(Long bookId) {
				
				Optional<Book> book = bookRespository.findById(bookId);
				if(book.isPresent())
					return book.get();
				return null;
			}

		private Book generateBook(BookRequest bookRequest) {
			Book book = new Book();
			book.setActive(bookRequest.isActive());
			book.setAuthorId(bookRequest.getAuthorId());
			book.setAuthorName(bookRequest.getAuthorName());
			book.setCategory(bookRequest.getCategory());
			book.setContent(bookRequest.getContent());
			book.setLogo(bookRequest.getLogo());
			book.setPrice(bookRequest.getPrice());
			book.setPublishedDate(bookRequest.getPublishedDate());
			book.setPublisher(bookRequest.getPublisher());
			book.setTitle(bookRequest.getTitle());
			System.out.println("generate Book "+book);
			return book;
		}

		public List<Book> searchBooks(String category, String title, String author) {
			
			System.out.println("Inside searchBooks method in BookService");
			
			List<Book> books = new ArrayList<>();
			if(ObjectUtils.isEmpty(category) && ObjectUtils.isEmpty(title) && !ObjectUtils.isEmpty(author))
				books =  bookRespository.findBooksByAuthorName(author);
			else if(ObjectUtils.isEmpty(category) && ObjectUtils.isEmpty(author) && !ObjectUtils.isEmpty(title))
				books = bookRespository.findBooksByBookTitle(title);
			else if(ObjectUtils.isEmpty(title) && ObjectUtils.isEmpty(author) && !ObjectUtils.isEmpty(category))
				books =  bookRespository.findBooksByBookCategory(category);
			else if(!ObjectUtils.isEmpty(title) && !ObjectUtils.isEmpty(author) && !ObjectUtils.isEmpty(category))
				books = bookRespository.findBooksByCategoryOrTitleOrAuthor(category, title, author);
			
			return books;
		}
		
	public ResponseEntity<?> saveBook(BookRequest bookRequest, Long authorId) {
		Book book = generateBook(bookRequest);
		
		try {
			if(Boolean.TRUE.equals(bookRespository.existsByAuthorIdAndTitle(authorId, book.getTitle()))) {
				
				return ResponseEntity.badRequest().body("Book already exist!");
			}
			book.setPublishedDate(Timestamp.valueOf(LocalDateTime.now()));
			book = bookRespository.save(book);
		} catch (Exception exception) {
			return ResponseEntity.internalServerError().body("Error: "+exception.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	
	public List<Book> getAllSubscribedBooks(List<Long> bookIds){
		
		List<Book> allSubscribedBooks = bookRespository.findAllById(bookIds);
		return allSubscribedBooks.stream().filter(Book::getActive).collect(Collectors.toList());
		
	}

	public boolean blockBook(Long authorId, Long bookId, boolean block) {
		if(Boolean.TRUE.equals(bookRespository.existsByAuthorIdAndId(authorId, bookId))) {
			Book book = getBook(bookId);
			if(book.getActive() != block)
				return false;
			book.setActive(!block);

			Book book1 = bookRespository.save(book);
			if(book1.getActive() != block) {
				
				return true;
			} else 
				return false;
		} else
			return false;
		
	}

	public boolean updateBook(BookRequest bookRequest, Long bookId, Long authorId) {
		Book book = generateBook(bookRequest);
		
		if(Boolean.TRUE.equals(bookRespository.existsByAuthorIdAndId(authorId, bookId))) {
			Book existBook = getBook(bookId);
			existBook.setCategory(book.getCategory());
			existBook.setContent(book.getContent());
			existBook.setPrice(book.getPrice());
			existBook.setPublisher(book.getPublisher());
			existBook.setTitle(book.getTitle());
			bookRespository.save(existBook);
			return true;
		}
		return false;
	}

	
	public MessageResponse readBook(Long bookId) {
		if(bookRespository.existsById(bookId)) {
			Book book = getBook(bookId);
			if(book.getActive()) {
				return new MessageResponse(book.getContent());
			}
		}
		return new MessageResponse("Invalid Request");
	}

	

	public List<Book> getAuthorBooks(Long authorId) {
		List<Book> books = bookRespository.findAllByAuthorId(authorId);
		return books;
	}
}
