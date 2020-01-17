package com.upp.nc.controller;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upp.nc.dto.FormFieldsDto;
import com.upp.nc.dto.FormSubmissionDto;
import com.upp.nc.util.MapListToDTO;

@Controller
@RequestMapping("/magazine")
public class MagazineController {
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@GetMapping(path = "/getForm", produces = "application/json")
    public @ResponseBody FormFieldsDto getRegisterForm() {
		//provera da li korisnik sa id-jem pera postoji
		//List<User> users = identityService.createUserQuery().userId("pera").list();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Magazine_creation");
		System.out.println(pi.getId());
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();	
        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }
	
	@PostMapping(value = "/fillForm/{taskId}")
	public ResponseEntity<String> register (@RequestBody List<FormSubmissionDto> registrationData, @PathVariable("taskId") String taskId){
		HashMap<String, Object> map = MapListToDTO.run(registrationData);
		System.out.println("Tuj smo");
		System.out.println(map);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "registration", registrationData);
		formService.submitTaskForm(taskId, map);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
		
	}
	
}
