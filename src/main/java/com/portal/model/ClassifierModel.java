package com.portal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassifierModel {

	private Integer id;
	private String value;
	private String type;
	private String label;
	private String description;
	
	public ClassifierModel(Integer id) {
		this.id = id;
	}
	
	public ClassifierModel(String type) {
		this.type = type;
	}
	
	public ClassifierModel(int id, String value) {
		if(id > 0) {
			this.id = id;
		}
		this.value = value;
	}
	
	public ClassifierModel(String value, String type) {
		if(value != null) this.value = value;
		if(type != null) this.type = type;
	}
	
	public ClassifierModel(int id, String value, String type) {
		if(id > 0) this.id = id;
		if(value != null) this.value = value;
		if(type != null) this.type = type;
	}
	
	public ClassifierModel(String value, String type, String label) {
		if(value != null) this.value = value;
		if(type != null) this.type = type;
		if(label != null) this.label = label;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassifierModel other = (ClassifierModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClassifierModel [id=" + id + ", value=" + value + ", type=" + type + "]";
	}
	
	
}
