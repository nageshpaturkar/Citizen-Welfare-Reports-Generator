package in.revnita.service;

import java.util.List;


import in.revnita.entity.CitizenPlan;
import in.revnita.request.SearchRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {
	
	public List<String> getPlanName();
	
	public List<String>getPlanStatuses();
	
	public List<CitizenPlan> search(SearchRequest request);
	
	public boolean exportExcel(HttpServletResponse response) throws Exception;
	
	public boolean exportPdf(HttpServletResponse response) throws Exception;;

	

}
