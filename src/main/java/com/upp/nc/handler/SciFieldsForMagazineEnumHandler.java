package com.upp.nc.handler;

import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.nc.model.PaymentMethods;
import com.upp.nc.model.ScientificField;
import com.upp.nc.util.FormFieldsEnumLoader;

@Service
public class SciFieldsForMagazineEnumHandler implements TaskListener {

	@Autowired
	FormService formService;

	public void notify(DelegateTask delegateTask) {
		System.out.println("Setting scientific fields enum");
        List<FormField> formFieldList=formService.getTaskFormData(delegateTask.getId()).getFormFields();

        
        FormFieldsEnumLoader.fillValues(formFieldList, "naucna_oblasti", ScientificField.getFields());
        
	}

}
