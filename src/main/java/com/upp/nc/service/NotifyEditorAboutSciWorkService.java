package com.upp.nc.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.nc.model.Editor;
import com.upp.nc.model.Magazine;
import com.upp.nc.model.Reviewer;
import com.upp.nc.model.User;
import com.upp.nc.repository.MagazineRepository;
import com.upp.nc.repository.UserRepository;
import com.upp.nc.util.EmailCfg;
import com.upp.nc.util.EmailSender;

@Service
public class NotifyEditorAboutSciWorkService implements JavaDelegate {

	@Autowired
	private EmailCfg emailCfg;
	
	@Autowired
	private MagazineRepository magazineRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String sciField = (String) execution.getVariable("naucna_oblast");
		System.out.println(sciField);

		System.out.println(execution.getVariables());
		String magName = (String) execution.getVariable("activeMagazine");
		Magazine m = magazineRepository.findByName(magName);
		String editorName = "";
		if (m.getScientificFieldEditors() != null) {
			editorName = m.getScientificFieldEditors().get(sciField);
		}
		Editor ed = (Editor) userRepository.findByUsername(editorName);
		if (ed == null) {
			execution.setVariable("urednik_postoji", false);
			ed = m.getChiefEditor();
		}else {
			execution.setVariable("urednik_postoji", true);
		}
		execution.setVariable("chosenEditor", ed.getUsername());
		String to = ed.getEmail();
		String subject = "Please select reviewers for new scientific work";
		String message = "A new scientific work has been submitted to magazine. Please select reviewers for this work in your scientific fields.\n"+
						"http://localhost:4200/setReviewers/"+execution.getProcessInstance().getId();
		EmailSender.send(emailCfg, to, subject, message);
		
	}
}
