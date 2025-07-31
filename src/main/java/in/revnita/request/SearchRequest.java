package in.revnita.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class SearchRequest {
	private String planName;
	private String planStatus;
	private String gender;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate startDate; //When You Go For LocaDate then dataFormat is yyyy-mm-dd but in html when we take date as a input then format is dd-mm-yyyy
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate endDate;

}
