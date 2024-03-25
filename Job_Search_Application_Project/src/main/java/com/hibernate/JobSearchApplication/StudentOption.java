package com.hibernate.JobSearchApplication;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.Date;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import com.JobSearch.Entity.*;
import com.JobSearch.Util.Job_Search_Util;
import com.JobSearchDao.*;

public class StudentOption 
{

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //System.out.println("Welcome to the Job Search Application!");
        

        try {
            studentOptions(scanner);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            scanner.close(); // Close the scanner to prevent resource leak
        }
    }
   
		

public static void studentOptions(Scanner scanner) throws ParseException 
{
	    System.out.println("");
        System.out.println("1. Enter your details");
        System.out.println("2. View posted jobs");
        System.out.println("3. Apply for a job");
        System.out.println("4. View applied jobs");
        System.out.println("5. If you want update any student Details ");
        System.out.println("6. Exit");
        System.out.println("");
      
        System.out.print("Enter your choice (1/2/3/4/5/6) :");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                enterStudentDetails(scanner);
                studentOptions(scanner);
                break;
            case 2:
                //viewAvailableJobs();
            	viewPostedJobs();
                studentOptions(scanner);
                break;
            case 3:
                applyForJob();
                studentOptions(scanner);
                break;
            case 4: 
            	
                System.out.println("");
                System.out.print("Enter your Email: ");
                String Email = scanner.next();

                System.out.println("You entered: " + Email);

            	viewAppliedJobsByStudent(Email);
                studentOptions(scanner);
                break;
            case 5:
            	updateStudentDetails(); 
                studentOptions(scanner);
                break;                                  
                              
            case 6:
                System.out.println(" *******Thank you*********");
                break;
            default:
                System.out.println("Invalid option");
                studentOptions(scanner);
        }
}

public static void enterStudentDetails(Scanner scanner) 
{
    System.out.println("");
    System.out.println("Enter your Details:");
    System.out.println("");
    System.out.print("Enter your First Name: ");
    String first_Name = scanner.nextLine();
    
    System.out.print("Enter your Last Name: ");
    String last_Name = scanner.nextLine();
    
    String email;

    do {
        System.out.print("Enter your Email: ");
        email = scanner.nextLine();

        // Check if the email ends with "@gmail.com"
        if (!email.toLowerCase().endsWith("@gmail.com")) {
            System.out.println("Invalid email format! Please enter a Gmail address.");
        } else if (isEmailExistsInDatabase(email)) {
            System.out.println("Email already exists in the database! Please enter a different email.");
        }
    } while (!email.toLowerCase().endsWith("@gmail.com") || isEmailExistsInDatabase(email));

    


    System.out.print("Enter your Highest Education: ");
    String education = scanner.nextLine();

    String Phone_number;
    while (true) {
        System.out.print("Enter your Phone Number: ");
        Phone_number = scanner.nextLine();

        // Validate phone number format
        if (!isValidPhoneNumber(Phone_number)) {
            System.out.println("Invalid phone number format! Please enter a 10-digit phone number.");
        } else if (isPhoneNumberExistsInDatabase(Phone_number)) {
            System.out.println("Phone number already exists in the database! Please enter a different phone number.");
        } else {
            System.out.println("Valid phone number entered: " + Phone_number);
            break; // Exit the loop if a valid and unique phone number is entered
        }
    }


   
    System.out.print("Enter your Known Skills: ");
    String skills = scanner.nextLine();
    
    System.out.print("Enter your Experience: ");
    String experience = scanner.nextLine();

    String resume;
    while (true) {
        System.out.print("Upload your Resume (PDF format only): ");
         resume = scanner.nextLine();

        // Check if the file extension is ".pdf"
        if (resume.toLowerCase().endsWith(".pdf")) {
            System.out.println("Resume uploaded successfully.");
            break; // Exit the loop if a valid PDF is provided
        } else {
            System.out.println("Please upload a PDF file only.");
        }
    }
 
    
    Session session = Job_Search_Util.getSessionFactory().openSession();
    
    HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
    criteriaQuery.select(builder.count(criteriaQuery.from(Student.class))); // Replace YourEntity with your actual entity class

    Long count = session.createQuery(criteriaQuery).getSingleResult();

    System.out.println("Total number of records: " + count);

    count++;
    System.out.println(count);
     
    String student_Id = "STD10" + count;
   	    
    // Create a new Student object with the entered details
//    Student newstudent = new Student(student_Id, first_Name, last_Name, email, education, phone_number, skills, experience, resume);
      Student newstudent = new Student();
      newstudent.setStudent_Id(student_Id);
      newstudent.setFirst_Name(first_Name);
      newstudent.setLast_Name(last_Name);
      newstudent.setEmail(email);
      newstudent.setEducation(education);
      newstudent.setPhone_number(Phone_number);
      newstudent.setExperience(experience);
      newstudent.setSkills(skills);
      newstudent.setResume(resume);
    // Save the student details to the database
    StudentDao.saveStudent(newstudent);
    
    System.out.println("Student details saved successfully!");
    System.out.println("------------------------------------");
}

private static boolean isValidPhoneNumber(String phoneNumber) {
    // Remove any non-numeric characters
    String numericPhone = phoneNumber.replaceAll("[^0-9]", "");

    // Check if the length is exactly 10 digits
    return numericPhone.length() == 10;
}

public static boolean isPhoneNumberExistsInDatabase(String Phone_number) {
    try {
        // Use the StudentDao to check if the phone number exists in the database
        StudentDao studentDao = new StudentDao();
        return studentDao.isPhoneNumberExists(Phone_number);
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public static boolean isEmailExistsInDatabase(String email) {
    try {
        // Use the StudentDao to check if the email exists in the database
        StudentDao studentDao = new StudentDao();
        return studentDao.getStudentByEmail(email) != null;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

  
    
    public static void viewPostedJobs() {
    	System.out.println("");
        System.out.println("Company Posted Jobs :");
        
        // Retrieve and display posted jobs for the student
        List<Job> postedJobs = JobDao.getAllPostedJobs();
        
        if (postedJobs != null && !postedJobs.isEmpty()) {
            for (Job job : postedJobs) {
                // Print job details
            	System.out.println("");
            	System.out.println("Job Details : ");
            	System.out.println("");  
                System.out.println( "Job ID              :" +job.getJob_Id());
                System.out.println( "Job Title           :" +job.getTitle());
                System.out.println( "Company Name        :" +job.getCompanyName());
                System.out.println( "Company Description :" +job.getJob_description());
                System.out.println( "Skills Required     :" +job.getSkills_required());
                System.out.println( "Job Location        :" +job.getLocation());
                System.out.println( "Job posted Date     :" +job.getPostedDate());
                System.out.println("");
                System.out.println("Company Details : ");
                System.out.println("");
                // Print related company details
                System.out.println("Company ID     : " + job.getCompany().getCompany_Id());
                System.out.println("Company Name   : " + job.getCompany().getCompany_Name());             
                System.out.println("Company Email  : " + job.getCompany().getCompany_Email());
                System.out.println("Company Address: " + job.getCompany().getCompany_Address());
                
                System.out.println("------------------------------------");
            }
        } else {
            System.out.println("No jobs are currently posted.");
        }
    }
   
    public static void applyForJob() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.print("Enter the ID of the job you want to apply for :  ");
        String Job_Id = scanner.next();
        scanner.nextLine(); // Consume newline

        // Retrieve the job details using the entered job ID
        Job job = JobDao.getJobById(Job_Id);    
        if (job == null) {
            System.out.println("No job found with ID " + Job_Id);
            return;
        }
        System.out.println("");
        System.out.print("Enter your first name:");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name:");
        String lastName = scanner.nextLine();

        System.out.print("Enter your email:");
        String Email = scanner.nextLine();

        // Retrieve the student details using the entered email
        Student student = StudentDao.getStudentByEmail(Email);    
        if (student == null) {
            System.out.println("No student found with email " + Email);
            return;
        }

        // Set the first name and last name of the student
        student.setFirst_Name(firstName);
        student.setLast_Name(lastName);

        Session session = Job_Search_Util.getSessionFactory().openSession();
        
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        criteriaQuery.select(builder.count(criteriaQuery.from(Applications.class))); // Replace YourEntity with your actual entity class

        Long count = session.createQuery(criteriaQuery).getSingleResult();

        System.out.println("Total number of records: " + count);

        count++;
        System.out.println(count);
         
        String app_Id = "APP10" + count;
        
        // Create an application object with job details, student information, and current date
//        Applications application = new Applications(job, student, new Date());
        Applications application = new Applications();
        application.setApplication_Id(app_Id);
        application.setStudent(student);
        application.setJob(job);
        application.setApplicationDate(new Date());
        // Save the application to the database
        ApplicationDao.saveApplication(application);

        System.out.println("Application submitted successfully!");
        System.out.println("------------------------------------");
       
    }
     

    
    public static void viewAppliedJobsByStudent(String Email) 
    {
        List<Applications> appliedJobs = StudentDao.getAppliedJobsByStudent(Email);

        if (appliedJobs == null || appliedJobs.isEmpty()) {
            System.out.println("No applied jobs found for the student.");
        } else {
            System.out.println("Applied Jobs:");
            for (Applications application : appliedJobs) {
                System.out.println("");
                System.out.println("Application ID  : " + application.getApplication_Id());
                System.out.println("Application Date: " + application.getApplicationDate());
                System.out.println("Job ID          : " + application.getJob().getJob_Id());
                System.out.println("Company Name    : " + application.getJob().getCompanyName());
                System.out.println("Title           : " + application.getJob().getTitle());
                System.out.println("Location        : " + application.getJob().getLocation());
       
                System.out.println("------------------------------------");
            }
        }
    }

    
	public static void updateStudentDetails() 
    {
    	Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.print("Enter the email if you want to update student details : ");
        String Email = scanner.nextLine();

        // Retrieve the student by email
        Student studentToUpdate = StudentDao.getStudentByEmail(Email);
        if (studentToUpdate == null) {
            System.out.println("No student found with email " + Email);
            return;
        }

        // Display a menu for updating different attributes
        System.out.println("Choose which attribute to update:"); 
        System.out.println("");
        System.out.println("1. Education");
        System.out.println("2. Email");
        System.out.println("3. Phone Number");
        System.out.println("4. Skills");
        System.out.println("5. Experience");
        System.out.println("6. Resume");
        System.out.print("Enter your choice (1/2/3/4/5/6) :");
        System.out.println(" ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                 System.out.print("Enter the new Education:");
                 String newEducation = scanner.nextLine();
                 studentToUpdate.setEducation(newEducation);
                break;
            case 2:
            	String email;
                do {
                    System.out.print("Enter your Email: ");
                    email = scanner.nextLine();
                    // Check if the email ends with "@gmail.com"
                    if (!email.toLowerCase().endsWith("@gmail.com")) {
                        System.out.println("Invalid email format! Please enter a Gmail address.");
                    }
                } while (!email.toLowerCase().endsWith("@gmail.com")); 
                break;
            case 3:
            	String phone_number;
                while (true) 
                {
                    System.out.print("Enter your Phone Number: ");
                     phone_number = scanner.nextLine();

                    // Validate phone number format
                    if (!isValidPhoneNumber(phone_number)) {
                        System.out.println("Invalid phone number format! Please enter a 10-digit phone number.");
                    } else {
                        System.out.println("Valid phone number entered: " + phone_number);
                        studentToUpdate.setPhone_number(phone_number);
                        break; // Exit the loop if a valid phone number is entered
                    }
                }
                break;
            case 4:
                System.out.println("Enter the new Skills:");
                String newSkills = scanner.nextLine();
                studentToUpdate.setSkills(newSkills);
                break;
            case 5:
                System.out.println("Enter the new Experience:");
                String newExperience = scanner.nextLine();
                studentToUpdate.setExperience(newExperience);
                break;
            case 6:
            	
            	   String resume;
            	    while (true) {
            	        System.out.print("Upload your Resume (PDF format only): ");
            	         resume = scanner.nextLine();

            	        // Check if the file extension is ".pdf"
            	        if (resume.toLowerCase().endsWith(".pdf")) {
            	            System.out.println("Resume uploaded successfully.");
            	            studentToUpdate.setResume(resume);
            	            break; // Exit the loop if a valid PDF is provided
            	        } else {
            	            System.out.println("Please upload a PDF file only.");
            	        }
            	    }
            	 
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        // Update the student details in the database
        StudentDao.updateStudent(studentToUpdate);
        System.out.println(" ");
        System.out.println("Student details updated successfully!");
        System.out.println(" ");
        System.out.println("Updated Student Details:");
        System.out.println(" First Name : " + studentToUpdate.getFirst_Name());
        System.out.println("Last  Name  : " + studentToUpdate.getLast_Name());
        System.out.println("Email       : " + studentToUpdate.getEmail());
        System.out.println("Education   : " + studentToUpdate.getEducation());
        System.out.println("Phone Number: " + studentToUpdate.getPhone_number());
        System.out.println("Skills      : " + studentToUpdate.getSkills());
        System.out.println("Experience  : " + studentToUpdate.getExperience());
        System.out.println("Resume      : " + studentToUpdate.getResume());
        
        System.out.println("------------------------------------");
    }
	 

}
