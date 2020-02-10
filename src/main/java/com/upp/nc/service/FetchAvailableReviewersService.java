package com.upp.nc.service;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.nc.model.Magazine;
import com.upp.nc.model.Reviewer;
import com.upp.nc.repository.MagazineRepository;

@Service
public class FetchAvailableReviewersService implements JavaDelegate {
	
	@Autowired
	private MagazineRepository magazineRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String magName = (String) execution.getVariable("activeMagazine");
		Magazine m = magazineRepository.findByName(magName);
		
		List<String> reviewers = new ArrayList<String>();
		for (Reviewer r : m.getReviewers()) {
			reviewers.add(r.getUsername());
		}
		execution.setVariable("availableReviewers", reviewers);
		execution.setVariable("prazna", reviewers.isEmpty());
		
	}

}
