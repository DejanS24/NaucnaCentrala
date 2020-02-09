package com.upp.nc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upp.nc.model.Magazine;

public interface MagazineRepository extends JpaRepository<Magazine, Long> {
	Magazine findByName(String name);
}
