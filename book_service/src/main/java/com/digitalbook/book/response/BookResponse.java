package com.digitalbook.book.response;

import java.sql.Blob;
import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



public class BookResponse {
	

	
	Long id;
	private String logo;
	
	@NotBlank
	private String category;
	
	@NotBlank
	private String title;
	
	@NotNull
	private Double price;
	
	private int authorId;
	
	private String authorName;
	
	private Date publishedDate;
	
	@NotBlank
	private String content;
	
	private boolean active=true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(authorName, category, id, logo, price, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookResponse other = (BookResponse) obj;
		return Objects.equals(authorName, other.authorName) && Objects.equals(category, other.category)
				&& Objects.equals(id, other.id) && Objects.equals(logo, other.logo)
				&& Objects.equals(price, other.price) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "BookRequest [id=" + id + ", logo=" + logo + ", category=" + category + ", title=" + title + ", price="
				+ price + ", authorId=" + authorId + ",authorName=" + authorName +", publishedDate=" + publishedDate + ", content=" + content
				+ ", active=" + active + "]";
	}

	

}
