package com.upp.nc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.upp.nc.model.User;
import com.upp.nc.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    
    try {
    System.out.println(user.getUsername() + "<- Nadjeni ->" + user.getPassword());	
    }catch(Exception e) {
    	
    }

    if (user == null) {
      throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
    } else {
//    	List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
//    	for (UserAuthority ua: user.getUserAuthorities()) {
//    		grantedAuthorities.add(
//    			new SimpleGrantedAuthority(ua.getAuthority().getName()));
//    	}
    	
    	//Java 1.8 way   	
    	List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
    	
    	grantedAuthorities.add(new SimpleGrantedAuthority(user.getUserAuthorities()));
    	
    	return new org.springframework.security.core.userdetails.User(
    		  user.getUsername(),
    		  user.getPassword(),
    		  grantedAuthorities);
    }
  }

}
