package com.rex.springmvc.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rex.springmvc.configuration.HibernateTestConfiguration;
import com.rex.springmvc.model.ClassRoom;
import com.rex.springmvc.model.Student;

@ContextConfiguration(classes = { HibernateTestConfiguration.class })
@WebAppConfiguration()
@org.springframework.transaction.annotation.Transactional
public class JPAtest extends AbstractTransactionalTestNGSpringContextTests {

	@PersistenceContext
	EntityManager em;

	@BeforeMethod
	public void Setup() {
		prepareData();

	}

	@AfterMethod
	public void Destroy() {

	}

	@Test
	@Rollback(false)
	public void test1() {

		ClassRoom cr = new ClassRoom();
		cr = em.find(ClassRoom.class, 1);

		Student student = em.find(Student.class, 1);
		student.setName("xxxxxxxxxxxx");
		cr.setName("aaaaaa");
		student.setCr(cr);
		em.merge(student);

		Student st = new Student();
		st.setName("aaaaaaaaaaa");
		st.setCr(cr);
		em.merge(st);
		
		Student sta = new Student();
		sta.setName("bbbbbbbbbbbbbbb");
		sta.setCr(cr);
		//cr.setStudent(new HashSet<>(Arrays.asList(sta)));
		em.merge(sta);
//		//cr.setStudent(new HashSet<>(Arrays.asList(st)));
//		
//		em.merge(cr);
	}

	public void prepareData() {
		Student st1 = new Student();
		st1.setName("st1");
		
		Student st2 = new Student();
		st2.setName("st2");

		ClassRoom cr = new ClassRoom();
		cr.setName("cr1");
		//st1.setCr(cr);
		Set<Student> se = new HashSet<>();
		se.add(st1);
		se.add(st2);
		cr.setStudent(se);
		// st1.setCr(cr1);
		em.persist(cr);

		// Student st2=new Student();
		// st2.setName("st2");
		// em.persist(st2);
		//
		// ClassRoom cr2 =new ClassRoom();
		// cr2.setName("cr2");
		// em.persist(cr2);

	}

}
