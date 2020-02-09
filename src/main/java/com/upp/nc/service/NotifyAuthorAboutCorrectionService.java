package com.upp.nc.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.nc.util.EmailCfg;
import com.upp.nc.util.EmailSender;

@Service
public class NotifyAuthorAboutCorrectionService implements JavaDelegate {

	@Autowired
	private EmailCfg emailCfg;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String to = "";
		String subject = "";
		String message = "";
		EmailSender.send(emailCfg, to, subject, message);
		
	}

}
