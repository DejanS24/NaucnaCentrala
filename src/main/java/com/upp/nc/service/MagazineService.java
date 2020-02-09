package com.upp.nc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.nc.model.Magazine;
import com.upp.nc.repository.MagazineRepository;

@Service
public class MagazineService {

	@Autowired
	private MagazineRepository magazineRepository;
	
	
	public Magazine getMagazine(String name) {
		Magazine m = magazineRepository.findByName(name);
		return m;
	}
	
	public List<Magazine> getAllMagazines(){
		return magazineRepository.findAll();
	}
}
