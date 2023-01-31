package com.portal.integration.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IntegrationIssuetypeJIRA {
	private String self;
	private String id;
	private String description;
	private String iconUrl;
	private String name;
	private boolean subtask;
	private float avatarId;
	private float hierarchyLevel;

	// Getter Methods

	public String getSelf() {
		return self;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public String getName() {
		return name;
	}

	public boolean getSubtask() {
		return subtask;
	}

	public float getAvatarId() {
		return avatarId;
	}

	public float getHierarchyLevel() {
		return hierarchyLevel;
	}

	// Setter Methods

	public void setSelf(String self) {
		this.self = self;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSubtask(boolean subtask) {
		this.subtask = subtask;
	}

	public void setAvatarId(float avatarId) {
		this.avatarId = avatarId;
	}

	public void setHierarchyLevel(float hierarchyLevel) {
		this.hierarchyLevel = hierarchyLevel;
	}
}