package com.portal.integration.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IntegrationJIRA {
	private String expand;
	private String id;
	private String self;
	private String key;
	private IntegrationFieldsJIRA fields;

	// Getter Methods

	public String getExpand() {
		return expand;
	}

	public String getId() {
		return id;
	}

	public String getSelf() {
		return self;
	}

	public String getKey() {
		return key;
	}

	public IntegrationFieldsJIRA getFields() {
		return fields;
	}

	// Setter Methods

	public void setExpand(String expand) {
		this.expand = expand;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setFields(IntegrationFieldsJIRA fieldsObject) {
		this.fields = fieldsObject;
	}
}