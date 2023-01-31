package com.portal.integration.jira;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Description {
	private String type;
	private float version;
	// TODO OLHAR ESSE CARA
	ArrayList<Object> content = new ArrayList<Object>();

	// Getter Methods

	public String getType() {
		return type;
	}

	public float getVersion() {
		return version;
	}

	// Setter Methods

	public void setType(String type) {
		this.type = type;
	}

	public void setVersion(float version) {
		this.version = version;
	}
}