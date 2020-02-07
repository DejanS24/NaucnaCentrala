package com.upp.nc.util;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;

import com.upp.nc.model.ScientificField;

public class FormFieldsEnumLoader {
	
	public static void fillValues(List<FormField> formFieldList, String id, List<String> enumValues) {
		if(formFieldList == null) return;
		
        for(FormField field : formFieldList){
            if(field.getId().equals(id)){
                Map<String, String> items = ((EnumFormType)field.getType()).getValues();
                for(String value : enumValues) {
                	items.put(value.toLowerCase(), value);
                }
            }
        }
        
	}

}
