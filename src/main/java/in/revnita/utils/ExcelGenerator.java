package in.revnita.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.revnita.entity.CitizenPlan;
import in.revnita.repo.CitizenPlanRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ExcelGenerator {
	@Autowired
	private CitizenPlanRepository planRepo;

	public void generate(HttpServletResponse response,List<CitizenPlan> records,File file) throws Exception {
		// Workbook workbook=new XSSFWorkbook()//it support xlsx extension of the excel
		// file
		Workbook workbook = new HSSFWorkbook(); // HSSFWorkbook is used for XLS (Excel 2003 format)&/Workbook is
												// interface HSSFWorkbook is a inplementation class for that workbook
		Sheet createSheet = workbook.createSheet("plans-data");
		Row headerRow = createSheet.createRow(0); // FIXED
		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("Citizen Name");
		headerRow.createCell(2).setCellValue("Plan Name");
		headerRow.createCell(3).setCellValue("Plan Status");
		headerRow.createCell(4).setCellValue("Plan Start Date");
		headerRow.createCell(5).setCellValue("Plan End Date");
		headerRow.createCell(6).setCellValue("Benefit Amt");

		//List<CitizenPlan> records= planRepo.findAll(); // It will retrive all data form the database table
		int dataRowIndex = 1;
		for (CitizenPlan plan : records) {
			Row dataRow = createSheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(plan.getCitizenId());
			dataRow.createCell(1).setCellValue(plan.getCitizenName());
			dataRow.createCell(2).setCellValue(plan.getPlanName());
			dataRow.createCell(3).setCellValue(plan.getPlanStatus());
			if (null != plan.getPlanStartDate()) {
				dataRow.createCell(4).setCellValue(plan.getPlanStartDate() + "");// To Convert that it into string we
																					// are useing "" any value that you
																					// append with +"" that get
																					// converted into string
			} else {
				dataRow.createCell(4).setCellValue("N/A");// To Convert that it into string we are useing "" any value
															// that you append with +"" that get converted into string

			}
			if (null != plan.getPlanEndDate()) {
				dataRow.createCell(5).setCellValue(plan.getPlanEndDate() + "");// To Convert that it into string we are
																				// useing ""
			} else {
				dataRow.createCell(5).setCellValue("N/A");// To Convert that it into string we are useing ""

			}

			// BenefitAmt for some cases it is null value and it trying to convert null
			// value into double data type and so it gives error.
			if (null != plan.getBenefitAmt()) {
				dataRow.createCell(6).setCellValue(plan.getBenefitAmt());
			} else {
				dataRow.createCell(6).setCellValue("N/A");

			}
			dataRowIndex++;

		}
		//For File Creation To attach that to the mail body. it create file in the current folder
		FileOutputStream fos=new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
		//It send that file contains to the Browser.
		ServletOutputStream outputStream = response.getOutputStream();// write that workbook data to the outputStream
		workbook.write(outputStream);
		workbook.close();

	}
}
