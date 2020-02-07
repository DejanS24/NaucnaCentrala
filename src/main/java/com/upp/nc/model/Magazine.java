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
import javax.persistence.FetchType;
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

    @Column(nullable = false, length=256)
    private String name;

    @Column(nullable = false, length=256)
    private String issnNumber;
	
	@Column
    @OneToMany(cascade=CascadeType.ALL, mappedBy="magazine")
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
    private Collection<User> reviewers;

}
