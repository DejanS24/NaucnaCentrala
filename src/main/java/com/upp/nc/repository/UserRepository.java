package com.upp.nc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.upp.nc.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
