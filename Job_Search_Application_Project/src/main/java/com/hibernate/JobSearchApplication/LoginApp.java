package com.hibernate.JobSearchApplication;
import java.util.Scanner;

import org.hibernate.Session;

import com.JobSearch.Entity.Admin;
import com.JobSearch.Entity.User;
import com.JobSearch.Util.Job_Search_Util;
import com.JobSearchDao.AdminDao;

public class LoginApp {
public static void main(String[] args)
{	         
        Scanner scan = new Scanner(System.in);
        System.out.println("");
        System.out.println("     ****Welcome to Job Search Applications****** \n   ");

        // Choose role
        int roleChoice;
        do {
            System.out.println("Choose your role:");
            System.out.println("1. Admin login");
            System.out.println("2. Student login");
            System.out.println("3. Company login");
            System.out.println("Enter your choice (1/2/3): ");
            System.out.println("");
            roleChoice = scan.nextInt();

            if (roleChoice < 1 || roleChoice > 3) {
                System.out.println("Invalid choice! Please enter 1, 2, or 3.");
            }
        } while (roleChoice < 1 || roleChoice > 3);

        String username;
        String password;

        System.out.print("Enter username: ");
        username = scan.next();
        System.out.print("Enter password: ");
        password = scan.next();
        System.out.println("");     

        Session session = Job_Search_Util.getSessionFactory().openSession();

        try {
            // Retrieve user from database
            User user = session.get(User.class, username.toLowerCase()); // Convert to lowercase for case-insensitivity

            if (user != null && user.getPassword().equals(password) && user.getUsertype().equalsIgnoreCase(getUserType1(roleChoice))) {
                System.out.println("\n\n***** WELCOME TO " + user.getUsername() + " MODULE****** ");
               
                switch (roleChoice) {
                    case 1: 
                    Scanner scanner = new Scanner(System.in);
     		        // Prompt the user to enter their Admin_Id for login
                    System.out.println("");
    		        System.out.print("Enter your Admin_Id to login: ");
    		        String adminId = scanner.nextLine();

    		        // Check if the provided Admin_Id exists in the database
    		        Admin admin = AdminDao.getAdminById(adminId);
    		        if (admin != null) {
    		            // Admin_Id exists, proceed to admin options
    		            System.out.println("Login successful!\n");
    		            // Admin functionality
    		            System.out.println("");
    		            System.out.println("Admin Options! ");
                                              
                        AdminOptions.adminOptions(scan);
    		            
    		        } else {
    		            // Admin_Id does not exist, deny access
    		            System.out.println("Invalid Admin_Id. Access denied!");
    		        }			
                        break;
                    case 2:
                        // Student functionality
                    	 System.out.println("");
                        System.out.println(" Student options! ");
                       
                       // students = getStudentsFromDatabase(); 
                        StudentOption.studentOptions(scan);
                  
                        break;
                    case 3:
                        // Company functionality
                    	 System.out.println("");
                        System.out.println("  Company options!");
                         CompanyOptions.companyOptions(scan);
                        
                                         
                        break;
                }
            } else {
                System.out.println("Invalid credentials or role selection!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close(); // Close the session to release resources
        }
    }

private static String getUserType1(int roleChoice) {
    switch (roleChoice) {
        case 1:
            return "Admin";
        case 2:
            return "Student";
        case 3:
            return "Company";
        default:
            return "";
    }
}
}