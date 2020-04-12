package com.upp.nc.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Editor extends User {

//	@ManyToOne(optional = true)
//	@JsonBackReference
//	private Magazine magazine;
	
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Magazine magazine;

//	@Column
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "editor_id")
//	private Collection<ScientificWork> scientificWorks;

//	public Magazine getMagazine() {
//		return magazine;
//	}
//
//	public void setMagazine(Magazine magazine) {
//		this.magazine = magazine;
//	}

//	public Collection<ScientificWork> getScientificWorks() {
//		return scientificWorks;
//	}
//
//	public void setScientificWorks(Collection<ScientificWork> scientificWorks) {
//		this.scientificWorks = scientificWorks;
//	}

	@Override
	public String getUserAuthorities() {
		return "Editor";
	}
}
