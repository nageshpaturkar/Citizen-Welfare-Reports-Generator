package in.revnita.utils;

import java.io.File;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender mailSender;

	public boolean sendEmail(String subject,String body,String to,File f) {
		
		try {
			
			MimeMessage mimeMsg=mailSender.createMimeMessage();//If You want some attachement go for minemsg
			MimeMessageHelper helper=new MimeMessageHelper(mimeMsg, true);//Constructor Injection.
			helper.setSubject(subject);
			helper.setText(body, true);//If Your Body contain html tag then True else false.
			helper.setTo(to);
			helper.addAttachment("Plans-Info", f);//Plans-info is any name that you want to send
			mailSender.send(mimeMsg);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	

}
