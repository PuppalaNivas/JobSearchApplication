package com.JobSearchDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.JobSearch.Entity.Admin;
import com.JobSearch.Entity.Companies;
import com.JobSearch.Entity.Job;
import com.JobSearch.Entity.Student;
import com.JobSearch.Util.Job_Search_Util;

public class AdminDao {

    //private static final Session HibernateUtil = null;

	public static void saveAdmin(Admin admin) 
	{
        Transaction transaction = null;
        Session session = null;
        try {

            session = Job_Search_Util.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
           
            // save the student object
            session.merge(admin);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            // Job_Search_Util.getSessionFactory().close();
        }

    }

    public static void updateAdmin(Admin admin) {
        Transaction transaction = null;
        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.merge(admin);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void deleteAdmin(Admin admin) {
        Transaction transaction = null;
        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // Delete the admin object
            session.delete(admin);
            System.out.println("Admin is deleted");

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Admin getAdmin(Long admin_Id) {
        Transaction transaction = null;
        Admin admin = null;
        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // get an admin object
            admin = session.get(Admin.class, admin_Id);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return admin;
    }

    public static List<Admin> getAllAdmins() {
        List<Admin> admins = null;
        Session session = null;
        try {
            session = Job_Search_Util.getSessionFactory().openSession();
            session.beginTransaction();
            Query<Admin> query = session.createQuery("FROM Admin", Admin.class);
            admins = query.getResultList();
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
        return admins;
    }
    public static List<Companies> getAllCompanies() {
        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
            // Hibernate query to retrieve all companies
            String hql = "FROM Companies";
            Query<Companies> query = session.createQuery(hql, Companies.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Admin getAdminById(String admin_Id) {
        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
            // Begin transaction
            session.beginTransaction();

            // Retrieve the admin by ID
            Admin admin = session.get(Admin.class, admin_Id);

            // Commit transaction
            session.getTransaction().commit();

            return admin;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int getAdminsCount() {
        Transaction transaction = null;
        long count = 0;
        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            count = (long) session.createQuery("select count(*) from Admin").uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return (int) count;
    }
    
    
    
    public static Admin getAdminByEmail(String email) {
        Admin admin = null;
        Session session = null;
        try {
            session = Job_Search_Util.getSessionFactory().openSession();
            String hql = "FROM Admin A WHERE A.email = :email";
            Query<Admin> query = session.createQuery(hql, Admin.class);
            query.setParameter("email", email);
            List<Admin> admins = query.getResultList();
            if (!admins.isEmpty()) {
                admin = admins.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return admin;
    }
    
    
    
    public static void deleteCompany(long company_Id) {
        Transaction transaction = null;
        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Retrieve the company entity to be deleted
            Companies company = session.get(Companies.class, company_Id);
            if (company != null) {
                session.delete(company); // Delete the company
                System.out.println("Company with ID " + company_Id + " deleted successfully.");

                // Remove the company from the database
                session.createQuery("DELETE FROM companies WHERE company_Id = :id")
                       .setParameter("id", company_Id)
                       .executeUpdate();
                System.out.println("Company data deleted from the database.");
            } else {
                System.out.println("Company with ID " + company_Id + " does not exist.");
            }

            transaction.commit(); // Commit the transaction
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback the transaction if an error occurs
            }
            e.printStackTrace();
        }       
    }
    

    public static void deleteAdmin(long admin_Id) {
        Transaction transaction = null;
        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Retrieve the admin entity to be deleted
            Admin admin = session.get(Admin.class, admin_Id);
            if (admin != null) {
                // Delete the admin
                session.delete(admin);
                System.out.println("Admin with ID " + admin_Id + " deleted successfully.");

                // Remove the admin from the database
                session.createQuery("DELETE FROM Admin WHERE adminId = :id")
                       .setParameter("id", admin_Id)
                       .executeUpdate();
                System.out.println("Admin data deleted from the database.");
            } else {
                System.out.println("Admin with ID " + admin_Id + " does not exist.");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
 
    public static Student getStudentById(long studentId) {
        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
            return session.get(Student.class, studentId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
