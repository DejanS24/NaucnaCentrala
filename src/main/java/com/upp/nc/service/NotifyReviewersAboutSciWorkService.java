package com.upp.nc.service;

import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.nc.dto.ReviewDTO;
import com.upp.nc.model.Reviewer;
import com.upp.nc.repository.UserRepository;
import com.upp.nc.util.EmailCfg;
import com.upp.nc.util.EmailSender;

@Service
public class NotifyReviewersAboutSciWorkService implements JavaDelegate{

	@Autowired
	private EmailCfg emailCfg;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		ArrayList<String> reviewers = (ArrayList<String>) execution.getVariable("selectedReviewers");
		System.out.println(execution.getVariable("activeMagazine"));
		for (String reviewer : reviewers) {
			Reviewer rev = (Reviewer) userRepository.findByUsername(reviewer);
			String to = rev.getEmail();
			String subject = "Review this scientific work";
			String message = "Please review this scientific work for magazine.";
			EmailSender.send(emailCfg, to, subject, message);
		}
		
		execution.setVariable("finishedReviews", new ArrayList<ReviewDTO>());
	}

}
