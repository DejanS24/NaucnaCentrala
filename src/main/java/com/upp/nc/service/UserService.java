package com.upp.nc.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.upp.nc.model.User;
import com.upp.nc.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	
	public String registerUser(HashMap<String, Object> userMap) {
		User user = new User();
        
        System.out.println(userMap);
        System.out.println(userMap.get("ime"));
        
        user.setUsername((String)userMap.get("username"));
        user.setEmail((String)userMap.get("email"));
        user.setCity((String)userMap.get("grad_i_drzava"));
        user.setName((String)userMap.get("ime"));
        user.setSurname((String)userMap.get("prezime"));
        user.setState("Inactive");
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode((String)userMap.get("password")));
        userRepository.save(user);
		return "Success";
	}


	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public String confirmUser(String token) {
		User u = userRepository.findByToken(token);
		if (u != null) {
			u.setState("Active");
			userRepository.save(u);
			return "Confirmed";
		}else {
			return null;			
		}
	}
}
