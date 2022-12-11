package com.digitalbook.user.dto;

import java.sql.Blob;

public class BookLogoFile {

	public Blob logo; 
	
	private Books books;

	public Blob getLogo() {
		return logo;
	}

	public void setLogo(Blob logo) {
		this.logo = logo;
	}

	public Books getBooks() {
		return books;
	}

	public void setBooks(Books books) {
		this.books = books;
	}

	public BookLogoFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BookLogoFile(Blob logo, Books books) {
		super();
		this.logo = logo;
		this.books = books;
	}
	
	
}
