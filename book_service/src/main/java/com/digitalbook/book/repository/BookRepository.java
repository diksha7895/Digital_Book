package com.digitalbook.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.digitalbook.book.model.Book;


public interface BookRepository extends JpaRepository<Book,Long> {

//	@Query(value="Select B.id, B.TITLE, B.LOGO, B.AUTHOR, B.CATEGORY, B.PRICE, B.PUBLISHER, B.update_on, B.PUBLISH_DATE, B.Active_Status, B.content,B.pic_byte from BOOKS B where B.category=:category and B.title=:title and B.author=:authorId and B.price=:price and B.publisher=:publisher and B.active_status=1", nativeQuery = true)
//	public Optional<BookInfo> searchBook(String category,String title,int authorId,String publisher,double price);
//	
//	public Optional<BookInfo> findByIdAndAuthorId(int bookId, int authorId);
//
//	public Optional<BookInfo> findByTitleAndAuthorId(String title, int authorId);
//	
//	public Optional<List<BookInfo>> findByAuthorId(int userId);
//	
//	@Query(value="Select B.id, B.TITLE, B.LOGO, B.AUTHOR, B.CATEGORY, B.PRICE, B.PUBLISHER, B.PUBLISHED_DATE, B.Active_Status, B.content from BOOKS B where  B.author=:authorId and B.id=:bookId and B.active=1", nativeQuery = true)
//	public Optional<BookInfo> searchByIdAndAuthorId(int bookId, int authorId);
	
	Boolean existsByAuthorIdAndTitle(Long authorId, String title);

	Boolean existsByAuthorIdAndId(Long authorId, Long id );
	
	List<Book> findAllByAuthorId(Long authorId);
	
	@Query("select books from Book books where books.category LIKE %:category% or books.title LIKE %:title% or books.authorName LIKE %:author%")
	List<Book> findBooksByCategoryOrTitleOrAuthor(String category, String title, String author);
	
	@Query("select books from Book books where books.authorName LIKE %:author%")
	List<Book> findBooksByAuthorName(String author);
	
	@Query("select books from Book books where books.title LIKE %:title%")
	List<Book> findBooksByBookTitle(String title);
	
	@Query("select books from Book books where books.category LIKE %:category%")
	List<Book> findBooksByBookCategory(String category);
	
	
}
