package com.JobSearchDao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import com.JobSearch.Entity.*;
import com.JobSearch.Util.*;
import com.hibernate.JobSearchApplication.*;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;
	public class CompanyDao 
	{
	   

		public static void saveCompany(Companies company) 
	    {
	        Transaction transaction = null;
	        Session session=null;
	        try {
	        	
	         session = Job_Search_Util.getSessionFactory().openSession(); 
	            // start a transaction
	            transaction = session.beginTransaction();
	            // save the student object
	            session.merge(company);
	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        finally {
	            if (session != null) {
	                session.close();
	            }
	            //Job_Search_Util.getSessionFactory().close();
	        }
	        
	    }
		
		public boolean TestsaveCompany(Companies company) 
		{
	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();
	            // save the student object
	            
	            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
	            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
	            criteriaQuery.select(builder.count(criteriaQuery.from(Companies.class))); // Replace YourEntity with your actual entity class

	            Long count = session.createQuery(criteriaQuery).getSingleResult();

	            System.out.println("Total number of records: " + count);

	            count++;
	            System.out.println(count);
	             
	            String company_Id = "COM10" + count;
	            company.setCompany_Id(company_Id);
	            
	            session.persist(company);
	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	                return(false);
	            }
	            e.printStackTrace();
	        }
	        return(true);
	    }


	    public static void updateCompany(Companies company) {
	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();
	            // save the student object
	            session.merge(company);
	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	    }

	    public void deleteCompany(String id) {

	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();

	            // Delete a instructor object
	            Companies company = session.get(Companies.class, id);
	            if (company != null) {
	                session.remove(company);
	                System.out.println("company is deleted");
	            }

	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	    }

	    public Companies getCompany(long Id) {

	        Transaction transaction = null;
	        Companies company = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();
	            // get an instructor object
	            company = session.get(Companies.class, Id); // changed id =Id
	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        return company;
	    }
	    
	    public int getCompaniesCount() {
	        Transaction transaction = null;
	        long count = 0;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            count = (long) session.createQuery("select count(*) from Companies").uniqueResult();
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        return (int) count;
	    }
	   

	    public static List<Job> viewAvailableJobs() {
	        List<Job> availableJobs = null;
	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            String hql = "FROM Job j WHERE j.selected = false";
	            Query<Job> query = session.createQuery(hql, Job.class);
	            availableJobs = query.list();
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        return availableJobs;
	    }
	    public static List<Companies> viewPostedJobs() 
	    {
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // Assuming you have an entity named 'Companies' and a table named 'Job'
	            Query<Companies> query = session.createQuery("FROM Companies", Companies.class);
	            return query.list();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null; // handle the exception appropriately in your actual implementation
	    }
	    
	    
	    public static List<Job> getAvailableJobs() {
	        List<Job> availableJobs = null;
	        Transaction transaction = null;

	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();

	            // Adjust this query based on your entity and database structure
	            String hql = "FROM Job j WHERE j.selected = false";
	            Query<Job> query = session.createQuery(hql, Job.class);
	            availableJobs = query.list();

	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }

	        return availableJobs;
	    }
	    public static List<Job> getJobsByCompany(String companyEmail) {
	        // Open a session
	        Session session = null;
	        List<Job> jobs = null;

	        try {
	            // Get session object
	            session = Job_Search_Util.getSessionFactory().openSession();
	            // Begin transaction
	            session.beginTransaction();

	            // Create query
	            Query<Job> query = session.createQuery("FROM Job WHERE company_email = :email", Job.class);
	            query.setParameter("email", companyEmail);
	            
	            // Execute query and get result
	            jobs = query.getResultList();
	            
	            // Commit transaction
	            session.getTransaction().commit();
	        } catch (Exception e) {
	            if (session != null) {
	                session.getTransaction().rollback();
	            }
	            e.printStackTrace();
	        } finally {
	            if (session != null) {
	                session.close();
	            }
	        }

	        return jobs;
	    }

	    public static List<Companies> getAllCompanies() {
	        Session session = null;
	        List<Companies> companiesList = null;

	        try {
	            // Open a session
	            session = Job_Search_Util.getSessionFactory().openSession();
	            // Begin a transaction
	            session.beginTransaction();

	            // Create a query to retrieve all companies
	            Query<Companies> query = session.createQuery("FROM Companies", Companies.class);

	            // Execute the query and get the result list
	            companiesList = query.getResultList();

	            // Commit the transaction
	            session.getTransaction().commit();
	        } catch (Exception e) {
	            // Rollback the transaction if an exception occurs
	            if (session != null) {
	                session.getTransaction().rollback();
	            }
	            e.printStackTrace();
	        } finally {
	            // Close the session
	            if (session != null) {
	                session.close();
	            }
	        }

	        return companiesList;
	    }
	    
//	    public static Companies getCompanyFromDatabase() {
//	        // Open a Hibernate session
//	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
//	            // Begin a transaction
//	            session.beginTransaction();
//	            
//	            // Retrieve the company from the database using a query
//	            String hql = "FROM Companies"; // Retrieve all companies
//	            Query<Companies> query = session.createQuery(hql, Companies.class);
//	            Companies company = query.uniqueResult();
//	            
//	            // Commit the transaction
//	            session.getTransaction().commit();
//	            
//	            return company;
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return null;
//	        }
//	    }
	    public static List<Applications> getStudentsWithAppliedJobs() {
	        List<Applications> applications = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            String hql = "FROM Applications";
	            Query<Applications> query = session.createQuery(hql, Applications.class);
	            applications = query.list();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return applications;
	    }
	    
	 public static Companies getCompanyById(String company_Id) {
	        Transaction transaction = null;
	        Companies company = null;

	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();

	            // Retrieve the company by ID
	            company = session.get(Companies.class, (String) company_Id);

	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }

	        return company;
	    }
	 
	 public static Job getPostedJobById(String job_Id) {
	        Transaction transaction = null;
	        Job job = null;

	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();

	            // Retrieve the job by ID
	            job = session.get(Job.class, job_Id);

	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }

	        return job;
	    }
	 
	 
	 public static void updatePostedJob(Job job) {
	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            session.update(job);
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	    }
	
	 public static Companies getCompanyFromDatabase() {
		    Session session = Job_Search_Util.getSessionFactory().openSession();
		    Transaction transaction = null;
		    Companies company = null;

		    try {
		        transaction = session.beginTransaction();
		        Query query = session.createQuery("FROM Companies");
		        query.setMaxResults(1); // Limit the result to just one company
		        company = (Companies) query.uniqueResult(); // Retrieve the first (and hopefully only) result

		        transaction.commit();
		    } catch (Exception e) {
		        if (transaction != null) {
		            transaction.rollback();
		        }
		        e.printStackTrace();
		    } finally {
		        session.close();
		    }

		    return company;
		}

	 public static Companies getCompanyByEmail(String email) {
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            String hql = "FROM Companies WHERE company_Email = :email";
	            Query<Companies> query = session.createQuery(hql, Companies.class);
	            query.setParameter("email", email);
	            return query.uniqueResult();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	
	}