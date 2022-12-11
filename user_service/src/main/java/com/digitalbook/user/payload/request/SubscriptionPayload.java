package com.digitalbook.user.payload.request;

public class SubscriptionPayload {
	private int bookId;
	private String email;
	public SubscriptionPayload() {
		super();
		
	}
	public SubscriptionPayload(int bookId, String email) {
		super();
		this.bookId = bookId;
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
