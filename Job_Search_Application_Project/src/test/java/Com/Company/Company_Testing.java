package Com.Company;

import org.junit.jupiter.api.Test;

import com.JobSearch.Entity.*;
import com.JobSearchDao.*;

import static org.junit.jupiter.api.Assertions.*;
public class Company_Testing {
	
	CompanyDao companydao;

//	 
	 @Test
 public void testNewCompanyInsert() 
	 {

		Companies c = new Companies();
		   
		companydao = new CompanyDao();
		    
		    		    
		    c.setCompany_Name("Capgemini");
		    c.setCompany_Email("capgemini@gmail.com");
		    c.setCompany_Address("Gachibowli");
			
	        // Perform login
	        //boolean result = bookdao.TestsaveBook(b);

	        // Assert that login is successful
	        assertTrue(companydao.TestsaveCompany(c));
	    }	

}