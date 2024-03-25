package com.JobSearchDao;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.JobSearch.Entity.Companies;
import com.JobSearch.Entity.Job;
import com.JobSearch.Util.Job_Search_Util;

import jakarta.persistence.EntityManager;

public class JobDao {

	 public static void saveJob(Job job) 
	    {
	        Transaction transaction = null;
	        Session session=null;
	        try {
	        	
	         session = Job_Search_Util.getSessionFactory().openSession(); 
	           
	            transaction = session.beginTransaction();
	            
	            session.persist(job);

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
	           
	        }
	        
	    }
	   public Job getJob(long Job_Id) {

	        Transaction transaction = null;
	      Job job = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();
	            // get an instructor object
	          job = session.get(Job.class, Job_Id);
	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        return job;
	    }
	   public static List<Job> getAllPostedJobs() {
	        Session session = null;
	        List<Job> postedJobs = null;

	        try {
	       
	            session = Job_Search_Util.getSessionFactory().openSession();
	            
	            session.beginTransaction();

	            // Create a query to retrieve all posted jobs
	            Query<Job> query = session.createQuery("FROM Job", Job.class);

	            // Execute the query and get the result list
	            postedJobs = query.getResultList();

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

	        return postedJobs;
	    }
	   public static Job getJobById(String job_Id) {
	        Session session = null;
	        Job job = null;

	        try {
	            session = Job_Search_Util.getSessionFactory().openSession();
	            session.beginTransaction();

	            // Retrieve the job entity by its ID
	            job = session.get(Job.class,job_Id);

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

	        return job;
	    }
//	   
//	   public static List<Job> viewPostedJobs() 
//	   {
//	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
//	            Query<Job> query = session.createQuery("FROM Job", Job.class);
//	            return query.list();
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	        return null;
//	    }
     public static List<Job> viewPostedJobs() {
	    try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	        Query<Job> query = session.createQuery("FROM Job j JOIN FETCH j.company c WHERE c.company_Id = j.company.company_Id", Job.class);
	        return query.list();
	    } catch (Exception e) {
	        e.printStackTrace();
	       
	    }
	    return null;
	}

     
     public int getJobCount() 
     {
    	    Transaction transaction = null;
    	    long count = 0;

    	    try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
    	        transaction = session.beginTransaction();

    	        count = (long) session.createQuery("select count(*) from Job").uniqueResult();

    	        transaction.commit();
    	    } catch (HibernateException e) {
    	        if (transaction != null) {
    	            transaction.rollback();
    	        }
    	        e.printStackTrace();
    	    }

    	    // Generate the new Job_Id string
    	    String Job_Id = "JOB10" + (++count);

    	    return (int) count;
    	}

	   
}