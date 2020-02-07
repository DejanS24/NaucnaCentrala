package com.upp.nc.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upp.nc.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	User findByToken(String token);
	ArrayList<User> findAll();
}
