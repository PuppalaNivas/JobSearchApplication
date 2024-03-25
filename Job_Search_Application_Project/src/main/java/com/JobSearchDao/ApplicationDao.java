package com.JobSearchDao;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.JobSearch.Entity.Applications;
import com.JobSearch.Util.Job_Search_Util;

public class ApplicationDao {
    
    public static void saveApplication(Applications application) {
        Transaction transaction = null; //s a declaration statement that initializes a variable named transaction
        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(application);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void deleteApplication(int Application_Id) {
        Transaction transaction = null;
        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Applications application = session.get(Applications.class, Application_Id);
            if (application != null) {
                session.delete(application);
                System.out.println("Application deleted successfully.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static List<Applications> getAppliedJobsByStudent(String Email) {
        List<Applications> appliedJobs = null;
        Session session = null;
        try {
            session = Job_Search_Util.getSessionFactory().openSession();
            String hql = "FROM Applications A WHERE A.studentEmail = : Email";
            Query query = session.createQuery(hql);
            query.setParameter("studentEmail", Email);
            appliedJobs = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return appliedJobs;
    }
    public static List<Applications> getAppliedStudentsByJob(int Job_Id) {
        // Open a session
        Session session = null;
        List<Applications> appliedStudents = null;

        try {
            // Get session object
            session = Job_Search_Util.getSessionFactory().openSession();
            // Begin transaction
            session.beginTransaction();

            // Create query
            Query<Applications> query = session.createQuery("FROM Applications WHERE jobId = :Job_Id", Applications.class);
            query.setParameter("jobId", Job_Id);
            
            // Execute query and get result
            appliedStudents = query.getResultList();
            
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

        return appliedStudents;
    }
    public static List<Applications> getAllApplications() {
        List<Applications> applications = null;
        Session session = null;
        try {
            session = Job_Search_Util.getSessionFactory().openSession();
            session.beginTransaction();
            Query<Applications> query = session.createQuery("FROM Application", Applications.class);
            applications = query.getResultList();
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
        return applications;
    }
}

