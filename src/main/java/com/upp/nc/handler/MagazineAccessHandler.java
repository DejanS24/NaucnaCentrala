package com.upp.nc.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.nc.model.Magazine;
import com.upp.nc.repository.MagazineRepository;
import com.upp.nc.util.FormFieldsEnumLoader;

@Service
public class MagazineAccessHandler implements TaskListener {

	@Autowired
	private MagazineRepository magazineRepository;

	@Autowired
	FormService formService;

	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println("Getting magazines for enum");
		DelegateExecution exec = delegateTask.getExecution();
		String magName = (String) exec.getVariable("casopis");
		Magazine m = magazineRepository.findByName(magName);
		
		exec.setVariable("open_access", m.isOpenAccess());
		
		exec.setVariable("chiefEditor", m.getChiefEditor().getUsername());
	}

}
