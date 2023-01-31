package com.portal.integration.jira;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IntegrationListJIRA {

	private String expand;
	private Integer startAt;
	private Integer maxResults;
	private Integer total;
	private ArrayList<IntegrationJIRA> issues;

	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}

	public Integer getStartAt() {
		return startAt;
	}

	public void setStartAt(Integer startAt) {
		this.startAt = startAt;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public ArrayList<IntegrationJIRA> getIssues() {
		return issues;
	}

	public void setIssues(ArrayList<IntegrationJIRA> issues) {
		this.issues = issues;
	}

}