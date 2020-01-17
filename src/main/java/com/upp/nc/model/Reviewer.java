package com.upp.nc.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Reviewer extends User {

	@ManyToMany
	@JsonBackReference
	private Collection<Magazine> magazines;

	@Column
	@OneToMany
	private Collection<ScientificWork> scientificWorks;

	public Collection<Magazine> getMagazines() {
		return magazines;
	}

	public void setMagazines(Collection<Magazine> magazines) {
		this.magazines = magazines;
	}

	public Collection<ScientificWork> getScientificWorks() {
		return scientificWorks;
	}

	public void setScientificWorks(Collection<ScientificWork> scientificWorks) {
		this.scientificWorks = scientificWorks;
	}
	
	@Override
	public String getUserAuthorities() {
		return "Reviewer";
	}
	
}
