package com.upp.nc.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.nc.repository.MagazineRepository;
import com.upp.nc.util.FormFieldsEnumLoader;

@Service
public class MagazinesEnumHandler implements TaskListener {

	@Autowired
	private MagazineRepository magazineRepository;

	@Autowired
	FormService formService;

	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println("Getting magazines for enum");
		List<FormField> formFieldList=formService.getTaskFormData(delegateTask.getId()).getFormFields();

		List<String> magazines = magazineRepository.findAll().stream().map(m -> m.getName()).collect(Collectors.toList());
       
        FormFieldsEnumLoader.fillValues(formFieldList, "casopis", magazines);
        
	}

}
