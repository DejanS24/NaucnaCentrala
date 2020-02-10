package com.upp.nc.service;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.nc.model.Editor;
import com.upp.nc.model.Magazine;
import com.upp.nc.repository.MagazineRepository;
import com.upp.nc.repository.UserRepository;
import com.upp.nc.util.EmailCfg;
import com.upp.nc.util.EmailSender;

@Service
public class SetChiefEditorAsSciFieldEditorService implements JavaDelegate {
	
	@Autowired
	private MagazineRepository magazineRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String sciField = (String) execution.getVariable("naucna_oblast");
		System.out.println(sciField);

		String magName = (String) execution.getVariable("activeMagazine");
		Magazine m = magazineRepository.findByName(magName);
	
		if (m.getScientificFieldEditors() == null) m.setScientificFieldEditors(new HashMap<String, String>());
		m.getScientificFieldEditors().put(sciField, m.getChiefEditor().getUsername());
		magazineRepository.save(m);
	}

}
