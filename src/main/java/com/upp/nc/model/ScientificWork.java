package com.upp.nc.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ScientificWork {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@NotNull
    @OneToOne
    private User author;

    @Column
    private String koautori;

    @Column(nullable = false)
    private String workAbstract;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ScientificField scientificField;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Magazine magazine;

    @Column
    private boolean isAccepted;
}
