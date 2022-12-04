package com.digitalbook.book.model;

import java.sql.Blob;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

public class BookInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookId;
	
	@Column(name="Title")
	private String title;
	
	@Column(name="Category")
	private String category;
	
	@Column(name="Price")
	private double price;
	
	@Column(name="Author")
	private String authorId;
	
	@Lob
	private Blob logo;
	
	@Column(name="Publisher")
	private String publisher;
	
	@Column(name="Publish_Date")
	private Date publishedOn;
	
	@Column(name="Update_On")
	private Date update;
	
	@Column(name="Content")
	private String content;
	
	@Column(name="Active_Status")
	private boolean active;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public Blob getLogo() {
		return logo;
	}

	public void setLogo(Blob logo) {
		this.logo = logo;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublishedOn() {
		return publishedOn;
	}

	public void setPublishedOn(Date publishedOn) {
		this.publishedOn = publishedOn;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public BookInfo() {
		
	}
	

	public BookInfo(int bookId, String title, String category, double price, String authorId, Blob logo,
			String publisher, Date publishedOn, Date update, String content, boolean active) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.category = category;
		this.price = price;
		this.authorId = authorId;
		this.logo = logo;
		this.publisher = publisher;
		this.publishedOn = publishedOn;
		this.update = update;
		this.content = content;
		this.active = active;
	}
	
	@Override
	public String toString() {
		return "BookInfo [bookId=" + bookId + ", title=" + title + ", category=" + category + ", price=" + price
				+ ", authorId=" + authorId + ", logo=" + logo + ", publisher=" + publisher + ", publishedOn="
				+ publishedOn + ", update=" + update + ", content=" + content + ", active=" + active + "]";
	}
	
	
	
}