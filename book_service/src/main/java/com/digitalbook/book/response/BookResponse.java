package com.digitalbook.book.response;

import java.sql.Blob;
import java.util.Date;



public class BookResponse {
	

	
	private String title;
	private String category;
	private double price;
	private int authorId;
	//private Blob logo;
	private String publisher;
	private Date publishedOn;
	private Date update;
	//private String content;
	private boolean isActive;
	
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
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int i) {
		this.authorId = i;
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
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public BookResponse(String title, String category, double price, int authorId, String publisher,
			Date publishedOn, Date update, boolean isActive) {
		super();
		
		this.title = title;
		this.category = category;
		this.price = price;
		this.authorId = authorId;
		this.publisher = publisher;
		this.publishedOn = publishedOn;
		this.update = update;
		this.isActive = isActive;
	}
	public BookResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	


}
