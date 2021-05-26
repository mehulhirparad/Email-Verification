package com.email.verification.logic;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
// email verification logic 
public class EmailVerificationLogic
{
	@Autowired(required=true)
	private JavaMailSender mailSender;
	
	public void sendVerificationEmail(List<String> emailVerifiacationFiledList) 
			throws MessagingException, UnsupportedEncodingException
	{
		String toAddress = "Receiver Email Address";
		String fromAddress = "Sender Emaul Address";
		String senderName = "Java learner";
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>"
				+ "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
				+ "Thank you,<br>"
				+ "Your company name";			
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);		
		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);	
		content = content.replace("[[name]]", emailVerifiacationFiledList.get(0));
		String verifyURL = "http://localhost:9999/emailverification" + "/verify?code=" + emailVerifiacationFiledList.get(1);		
		content = content.replace("[[URL]]", verifyURL);		
		helper.setText(content, true);
		mailSender.send(message);		
		System.out.println("Email has been sent");
	}
}
