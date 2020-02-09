package com.upp.nc.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.nc.dto.ScientificWorkDTO;
import com.upp.nc.model.Magazine;
import com.upp.nc.model.ScientificWork;
import com.upp.nc.model.User;
import com.upp.nc.repository.ScientificWorkRepository;
import com.upp.nc.repository.UserRepository;
import com.upp.nc.util.EmailCfg;
import com.upp.nc.util.EmailSender;

@Service
public class ScientificWorkService {
	
	@Autowired
	private EmailCfg emailCfg;
	
	@Autowired
	private ScientificWorkRepository scientificWorkRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public String create(HashMap<String, Object> sciWorkMap, String username) {
		
		ScientificWork sciWork = new ScientificWork();
		sciWork.setTitle((String)sciWorkMap.get("naslov"));
		sciWork.setWorkAbstract((String)sciWorkMap.get("tekst"));
		User user = userRepository.findByUsername(username);
		sciWork.setAuthor(user);
		scientificWorkRepository.save(sciWork);
		return "Success";
	}

	public void notifyAboutWorkRequest(Magazine magazine, String author) {
		
		String subject = "New request for scientific work";
		String message = "New scientific work has been created by user "+author+
				". Please review the work. link";
		
		EmailSender.send(emailCfg, magazine.getChiefEditor().getEmail(), subject, message);
		
		String subject2 = "Scientific work request created";
		String message2 = "Your request for scientific work has been created. Work acceptance is pending.";
		
		User u = userRepository.findByUsername(author);
		EmailSender.send(emailCfg, u.getEmail(), subject2, message2);

		
	}
	
	

}
