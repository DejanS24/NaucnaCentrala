package com.upp.nc.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.nc.model.Reviewer;
import com.upp.nc.model.User;
import com.upp.nc.repository.UserRepository;
import com.upp.nc.util.EmailCfg;
import com.upp.nc.util.EmailSender;

@Service
public class NotifyAuthorAboutRejectionService implements JavaDelegate {

	@Autowired
	private EmailCfg emailCfg;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String authorUsername = (String) execution.getVariable("workAuthor");
		String reason = (String) execution.getVariable("obrazlozenje");
		
		User rev = userRepository.findByUsername(authorUsername);
		String to = rev.getEmail();
		String subject = "Your work has been rejected";
		String message = "Your scientific work has been reviewed and it is not accepted to magazine.";
		if (reason != null) message += "\nReason: "+reason;
		EmailSender.send(emailCfg, to, subject, message);
		
	}
}
