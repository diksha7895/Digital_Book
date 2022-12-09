package com.digitalbook.book.response;

import com.digitalbook.book.model.BookInfo;

public class BookWithByteFile {
	private byte[] file; 
	
	private BookInfo books;

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public BookInfo getBooks() {
		return books;
	}

	public void setBooks(BookInfo books) {
		this.books = books;
	}

	public BookWithByteFile(byte[] file, BookInfo books) {
		super();
		this.file = file;
		this.books = books;
	}
	

	public BookWithByteFile() {
		super();
	}
	
	

}
