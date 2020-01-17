package com.upp.nc.service;

import java.security.Key;
import java.util.HashMap;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.upp.nc.dto.FormSubmissionDto;
import com.upp.nc.dto.UserDTO;
import com.upp.nc.model.User;
import com.upp.nc.repository.UserRepository;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@Autowired
	private UserRepository userRepository;

	
	public String registerUser(HashMap<String, Object> userMap) {
//		User user = identityService.newUser("");
		User user = new User();
        UserDTO dto = new UserDTO();
        
        dto.setUsername((String)userMap.get("username"));
        dto.setPassword((String)userMap.get("password"));
        user.setUsername((String)userMap.get("username"));
        user.setPassword((String)userMap.get("password"));
        user.setEmail((String)userMap.get("email"));
        user.setCity((String)userMap.get("grad_i_drzava"));
        user.setName((String)userMap.get("ime"));
        user.setSurname((String)userMap.get("prezime"));
        user.setState("Active");
        userRepository.save(user);
//		try {
//			this.sendMail(user, dto);
//		} catch (MailException | InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "Fail";
//		}
		return "Success";
	}

	@Async
	public void sendMail(User user, UserDTO dto) throws MailException, InterruptedException {
		System.out.println("Usao u slanje maila");
		Gson gson = new Gson();
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("login");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        
		JwtBuilder builder = Jwts.builder()
                .setSubject(gson.toJson(dto))
                .signWith(signatureAlgorithm, signingKey);
      
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(fromEmail);
		mail.setSubject("Confirmation mail");
		mail.setText("Hello, " + user.getName() + " thanks for singing up to our site, please click link to verify your email!"
				+ "\nhttp://localhost:8070/guest/confirm/?token="+builder.compact());
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}

	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public String confirmUser(String token) {
//		User u = 
		return null;
	}
}
