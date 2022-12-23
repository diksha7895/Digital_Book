package com.digitalbook.user.payload.request;

public class SubscriptionPayload {
	private Long id;
	
	private Long bookId;
	
	private Long userId;
	
	private boolean active = true;
	
	private String subscriptionTime;
	public SubscriptionPayload() {
		super();
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getSubscriptionTime() {
		return subscriptionTime;
	}
	public void setSubscriptionTime(String subscriptionTime) {
		this.subscriptionTime = subscriptionTime;
	}
	@Override
	public String toString() {
		return "SubscriptionPayload [id=" + id + ", bookId=" + bookId + ", userId=" + userId + ", active=" + active
				+ ", subscriptionTime=" + subscriptionTime + "]";
	}
	
	
}
