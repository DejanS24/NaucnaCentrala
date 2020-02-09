package com.upp.nc.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.nc.dto.FormSubmissionDto;
import com.upp.nc.model.Reviewer;
import com.upp.nc.model.User;
import com.upp.nc.repository.UserRepository;

@Service
public class NewUserTypeService implements JavaDelegate  {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
		
		String username = "";
		for (FormSubmissionDto formField : registration) {
			if(formField.getFieldId().equals("username")) {
				username = formField.getFieldValue();
			}
		}
		if (username.equals("")) return;
		User u = userRepository.findByUsername(username);
		Reviewer r = new Reviewer(u);
		userRepository.save(r);
		userRepository.delete(u);
	}

}
