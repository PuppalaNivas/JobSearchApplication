package Com.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.JobSearch.Entity.*;
import com.JobSearch.Util.Job_Search_Util;
import com.JobSearchDao.*;

import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import jakarta.persistence.criteria.CriteriaQuery;

public class Student_Testing {
	
	StudentDao studentdao;
	
//	 @BeforeEach
//	    public void setUp() {
//	        // bookdao while inserting new book details
//		 studentdao = new StudentDao();
//	     
//	    }
//	 
	 @Test
	    public void testNewStudentInsert() 
	 {
		Student s = new Student();
		
		   
		    studentdao = new StudentDao();
		    
		    		    
		    s.setFirst_Name("Nivas");
		    s.setLast_Name("Puppala");
		    s.setPhone_number("8932422452");
		    s.setEmail("nivas@gmail.com");
		    s.setSkills(" Java,MySQL,Hibernate");
		    s.setResume("myresume.pdf");
		    s.setEducation("Degree");
		    s.setExperience("Fresher");
			
			
	        // Perform login
	        //boolean result = bookdao.TestsaveBook(b);

	        // Assert that login is successful
	        assertTrue(studentdao.TestsaveStudent(s));
	    }	

}