package com.upp.nc.dto;

public class ReviewDTO {

	private boolean review_pass;
	private String editor_comments;
	private String author_comments;
	public boolean isReview_pass() {
		return review_pass;
	}
	public void setReview_pass(boolean review_pass) {
		this.review_pass = review_pass;
	}
	public String getEditor_comments() {
		return editor_comments;
	}
	public void setEditor_comments(String editor_comments) {
		this.editor_comments = editor_comments;
	}
	public String getAuthor_comments() {
		return author_comments;
	}
	public void setAuthor_comments(String author_comments) {
		this.author_comments = author_comments;
	}
	
	
}
