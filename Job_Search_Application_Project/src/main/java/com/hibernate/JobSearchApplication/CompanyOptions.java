package com.hibernate.JobSearchApplication;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import com.JobSearch.Entity.*;
import com.JobSearch.Util.Job_Search_Util;
import com.JobSearchDao.*;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CompanyOptions 
{

    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        

        try {
            companyOptions(scanner);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            scanner.close(); // Close the scanner to prevent resource leak
        }
    }
   

    public static void companyOptions(Scanner scanner) throws ParseException {
    	System.out.println("");
        System.out.println("1. Enter new company details");
        System.out.println("2. Post a new job");
        System.out.println("3. View applied students for a job");
        System.out.println("4. Update Companies Detail");
        System.out.println("5. Update posted Jobs ");
        System.out.println("6. Exit");
        System.out.print("Enter your choice (1/2/3/4/5/6): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) 
        {
       
              case 1:
        
        	  enterCompanyDetails(scanner);
        	  companyOptions(scanner);
             break;
        
            case 2: 
            	           	    		            	
            	CompanyOptions.postNewJob(scanner);            	
                    companyOptions(scanner);
            break;                                                                         
            case 3:
            	
            	viewStudentsWithAppliedJobs();
                companyOptions(scanner);             

                break;
             case 4:
            	
            	 updateCompanyDetails();
                companyOptions(scanner);             

                break; 
             case 5:
             	
            	 updatePostedJobs();
                companyOptions(scanner);             

                break;  
                
            case 6:      	
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid option");
                companyOptions(scanner);
        }
    }

    public static void enterCompanyDetails(Scanner scanner)
    {
    	System.out.println("");
        System.out.println("Enter company details:");
        System.out.println("");
        System.out.print("Company Name: ");
        String company_Name = scanner.nextLine();

//        
//        String company_Email;
//        do {
//        	 System.out.print("Company Email: ");
//            company_Email = scanner.nextLine();
//            // Check if the email ends with "@gmail.com"
//            if (!company_Email.toLowerCase().endsWith("@gmail.com")) {
//                System.out.println("Invalid email format! Please enter a Gmail address.");
//            }
//        } while (!company_Email.toLowerCase().endsWith("@gmail.com")); 
//        
        String company_Email;
        do {
            System.out.print("Company Email: ");
            company_Email = scanner.nextLine();
            
            // Check if the email ends with "@gmail.com"
            if (!company_Email.toLowerCase().endsWith("@gmail.com")) {
                System.out.println("Invalid email format! Please enter a Gmail address.");
            } else if (isEmailExistsInDatabase(company_Email)) {
                System.out.println("Email already exists! Please enter a different email.");
            }
        } while (!company_Email.toLowerCase().endsWith("@gmail.com") || isEmailExistsInDatabase(company_Email));


        
        
        System.out.print("Company Address: ");
        String company_Address = scanner.nextLine();


        
        Session session = Job_Search_Util.getSessionFactory().openSession();
	    
	    HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
	    CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
	    criteriaQuery.select(builder.count(criteriaQuery.from(Companies.class))); // Replace YourEntity with your actual entity class

	    Long count = session.createQuery(criteriaQuery).getSingleResult();

	    System.out.println("Total number of records: " + count);

	    
	    count++;
	     System.out.println(count);
	     
        String company_Id = "COM10" +count;
       	    
		// Create a new Admin object with the entered details
	    Companies newcompany = new Companies(company_Id,company_Name,company_Email,company_Address);

        // Save the company details to the database
        CompanyDao.saveCompany(newcompany);
        
        System.out.println("New Company details saved successfully!");
        
        System.out.println("------------------------------------");
        
      
    }
    
    public static boolean isEmailExistsInDatabase(String company_Email) {
        try {
            // Use the CompanyDao to check if the email exists in the database
            CompanyDao companyDao = new CompanyDao();
            return companyDao.getCompanyByEmail(company_Email) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
 public static void postNewJob(Scanner scanner) 
	{
		
    	System.out.println("");
		System.out.println("Login through Email to post jobs:");
		System.out.print("Company Email: ");
		String email = scanner.nextLine();

		// Check if the company exists
		Companies company1 = CompanyDao.getCompanyByEmail(email);
		if (company1 == null) {
		    System.out.println("Company Email does not exist. Please enter company details first.");
		    enterCompanyDetails(scanner);
		    // After entering company details, call the method recursively to post the job
		   postNewJob(scanner);
		    return;
		}else 
		{	
	    System.out.println("");
        System.out.println("Enter job details:");
        System.out.println("");
        System.out.print("Enter Company Name: ");
        String companyname = scanner.nextLine();

        String title ;
        
        while (true) {
            System.out.print("Enter Job Title: ");
             title = scanner.nextLine();

            // Check if the title contains more than one job title
            if (title.contains(",")) {
                System.out.println("Please enter only one job title.");
            } else {
                // Further processing with the job title can be done here
                System.out.println("Job Title entered: " + title);
                break; // Exit the loop if a single job title is entered
            }
        }
        System.out.print("Enter Job Description: ");       
        String jobDescription = scanner.nextLine();
        System.out.print("Enter Skills Required: ");
        String skillsRequired = scanner.nextLine();
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();
        
        System.out.print("Posted Date (YYYY-MM-DD): ");
        String postedDateString = scanner.nextLine();

        try 
        {
        
        	 Session session = Job_Search_Util.getSessionFactory().openSession();
        	Query <Companies> query = session.createQuery("FROM Companies WHERE company_Name= :companyname", Companies.class);
        	
        	query.setParameter("companyname", companyname);
        	Companies company = query.uniqueResult(); // Assuming company name is unique

        	// Check if the company exists
        	if (company != null) {
        	    String companyId = company.getCompany_Id();
        	    
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date postedDate = dateFormat.parse(postedDateString);

            // Create a new job entity
            Job newJob = new Job();
            newJob.setCompanyName(companyname);
            
            newJob.setTitle(title);
            newJob.setCompany(company);
            newJob.setJob_description(jobDescription);
            newJob.setSkills_required(skillsRequired);          
            newJob.setLocation(location);
            newJob.setPostedDate(postedDate);
            
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
            criteriaQuery.select(builder.count(criteriaQuery.from(Job.class))); // Replace YourEntity with your actual entity class

            Long count = session.createQuery(criteriaQuery).getSingleResult();

            System.out.println("Total number of records: " + count);

            count++;
            System.out.println(count);
             
            String Job_Id = "JOB10" + count;
            
            Job job = new Job();
            job.setJob_Id(Job_Id);
            job.setCompanyName(companyname);
            job.setCompany(company);
            job.setJob_description(jobDescription);
            job.setLocation(location);
            job.setPostedDate(postedDate);
            job.setSkills_required(skillsRequired);
            job.setTitle(title);
            // Save the job entity to the database
            JobDao.saveJob(job);

            System.out.println("Job posted successfully!");
        } 
        }
    	catch (ParseException e) {
        System.out.println("Error: Invalid date format. Please enter the date in YYYY-MM-DD format.");
        
        System.out.println("------------------------------------");
    }
		}
}
        
        
     



	
	  
	  public static void viewStudentsWithAppliedJobs() {
		  System.out.println("");
		    // Retrieve applications with associated students and jobs
		    List<Applications> applications = CompanyDao.getStudentsWithAppliedJobs();
		    if (applications.isEmpty()) {
		        System.out.println("No students found with applied jobs.");
		    } else {
		        System.out.println("Students with applied jobs:");
		        for (Applications application : applications) 
		        {
		        	System.out.println("");
		        	System.out.println("Application ID  : " + application.getApplication_Id()); 
		            System.out.println("Application Date: " + application.getApplicationDate());
		            System.out.println("Student ID      : " + application.getStudent().getStudent_Id());
		            System.out.println("Full Name       : " + application.getStudent().getFirst_Name() + " " + application.getStudent().getLast_Name());
		            System.out.println("Email           : " + application.getStudent().getEmail());
		            System.out.println("***************************");
		            System.out.println("Applied Job:");
		            System.out.println("- Job ID      : " + application.getJob().getJob_Id());
		            System.out.println("- Title       : " + application.getJob().getTitle());
		            System.out.println("- Company Name: " + application.getJob().getCompanyName());
		            System.out.println("- Location    : " + application.getJob().getLocation());
		            // Include other job details as needed
		            System.out.println("------------------------------------");
		        }
		        }
		    }
	  
	  public static void updateCompanyDetails() 
	  {
		  System.out.println("");
		    Scanner scanner = new Scanner(System.in);

		    // Prompt the user to enter the ID of the company to update
		    System.out.println("Enter the ID of the company you want to update:");
		    String company_Id = scanner.next();
		    scanner.nextLine(); // Consume newline

		    // Retrieve the company by ID
		    Companies company = CompanyDao.getCompanyById(company_Id);
		    if (company == null) {
		        System.out.println("No company found with ID " + company_Id);
		        return;
		    }
		    System.out.println("");
		    // Prompt the user to choose which attribute to update
		    System.out.println("Choose which details has to update:");
		
		    System.out.println("1. Update Company Address");
		    System.out.println("2. Update Company Email");
		    System.out.print("Enter your choice (1/2) :");
		    int choice = scanner.nextInt();
		   
		    scanner.nextLine(); // Consume newline

		    switch (choice) {
		      
		        case 1:
		            System.out.print("Enter the new Address:");
		            String newAddress = scanner.nextLine();
		            company.setCompany_Address(newAddress);
		            break;
		        case 2:
		            System.out.print("Enter the new Email:");
		            String newEmail = scanner.nextLine();
		            company.setCompany_Email(newEmail);
		            break;
		        default:
		            System.out.print("Invalid choice.");
		            return;
		    }

		    // Save the updated company details to the database
		    CompanyDao.updateCompany(company);
		    System.out.println("");
		    System.out.println("Company details updated successfully!");
		    System.out.println("");
		    System.out.println("Updated Company Details:");
		    System.out.println("Company Name   : " + company.getCompany_Name());
		    System.out.println("Company Email  : " + company.getCompany_Email());
		    System.out.println("Company Address: " + company.getCompany_Address());
	
		    System.out.println("------------------------------------");
		    System.out.println("------------------------------------");
		}
	  
	  
	  public static void updatePostedJobs() {
		  System.out.println("");
		    Scanner scanner = new Scanner(System.in);

		    // Prompt the user to enter the ID of the job to update
		    System.out.print("Enter the ID of the job you want to update:");
		    String Job_Id = scanner.next();
		    scanner.nextLine(); // Consume newline

		    // Retrieve the job by ID
		    Job jobToUpdate = CompanyDao.getPostedJobById(Job_Id);
		    if (jobToUpdate == null) {
		        System.out.println("No job found with ID " + Job_Id);
		        return;
		    }
		    System.out.println("");
		    // Display choices for updating job details
		    System.out.println("Choose which Details has to update:");
		
		    System.out.println("1. Skills");
		    System.out.println("2. Job Description");
		    System.out.println("3. Location");
		    System.out.println("4. Posted Date");
		    System.out.print("Enter your choice (1/2/3/4) :");
		    System.out.println("");
		    int choice = scanner.nextInt();
		    scanner.nextLine(); // Consume newline

		    // Prompt the user to enter the new value for the selected attribute
		    String newValue;
		    switch (choice) {		      
		        case 1:
		            
		            System.out.print("Enter the new Skills of the job:");
		            newValue = scanner.nextLine();
		            jobToUpdate.setSkills_required(newValue);
		            break;
		        case 2:
		            System.out.print("Enter the new Job description:");
		            newValue = scanner.nextLine();
		            jobToUpdate.setJob_description(newValue);
		            break;
		        case 3:
		            System.out.print("Enter the new location:");
		            newValue = scanner.nextLine();
		            jobToUpdate.setLocation(newValue);
		            break;
		        case 4:
		        	System.out.print("Enter the new posted date (yyyy-MM-dd):");
		            newValue = scanner.nextLine();
		            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		            try {
		                Date newPostedDate = dateFormat.parse(newValue);
		                jobToUpdate.setPostedDate(newPostedDate);
		            } catch (ParseException e) {
		                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
		                return;
		            }
		            break;
		        default:
		            System.out.println("Invalid choice.");
		            return;
		    }

		    CompanyDao.updatePostedJob(jobToUpdate);
		    System.out.println("");
		    System.out.println("Job details updated successfully!");
		    System.out.println("");
		    System.out.println("Updated Job Details:");
		    System.out.println("");
	        System.out.println("Job ID         : " + jobToUpdate.getJob_Id());
	        System.out.println("Company Name   : " + jobToUpdate.getCompanyName());
	        System.out.println("Job Title      : " + jobToUpdate.getTitle());
	        System.out.println("Skills Required: " + jobToUpdate.getSkills_required());
	        System.out.println("Job Description: " + jobToUpdate.getJob_description());
	        System.out.println("Location       : " + jobToUpdate.getLocation());
	        System.out.println("Posted Date    : " + jobToUpdate.getPostedDate());
		    
		    System.out.println("------------------------------------");
		}

	 
  }




