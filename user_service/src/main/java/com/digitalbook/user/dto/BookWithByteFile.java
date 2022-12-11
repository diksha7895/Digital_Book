package com.digitalbook.user.dto;


public class BookWithByteFile {
	private byte[] file; 
	
	private Books books;

	public byte[] getFile() {
		return file;
	}

	public Books getBooks() {
		return books;
	}

	public void setBooks(Books books) {
		this.books = books;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public BookWithByteFile(byte[] file, Books books) {
		super();
		this.file = file;
		this.books = books;
	}

	public BookWithByteFile() {
		super();
		
	}

	
	
}
