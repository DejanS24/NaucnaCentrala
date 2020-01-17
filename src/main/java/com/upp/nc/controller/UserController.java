package com.upp.nc.controller;

import java.util.ArrayList;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upp.nc.dto.FormFieldsDto;
import com.upp.nc.dto.FormSubmissionDto;
import com.upp.nc.dto.LoginDTO;
import com.upp.nc.dto.TaskDto;
import com.upp.nc.model.User;
import com.upp.nc.security.TokenUtils;
import com.upp.nc.service.UserService;
import com.upp.nc.util.MapListToDTO;

@Controller
@RequestMapping("/user")
public class UserController {
	
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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@GetMapping(value = "/{username}")
    public User getUserByUsername(@PathVariable String username) {
        User user = userService.findUserByUsername(username);

        return user;
    }
	
	@GetMapping(path = "/getRegForm", produces = "application/json")
    public @ResponseBody FormFieldsDto getRegisterForm() {
		//provera da li korisnik sa id-jem pera postoji
		//List<User> users = identityService.createUserQuery().userId("pera").list();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("User_registration");
		System.out.println(pi.getId());
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();	
        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }
	
	@PostMapping(value = "/register/{taskId}")
	public ResponseEntity<String> register (@RequestBody List<FormSubmissionDto> registrationData, @PathVariable("taskId") String taskId){
		HashMap<String, Object> map = MapListToDTO.run(registrationData);
		System.out.println("Tuj smo");
		System.out.println(map);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "registration", registrationData);
		formService.submitTaskForm(taskId, map);
		userService.registerUser(map);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
		
	}


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        try {
        	// Perform the authentication
        	UsernamePasswordAuthenticationToken token = 
        			new UsernamePasswordAuthenticationToken(
					loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Reload user details so we can generate token
            UserDetails details = userDetailsService.
            		loadUserByUsername(loginDTO.getUsername());
            return new ResponseEntity<String>(
            		tokenUtils.generateToken(details), HttpStatus.OK);
        } catch (Exception ex) {
        	System.out.println(ex.getMessage());
            return new ResponseEntity<String>("Invalid login", HttpStatus.BAD_REQUEST);
        }
	}
	
	@GetMapping(path = "/confirm/{token}")
	public ResponseEntity<String> confirmMail(@PathVariable("token") String token){
		
		return new ResponseEntity<String>("Confirmed.", HttpStatus.OK);
	}
	
	
}
