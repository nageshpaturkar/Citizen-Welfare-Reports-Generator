package in.revnita.controller;

import java.util.List;

import org.apache.catalina.filters.ExpiresFilter.XHttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import in.revnita.entity.CitizenPlan;
import in.revnita.request.SearchRequest;
import in.revnita.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReportController {
	@Autowired
	private ReportService service;
	
	@GetMapping("/pdf")
	//in this we have void as a return type but we take response and send also that respose. we added our attachement to the response 
	public void pdfExport(HttpServletResponse response,Model model ) throws Exception { //it is just like request and response type we set its contenttype & Header as in postman
		response.setContentType("application/pdf");//for pdf=application/pdf
		response.addHeader("Content-Disposition", "attachment;filename=plans.pdf");// tell to the browser that i send a file attached to it you download that file in the browser .
		service.exportPdf(response);
		
	}
	
	
	@GetMapping("/excel")
	//in this we have void as a return type but we take response and send also that respose. we added our attachement to the response 
	public void excelExport(HttpServletResponse response,Model model) throws Exception { //it is just like request and response type we set its contenttype & Header as in postman
		response.setContentType("application/octet-stream");//for pdf=application/pdf
		response.addHeader("Content-Disposition", "attachment;filename=plans.xls");// tell to the browser that i send a file attached to it you download that file in the browser .
		service.exportExcel(response);
	}

	@PostMapping("/search")
	public String handleSearch(@ModelAttribute("search") SearchRequest search, Model model) {
		System.out.println(search);
		List<CitizenPlan> plans = service.search(search);
		model.addAttribute("plans", plans);
		init(model);

		return "index"; // here you are land on the same page after searching data in that case binding
						// object is required and if we want to send binding object(data the we are select as a filter) back to the form then
						// @ModelAttribute annotation capture data from the form and whatever object are
						// avalible they are get send back to the form is called form binding object
						// data binding to the form & form data binding to the object to load the form binding object is required and for that @ModelAttribute is required
	}

	@GetMapping("/")
	public String indexPage(Model model) {
		model.addAttribute("search", new SearchRequest());// it will set our filter object value to null when it is
															// running first time
		init(model);
		return "index";
	}

	private void init(Model model) {
		model.addAttribute("names", service.getPlanName());
		model.addAttribute("status", service.getPlanStatuses());
	}

}
