package in.revnita.service;


import java.io.File;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

import in.revnita.entity.CitizenPlan;
import in.revnita.repo.CitizenPlanRepository;
import in.revnita.request.SearchRequest;
import in.revnita.utils.EmailUtils;
import in.revnita.utils.ExcelGenerator;
import in.revnita.utils.PdfGenerator;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ExcelGenerator excelGenerator;
	
	@Autowired
	private PdfGenerator pdfGenerator;
	
	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private CitizenPlanRepository planRepo;
	

	@Override
	public List<String> getPlanName() {
		List<String> planNames = planRepo.getPlanNames();
		return planNames;
	}

	@Override
	public List<String> getPlanStatuses() {
		return planRepo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {
		// for convertion of binding object to the entity object
		CitizenPlan entity = new CitizenPlan();
		if (null != request.getPlanName() && !"".equals(request.getPlanName())) {// empty "" not equal to getplanName;
			entity.setPlanName(request.getPlanName());
		}
		if (null != request.getPlanStatus() && !"".equals(request.getPlanStatus())) {// empty "" not equal to
																						// getplanName;
			entity.setPlanStatus(request.getPlanStatus());
		}
		if (null != request.getGender() && !"".equals(request.getGender())) {// empty "" not equal to getplanName;
			entity.setGender(request.getGender());
		}
		if (null != request.getStartDate()) {
			entity.setPlanStartDate(request.getStartDate());
		}
		return planRepo.findAll(Example.of(entity));//Here query creation work will take care by jpa.
	}

	//we are trying to follow single responsibily principle.
	@Override
	public boolean exportExcel(HttpServletResponse response) throws Exception {
		File f=new File("Plans.xls");//Ekach file create keli tich excel manun store hote ani tich email l send hote.
		List<CitizenPlan>plan=planRepo.findAll();
		excelGenerator.generate(response, plan,f);
		String subject="Test mail Subject";
		String body="<h1>Test Mail body </h1>";
		String to="kapsemanish37@gmail.com";
//		File f=new File("plans.xls");//Here file name should be same as file that we are store in our folder
		emailUtils.sendEmail(subject, body, to,f);
		f.delete();//After file is send as a mail then delete that file from folder.
		return true;
	}



	@Override
	public boolean exportPdf(HttpServletResponse response) throws Exception {
		File f=new File("Plans.pdf");//Ekach file create keli tich excel manun store hote ani tich email l send hote.

		List<CitizenPlan>plans=planRepo.findAll();
		pdfGenerator.generate(response, plans,f);
		String subject="Test mail Subject";
		String body="<h1>Test Mail body </h1>";
		String to="kapsemanish37@gmail.com";
//		File f=new File("plans.xls");//Here file name should be same as file that we are store in our folder
		emailUtils.sendEmail(subject, body, to,f);
		f.delete();//After file is send as a mail then delete that file from folder.
		return true;
		
	}
		

}
