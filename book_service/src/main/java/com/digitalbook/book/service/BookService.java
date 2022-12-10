package com.digitalbook.book.service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.digitalbook.book.model.BookInfo;
import com.digitalbook.book.repository.BookRepository;
import com.digitalbook.book.response.BookContentResponse;
import com.digitalbook.book.response.BookResponse;
import com.digitalbook.book.response.BookWithByteFile;

@Service
@Transactional
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
	
	public BookInfo getBookForSearch(String category,String title,int authorId,String publisher,double price) {
		Optional<BookInfo> book = bookRepo.searchBook(category, title, authorId, publisher, price);
		return book.isEmpty() ? null : book.get();
	}
	
	public BookInfo saveBook(Blob blob,BookWithByteFile book, int authorId) {
		Optional<BookInfo> existingBook = bookRepo.findByTitleAndAuthorId(book.getBooks().getTitle(),authorId);
		if(existingBook.isEmpty()) { 
			BookInfo bookToBeSaved = new BookInfo();
			bookToBeSaved.setLogo(blob);
			bookToBeSaved.setActive(book.getBooks().isActive());
			bookToBeSaved.setAuthorId(authorId);
			bookToBeSaved.setCategory(book.getBooks().getCategory());
			bookToBeSaved.setContent(book.getBooks().getContent());
			bookToBeSaved.setPrice(book.getBooks().getPrice());
			bookToBeSaved.setPublishedOn(new Date());
			bookToBeSaved.setPublisher(book.getBooks().getPublisher());
			bookToBeSaved.setTitle(book.getBooks().getTitle());
			return bookRepo.save(bookToBeSaved);
		}
		else {
			return null;
		}

	}
	
  public BookInfo updateBook(Blob blob, BookWithByteFile book, int authorId, int bookId) {
		
		Optional<BookInfo> existingBookForUser = bookRepo.searchByIdAndAuthorId(bookId,authorId);
		if(!existingBookForUser.isEmpty()) {


			existingBookForUser.get().setLogo(blob);
			existingBookForUser.get().setTitle(book.getBooks().getTitle());
			existingBookForUser.get().setPrice(book.getBooks().getPrice());
			existingBookForUser.get().setPublisher(book.getBooks().getPublisher());
			existingBookForUser.get().setActive(book.getBooks().isActive());
			existingBookForUser.get().setContent(book.getBooks().getContent());
			existingBookForUser.get().setCategory(book.getBooks().getCategory());
			return bookRepo.save(existingBookForUser.get());
		}
		else {
			return null;
         }
		
	}
	
  public byte[] getSubscribedBookForLogo(int bookId) {

		byte[] byteArray = null;
		try {
			Optional<BookInfo> book =	bookRepo.findById(bookId);
			byteArray = book.isEmpty()? null : book.get().getLogo().getBytes(1,(int)book.get().getLogo().length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return byteArray;
	}

	public BookInfo getSubscribedBook(int bookId) {
		Optional<BookInfo> book =	bookRepo.findById(bookId);
		return book.isEmpty()?null:book.get();
	}
	
	
	public BookInfo checkBookAvailability( int bookId) {
		Optional<BookInfo> book =	bookRepo.findById(bookId);
		if(!book.isEmpty()) {
			return book.get();
		}
		else {
			return null;
		}
		
	}

	public BookInfo checkBookUserAvailability(int userId, int bookId) {
		Optional<BookInfo> book =	bookRepo.findById(bookId);
		if(!book.isEmpty() && book.get().getAuthorId()==userId) {		
				return book.get();	
		}
			return null;
				
	}
	
	public BookContentResponse getSubscribedBookContent(int bookId) {
		Optional<BookInfo> book =	bookRepo.findById(bookId);
		BookContentResponse bookContent = new BookContentResponse();
		if(!book.isEmpty()) {
			bookContent.setContent(book.get().getContent());
			bookContent.setTitle(book.get().getTitle());
			bookContent.setActive(book.get().isActive());
			return bookContent;
		}
		
		else{
			return null;
		}
	}
	
	public BookInfo blockBook(int bookId, String block) {
		Optional<BookInfo> book = bookRepo.findById(bookId);
		BookInfo savedBook  = null; 
		if(block.equalsIgnoreCase("yes") && !book.isEmpty()) {
			
				book.get().setActive(false);
				 savedBook = bookRepo.save(book.get());
			}
		if(block.equalsIgnoreCase("no") && !book.isEmpty()) {
			
				book.get().setActive(true);
				 savedBook = bookRepo.save(book.get());
			}
		
		return savedBook;
		
	}
}
