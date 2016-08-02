package com.rex.springmvc.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rex.springmvc.configuration.HibernateTestConfiguration;
import com.rex.springmvc.model.ClassRoom;
import com.rex.springmvc.model.Student;
import com.rex.springmvc.model.Teacher;

@ContextConfiguration(classes = { HibernateTestConfiguration.class })
@WebAppConfiguration()
//@org.springframework.transaction.annotation.Transactional
public class JPAtest extends AbstractTransactionalTestNGSpringContextTests {

	@PersistenceContext
	EntityManager em;
	ClassRoom cr;

	// @BeforeMethod
	public void Setup() {
		prepareData();

	}

	// @AfterMethod
	public void Destroy() {
		em.remove(cr);
	}

	// @Test
	// @Rollback(false)
	public void test3() {
		// Student st = em.find(Student.class, 1);
		// em.remove(st);
		// ClassRoom cr = em.find(ClassRoom.class, 1);
		// cr.getStudent().remove(st);
		// em.persist(cr);
		em.remove(cr);
		Assert.assertEquals(em.createQuery("select c from ClassRoom c").getResultList().size(), 0);

	}
	
	@Test
	public void test6(){
		List<Object> ls=em.createNamedQuery("s1").setParameter("id", 1).getResultList();
		List<Object> ls1=em.createNamedQuery("s2").setParameter("id", 1).getResultList();
		ls.size();
		
	}
	
	@Test
	@Rollback(false)
	public void test7(){
		
		//单向关联增删改查
		Student st = em.find(Student.class, 1);
		st.setName("dachui");
		
		Student st1 = new Student();
		st1.setName("xiaofeng");
		cr = em.find(ClassRoom.class, 1);
		cr.getStudent().add(st1);
		//em.merge(cr);
		Student st2 = em.find(Student.class, 2);
		cr.getStudent().remove(st2);
		//em.merge(cr);
		em.remove(st2);
	}

	@Test
	@Rollback(false)
	public void test5() {
		
		
		//多对多:级联删除学生 老师
//		Student st1 = em.find(Student.class, 1);
//		em.remove(st1);

		
		//多对多:只删除中间表绑定关系
//		Student st1 = em.find(Student.class, 1);
//		Teacher th1=em.find(Teacher.class, 1);
//		st1.getTeachers().remove(th1);
		
	
		
//		st.setName("dachui");
//		
//		Student st1 = new Student();
//		st1.setName("xiaofeng");
//		cr = em.find(ClassRoom.class, 1);
//		cr.getStudent().add(st1);
//		em.persist(st);
//		
//		st = em.find(Student.class, 1);
//		em.remove(st);
		// cr = em.find(ClassRoom.class, 1);
		// cr.getStudent().remove(st);
		// st.setCr(null);
		//em.remove(st);
		// em.persist(cr);
		//Student st1 = em.find(Student.class, 1);
		//.assertEquals(st1,null);
		// cr= em.find(ClassRoom.class, 1);
	}

	@Test
	@Rollback(false)
	public void test4() {
		prepareData();
	}

	// @Test
	public void test2() {
		List<Student> lt = em.createNamedQuery("s1", Student.class).setParameter("id", 2).getResultList();
		Assert.assertEquals(lt.size(), 2);
	}



	public void prepareData() {

		Student st1 = new Student();
		st1.setName("st1");

		Student st2 = new Student();
		st2.setName("st2");
		
		Teacher th1 = new Teacher();
		th1.setName("th1");
		//th1.getStudent().add(st1);
		st1.getTeachers().add(th1);

		cr = new ClassRoom();
		cr.setName("cr1");

		List<Student> se = new ArrayList<>();
		se.add(st1);
		se.add(st2);

		cr.setStudent(se);
		em.persist(cr);
		// em.persist(st1);
		// em.persist(st2);

		// Student st1 = new Student();
		// st1.setName("st1");
		//
		// Student st2 = new Student();
		// st2.setName("st2");
		//
		// cr = new ClassRoom();
		// cr.setName("cr1");
		// // st1.setCr(cr);
		// List<Student> se = new ArrayList<>();
		// se.add(st1);
		// se.add(st2);
		// cr.setStudent(se);
		// // st1.setCr(cr1);
		// em.persist(cr);
		//
//		Teacher th1 = new Teacher();
//		th1.setName("th1");
//		th1.getStudent().add(st1);
		//st1.getTeachers().add(th1);
		//em.persist(th1);

		// Student st2=new Student();
		// st2.setName("st2");
		// em.persist(st2);
		//
		// ClassRoom cr2 =new ClassRoom();
		// cr2.setName("cr2");
		// em.persist(cr2);

	}

}
