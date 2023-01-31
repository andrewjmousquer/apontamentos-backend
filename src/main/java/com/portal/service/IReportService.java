package com.portal.service;

import java.util.List;

import com.portal.model.ReportModel;

public interface IReportService {
	
	public Boolean check();
	
	public List<ReportModel> listAll(String token);
	
	public List<ReportModel> listAllFolders(String token);

}
