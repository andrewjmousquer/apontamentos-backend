package com.portal.integration.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IntegrationSubtaskFieldsJIRA {
	private String summary;
	private Status status;
	private IntegrationIssuetypeJIRA issuetype;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public IntegrationIssuetypeJIRA getIssuetype() {
		return issuetype;
	}

	public void setIssuetype(IntegrationIssuetypeJIRA issuetype) {
		this.issuetype = issuetype;
	}

}