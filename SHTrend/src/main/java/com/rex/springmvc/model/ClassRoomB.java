package com.rex.springmvc.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ClassRoomB")
public class ClassRoomB {

	private int id;
	private String name;
	private List<StudentB> student = new ArrayList();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cr", orphanRemoval = true)
	// @JoinColumn(name="cr_id")
	@JsonIgnore
	public List<StudentB> getStudent() {
		return student;
	}

	public void setStudent(List<StudentB> student) {
		this.student = student;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "--" + this.id + "--" + this.name;
	}

}
