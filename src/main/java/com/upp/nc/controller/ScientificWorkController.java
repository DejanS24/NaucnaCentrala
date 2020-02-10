package com.upp.nc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.util.json.JSONArray;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upp.nc.dto.FormFieldsDto;
import com.upp.nc.dto.FormSubmissionDto;
import com.upp.nc.dto.ReviewDTO;
import com.upp.nc.model.Magazine;
import com.upp.nc.model.User;
import com.upp.nc.service.MagazineService;
import com.upp.nc.service.ScientificWorkService;
import com.upp.nc.util.EmailSender;
import com.upp.nc.util.MapListToDTO;

@Controller
@RequestMapping("/work")
public class ScientificWorkController {
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	private ScientificWorkService scientificWorkService;
	
	@Autowired
	private MagazineService magazineService;
	
	@GetMapping(value="/choose")
	public @ResponseBody FormFieldsDto chooseMagazine() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null){
            identityService.setAuthenticatedUserId(auth.getName());
        }
        
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Proces_objave_teksta");
		System.out.println(pi.getId());
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		System.out.println(task.getAssignee());
		runtimeService.setVariable(pi.getId(), "workAuthor", auth.getName());
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();	
        return new FormFieldsDto(task.getId(), pi.getId(), properties);
	}
	
	@PostMapping(value = "/choose/{taskId}")
	public @ResponseBody ResponseEntity<String> chooseMagazine (@RequestBody List<FormSubmissionDto> magazineChoice, @PathVariable("taskId") String taskId){
		HashMap<String, Object> map = MapListToDTO.run(magazineChoice);
		System.out.println("Tuj smo");
		System.out.println(map);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String chosenMagazine = (String)map.get("casopis");
		if (chosenMagazine == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Magazine m = magazineService.getMagazine(chosenMagazine);
		if (m == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		runtimeService.setVariable(task.getProcessInstanceId(), "open_access", m.isOpenAccess());
		runtimeService.setVariable(task.getProcessInstanceId(), "chosenMagazine",chosenMagazine);
		formService.submitTaskForm(taskId, map);
		magazineService.getMagazine((String)map.get("casopis"));
		return new ResponseEntity<String>("Success", HttpStatus.OK);
		
	}
	
	@GetMapping(value="/workForm/{instanceId}")
	public @ResponseBody FormFieldsDto getWorkForm(@PathVariable("instanceId") String instanceId) {
		Task task = taskService.createTaskQuery().processInstanceId(instanceId).list().get(0);
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();	
        return new FormFieldsDto(task.getId(), instanceId, properties);
	}
	
	@PostMapping(value = "/submit/{taskId}")
	public @ResponseBody ResponseEntity<String> submitWork (@RequestBody List<FormSubmissionDto> registrationData, @PathVariable("taskId") String taskId){
		HashMap<String, Object> map = MapListToDTO.run(registrationData);
		System.out.println("Tuj smo");
		System.out.println(map);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		System.out.println(task.getAssignee());
		
//		User user = (User)runtimeService.getVariable(task.getProcessInstanceId(), "pokretac");
//		User user = null;
		
		formService.submitTaskForm(taskId, map);
		scientificWorkService.create(map, task.getAssignee());
		
		String magName = (String)runtimeService.getVariable(task.getProcessInstanceId(), "chosenMagazine");
		Magazine magazine = magazineService.getMagazine(magName);
		
		scientificWorkService.notifyAboutWorkRequest(magazine, task.getAssignee(), task.getProcessInstanceId());
		
		return new ResponseEntity<String>("Success", HttpStatus.OK);
		
	}
	
	@GetMapping(value="/editorStep/{instanceId}")
	public @ResponseBody FormFieldsDto workRelevantForm(@PathVariable("instanceId") String instanceId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null){
            identityService.setAuthenticatedUserId(auth.getName());
        }
        
		Task task = taskService.createTaskQuery().processInstanceId(instanceId).list().get(0);

		System.out.println(task.getAssignee());
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();	
        return new FormFieldsDto(task.getId(), instanceId, properties);
		
	}
	
	@PostMapping(value="/editorStep/{taskId}")
	public ResponseEntity<String> isWorkRelevant(@RequestBody List<FormSubmissionDto> formData, @PathVariable("taskId") String taskId){
		HashMap<String, Object> map = MapListToDTO.run(formData);
		System.out.println(map);
		formService.submitTaskForm(taskId, map);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}


//
//	@GetMapping(value="/reject/{instanceId}")
//	public @ResponseBody FormFieldsDto workRejectForm(@PathVariable("instanceId") String instanceId) {
//		Task task = taskService.createTaskQuery().processInstanceId(instanceId).list().get(0);
//		TaskFormData tfd = formService.getTaskFormData(task.getId());
//		List<FormField> properties = tfd.getFormFields();	
//        return new FormFieldsDto(task.getId(), instanceId, properties);
//	}
//	
//	@PostMapping(value="/reject/{taskId}")
//	public ResponseEntity<String> rejectWork(@RequestBody List<FormSubmissionDto> formData, @PathVariable("taskId") String taskId){
//		HashMap<String, Object> map = MapListToDTO.run(formData);
//		System.out.println(map);
////		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
////		String processInstanceId = task.getProcessInstanceId();
////		runtimeService.setVariable(processInstanceId, "relevant", (String)map.get("relevant"));
//		formService.submitTaskForm(taskId, map);
//		return new ResponseEntity<String>("Success", HttpStatus.OK);
//		
//	}
//	
//	@GetMapping(value="/chooseReviewers/{instanceId}")
//	public @ResponseBody FormFieldsDto chooseReviewersForm(@PathVariable("instanceId") String instanceId) {
//		Task task = taskService.createTaskQuery().processInstanceId(instanceId).list().get(0);
//		TaskFormData tfd = formService.getTaskFormData(task.getId());
//		List<FormField> properties = tfd.getFormFields();
//		
//        return new FormFieldsDto(task.getId(), instanceId, properties);
//	}
//	
	@PostMapping(value="/chooseReviewers/{taskId}")
	public ResponseEntity<String> chooseReviewers(@RequestBody List<FormSubmissionDto> formData, @PathVariable("taskId") String taskId){
		HashMap<String, Object> map = MapListToDTO.run(formData);
		System.out.println(map);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		System.out.println(map.get("reviewersChoice"));
		
		ArrayList<String> reviewers = new ArrayList<String>();
//		JSONArray jsonArray = (JSONArray)map.get("reviewersChoice"); 
//		if (jsonArray != null) { 
//		   int len = jsonArray.length();
//		   for (int i=0;i<len;i++){ 
//		    reviewers.add(jsonArray.get(i).toString());
//		   } 
//		} 
		reviewers.add((String)map.get("reviewersChoice"));
		runtimeService.setVariable(processInstanceId, "selectedReviewers", reviewers);
		runtimeService.getVariables(processInstanceId);
		formService.submitTaskForm(taskId, map);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
		
	}
	
	@GetMapping(value="/review/{instanceId}")
	public @ResponseBody FormFieldsDto reviewForm(@PathVariable("instanceId") String instanceId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null){
            identityService.setAuthenticatedUserId(auth.getName());
        }
        
		Task task = taskService.createTaskQuery().processInstanceId(instanceId).list().get(0);

		System.out.println(task.getAssignee());
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();	
        return new FormFieldsDto(task.getId(), instanceId, properties);
	}
	
	@PostMapping(value="/review/{taskId}")
	public ResponseEntity<String> submitReview(@RequestBody List<FormSubmissionDto> formData, @PathVariable("taskId") String taskId){
		HashMap<String, Object> map = MapListToDTO.run(formData);
		System.out.println(map);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		ArrayList<ReviewDTO> reviews = (ArrayList<ReviewDTO>) runtimeService.getVariable(processInstanceId, "finishedReviews");
		ReviewDTO rev = new ReviewDTO();
		rev.setReview_pass(Boolean.getBoolean(((String)map.get("review_pass"))));
		rev.setAuthor_comments((String)map.get("author_comments"));
		rev.setEditor_comments((String)map.get("editor_comments"));
		
		reviews.add(rev);
		runtimeService.setVariable(processInstanceId, "finishedReviews", reviews);
		
		formService.submitTaskForm(taskId, map);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
		
	}
	
	@GetMapping(value="/correction/{instanceId}")
	public @ResponseBody FormFieldsDto reviewCorrection(@PathVariable("instanceId") String instanceId) {
		Task task = taskService.createTaskQuery().processInstanceId(instanceId).list().get(0);
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();	
        return new FormFieldsDto(task.getId(), instanceId, properties);
	}
	
	@PostMapping(value="/correction/{taskId}")
	public ResponseEntity<String> submitCorrectedWork(@RequestBody List<FormSubmissionDto> formData, @PathVariable("taskId") String taskId){
		HashMap<String, Object> map = MapListToDTO.run(formData);
		System.out.println(map);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "relevant", (String)map.get("relevant"));
		formService.submitTaskForm(taskId, map);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
		
	}
}
