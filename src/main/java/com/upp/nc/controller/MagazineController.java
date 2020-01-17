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
    public @ResponseBody FormFieldsDto getMagazineForm() {
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
	public ResponseEntity<String> createMagazine (@RequestBody List<FormSubmissionDto> magazineData, @PathVariable("taskId") String taskId){
		HashMap<String, Object> map = MapListToDTO.run(magazineData);
		System.out.println("Tuj smo");
		System.out.println(map);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "magazine", magazineData);
		formService.submitTaskForm(taskId, map);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
		
	}
	
	@GetMapping(path = "/editorsForm/{taskId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getEditorsForm(@PathVariable("taskId") String taskId) {
		//provera da li korisnik sa id-jem pera postoji
		//List<User> users = identityService.createUserQuery().userId("pera").list();
		Task task = taskService.createTaskQuery().processInstanceId(taskId).list().get(0);

		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
        return new FormFieldsDto(task.getId(), taskId, properties);
    }
	
	@PostMapping(value = "/fillEditors/{taskId}")
	public ResponseEntity<String> addEditors (@RequestBody List<FormSubmissionDto> magazineData, @PathVariable("taskId") String taskId){
		HashMap<String, Object> map = MapListToDTO.run(magazineData);
		System.out.println("Tuj smo");
		System.out.println(map);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "magazine", magazineData);
		formService.submitTaskForm(taskId, map);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
		
	}
	
}
