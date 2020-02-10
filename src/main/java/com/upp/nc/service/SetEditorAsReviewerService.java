package com.upp.nc.service;

import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.upp.nc.model.Editor;
import com.upp.nc.model.Magazine;
import com.upp.nc.repository.MagazineRepository;
import com.upp.nc.repository.UserRepository;
import com.upp.nc.util.EmailCfg;
import com.upp.nc.util.EmailSender;

public class SetEditorAsReviewerService implements JavaDelegate {

	@Autowired
	private EmailCfg emailCfg;
	
	@Autowired
	private MagazineRepository magazineRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String edUsername = (String)execution.getVariable("chosenEditor");
		
		ArrayList<String> reviewers = new ArrayList<String>();
		reviewers.add(edUsername);
		execution.setVariable("selectedReviewers", reviewers);
	}
}
