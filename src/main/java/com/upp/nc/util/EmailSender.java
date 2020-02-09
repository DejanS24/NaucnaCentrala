package com.upp.nc.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.upp.nc.model.User;

public class EmailSender {

	public static String send(EmailCfg emailCfg, String to, String subject, String message) {
		// Create a mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailCfg.getHost());
        mailSender.setPort(emailCfg.getPort());
        mailSender.setUsername(emailCfg.getUsername());
        mailSender.setPassword(emailCfg.getPassword());
        
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(to);
		mail.setFrom("support@naucnacentrala.com");
		mail.setSubject(subject);
		mail.setText(message);
		
		mailSender.send(mail);
		
		return "Mail sent";
	}
}
