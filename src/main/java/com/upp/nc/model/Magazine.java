package com.upp.nc.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Magazine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 256)
	private String name;

	@Column(nullable = false, length = 256)
	private String issnNumber;

	@Column
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "magazine")
	@JsonBackReference
	private Collection<ScientificWork> scientificWorks;

	@NotNull
	@ElementCollection(targetClass = ScientificField.class)
	@CollectionTable(name = "magazine_scientificField", joinColumns = @JoinColumn(name = "magazine_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "scientificField_id")
	private List<ScientificField> scientificFields;

	@Column(nullable = false)
	private boolean isOpenAccess;

	@OneToOne
	private Editor chiefEditor;

	@Column
	private HashMap<String, String> scientificFieldEditors;

	@Column
	@ManyToMany
	@JsonBackReference
	private Collection<Reviewer> reviewers;

	@Column
	private String state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIssnNumber() {
		return issnNumber;
	}

	public void setIssnNumber(String issnNumber) {
		this.issnNumber = issnNumber;
	}

	public Collection<ScientificWork> getScientificWorks() {
		return scientificWorks;
	}

	public void setScientificWorks(Collection<ScientificWork> scientificWorks) {
		this.scientificWorks = scientificWorks;
	}

	public List<ScientificField> getScientificFields() {
		return scientificFields;
	}

	public void setScientificFields(List<ScientificField> scientificFields) {
		this.scientificFields = scientificFields;
	}

	public boolean isOpenAccess() {
		return isOpenAccess;
	}

	public void setOpenAccess(boolean isOpenAccess) {
		this.isOpenAccess = isOpenAccess;
	}

	public Editor getChiefEditor() {
		return chiefEditor;
	}

	public void setChiefEditor(Editor chiefEditor) {
		this.chiefEditor = chiefEditor;
	}

	public HashMap<String, String> getScientificFieldEditors() {
		return scientificFieldEditors;
	}

	public void setScientificFieldEditors(HashMap<String, String> scientificFieldEditors) {
		this.scientificFieldEditors = scientificFieldEditors;
	}

	public Collection<Reviewer> getReviewers() {
		return reviewers;
	}

	public void setReviewers(Collection<Reviewer> reviewers) {
		this.reviewers = reviewers;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
