package com.upp.nc.handler;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.form.FormField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.nc.model.Editor;
import com.upp.nc.model.PaymentMethods;
import com.upp.nc.model.Reviewer;
import com.upp.nc.model.ScientificField;
import com.upp.nc.model.User;
import com.upp.nc.repository.UserRepository;
import com.upp.nc.util.FormFieldsEnumLoader;

@Service
public class EditorsAndReviewersEnumHandler {
	@Autowired
	FormService formService;

	@Autowired
	UserRepository userRepository;
	
	public void notify(DelegateTask delegateTask) {
		System.out.println("Setting scientific fields enum");
        List<FormField> formFieldList=formService.getTaskFormData(delegateTask.getId()).getFormFields();
        
        ArrayList<User> users = userRepository.findAll();
        ArrayList<String> editors = new ArrayList<String>();
        ArrayList<String> reviewers = new ArrayList<String>();
        for(User us : users) {
        	if ( us instanceof Editor) editors.add(us.getUsername());
        	if ( us instanceof Reviewer) reviewers.add(us.getUsername());
        }

        FormFieldsEnumLoader.fillValues(formFieldList, "editors", editors);
        FormFieldsEnumLoader.fillValues(formFieldList, "reviewers", reviewers);
        
	}
}
