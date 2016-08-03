package com.rex.springmvc.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "StudentB")
@NamedQuery(name = "s2", query = "select s from StudentB s where s.cr.id=:id")
public class StudentB {

	private int id;
	private String name;
	private ClassRoomB cr;
	private Set<TeacherB> teachers = new HashSet<TeacherB>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "cr_id")
	public ClassRoomB getCr() {
		return cr;
	}

	public void setCr(ClassRoomB cr) {
		this.cr = cr;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "teacher_student_b", joinColumns = { @JoinColumn(name = "student_id") }, inverseJoinColumns = {
			@JoinColumn(name = "teacher_id") })
	public Set<TeacherB> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<TeacherB> teachers) {
		this.teachers = teachers;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getId() + "-" + this.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof StudentB)) {
			return false;
		}
		StudentB student = (StudentB) obj;
		if (student.getId() == this.getId()) {
			return true;
		}
		return false;

	}
}
