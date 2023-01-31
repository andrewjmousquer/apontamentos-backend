package com.portal.integration.jira;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IntegrationCommentJIRA {
	// TODO OLHAR ESSE CARA
	private ArrayList<Object> comments = new ArrayList<Object>();
	private String self;
	private float maxResults;
	private float total;
	private float startAt;

	// Getter Methods

	public ArrayList<Object> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Object> comments) {
		this.comments = comments;
	}

	public String getSelf() {
		return self;
	}

	public float getMaxResults() {
		return maxResults;
	}

	public float getTotal() {
		return total;
	}

	public float getStartAt() {
		return startAt;
	}

	// Setter Methods

	public void setSelf(String self) {
		this.self = self;
	}

	public void setMaxResults(float maxResults) {
		this.maxResults = maxResults;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public void setStartAt(float startAt) {
		this.startAt = startAt;
	}
}
