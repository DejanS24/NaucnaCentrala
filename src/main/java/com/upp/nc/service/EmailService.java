package com.upp.nc.service;

import java.security.Key;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.upp.nc.dto.FormSubmissionDto;
import com.upp.nc.dto.UserDTO;
import com.upp.nc.model.User;
import com.upp.nc.repository.UserRepository;
import com.upp.nc.util.EmailCfg;
import com.upp.nc.util.EmailSender;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class EmailService implements JavaDelegate {

	@Autowired
	IdentityService identityService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailCfg emailCfg;
	
	@Async
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
		System.out.println(registration);
//		User user = identityService.newUser("");
        UserDTO dto = new UserDTO();

		for (FormSubmissionDto formField : registration) {
			if(formField.getFieldId().equals("username")) {
				dto.setUsername(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("password")) {
				dto.setPassword(formField.getFieldValue());
			}
		}
		
		User user = userRepository.findByUsername(dto.getUsername());
//		identityService.saveUser(user);
		Gson gson = new Gson();
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("login");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        
		JwtBuilder builder = Jwts.builder()
                .setSubject(gson.toJson(dto))
                .signWith(signatureAlgorithm, signingKey);
      
		String token = builder.compact();
		
		user.setToken(token);
		userRepository.save(user);
		
		String subject = "Confirmation mail";
		String message = "Hello, " + user.getName() + " thanks for singing up to our site, please click link to verify your email!"
				+ "\nhttp://localhost:8070/user/confirm/"+token;
		
		EmailSender.send(emailCfg, user.getEmail(), subject, message);
				
		System.out.println("http://localhost:8070/user/confirm/"+token);
		
		System.out.println("Email poslat!");
	}

}
