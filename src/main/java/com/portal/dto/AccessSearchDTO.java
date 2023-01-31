package com.portal.dto;

import java.util.Date;
import java.util.List;

import com.portal.model.ClassifierModel;

public class AccessSearchDTO {
	private Date dateStart;
	private Date dateEnd;
	private String bed;
	private ClassifierModel accessTypeFilter;
	private List<ClassifierModel> visitorTypeList;
	private Integer first;
	private Integer rows;
	
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	public Date getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getBed() {
		return bed;
	}
	public void setBed(String bed) {
		this.bed = bed;
	}
	public ClassifierModel getAccessTypeFilter() {
		return accessTypeFilter;
	}
	public void setAccessTypeFilter(ClassifierModel accessTypeFilter) {
		this.accessTypeFilter = accessTypeFilter;
	}
	public List<ClassifierModel> getVisitorTypeList() {
		return visitorTypeList;
	}
	public void setVisitorTypeList(List<ClassifierModel> visitorTypeList) {
		this.visitorTypeList = visitorTypeList;
	}
	public Integer getFirst() {
		return first;
	}
	public void setFirst(Integer first) {
		this.first = first;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
}
