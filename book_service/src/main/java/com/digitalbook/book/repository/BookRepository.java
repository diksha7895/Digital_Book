package com.digitalbook.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.digitalbook.book.model.BookInfo;

public interface BookRepository extends JpaRepository<BookInfo,Integer> {

	@Query(value="Select B.id, B.TITLE, B.LOGO, B.AUTHOR, B.CATEGORY, B.PRICE, B.PUBLISHER, B.PUBLISHED_DATE, B.Active_Status, B.content from BOOKS B where B.category=:category and B.title=:title and B.author=:authorId and B.price=:price and B.publisher=:publisher and B.active=1", nativeQuery = true)
	public Optional<BookInfo> searchBook(String category,String title,int authorId,String publisher,double price);
	
	public Optional<BookInfo> findByIdAndAuthorId(int bookId, int authorId);

	public Optional<BookInfo> findByTitleAndAuthorId(String title, int authorId);
	
	@Query(value="Select B.id, B.TITLE, B.LOGO, B.AUTHOR, B.CATEGORY, B.PRICE, B.PUBLISHER, B.PUBLISHED_DATE, B.Active_Status, B.content from BOOKS B where  B.author=:authorId and B.id=:bookId and B.active=1", nativeQuery = true)
	public Optional<BookInfo> searchByIdAndAuthorId(int bookId, int authorId);
}
