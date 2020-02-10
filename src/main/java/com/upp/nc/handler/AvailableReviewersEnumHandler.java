package com.upp.nc.handler;

import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.nc.util.FormFieldsEnumLoader;

@Service
public class AvailableReviewersEnumHandler implements TaskListener{

	@Autowired
	FormService formService;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		List<String> availableRevs = (List<String>)delegateTask.getExecution().getVariable("availableReviewers");
		List<FormField> formFieldList=formService.getTaskFormData(delegateTask.getId()).getFormFields();
		
		FormFieldsEnumLoader.fillValues(formFieldList, "reviewersChoice", availableRevs);
	}

}
