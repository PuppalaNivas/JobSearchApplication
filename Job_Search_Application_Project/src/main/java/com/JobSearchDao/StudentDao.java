package com.JobSearchDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import com.JobSearch.Entity.Applications;
import com.JobSearch.Entity.Job;
import com.JobSearch.Entity.Student;
import com.JobSearch.Util.Job_Search_Util;

import jakarta.persistence.criteria.CriteriaQuery;

public class StudentDao {

		public static void saveStudent(Student student) {
	        Transaction transaction = null;
	        Session session=null;
	        try {
	        	
	         session = Job_Search_Util.getSessionFactory().openSession(); 
	            // start a transaction
	            transaction = session.beginTransaction();
	            // save the student object
	            session.merge(student);
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
    public static void updateStudentEmail(String studentId, String newEmail) {
        Transaction transaction = null;
        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Retrieve the student by ID
            Student student = session.get(Student.class, studentId);
            if (student != null) {
                // Update the email
                student.setEmail(newEmail);
                session.update(student);
                transaction.commit();
                System.out.println("Student email updated successfully!");
            } else {
                System.out.println("Student not found!");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
		 public static  boolean isEmailExists(String email) {
		        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
		            // Create HQL query to count rows with the given email
		            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Student WHERE email = :email", Long.class);
		            query.setParameter("email", email);
		            Long count = query.uniqueResult();
		            
		            // Return true if count is greater than 0, indicating the email exists
		            return count != null && count > 0;
		        } catch (Exception e) {
		            e.printStackTrace();
		            return false; // Assume email does not exist if an exception occurs
		        }
		    }
		
		
		//testing method
		public boolean TestsaveStudent(Student student) {
	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();
	            // save the student object
	            
	            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
	            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
	            criteriaQuery.select(builder.count(criteriaQuery.from(Student.class))); // Replace YourEntity with your actual entity class

	            Long count = session.createQuery(criteriaQuery).getSingleResult();

	            System.out.println("Total number of records: " + count);

	            count++;
	            System.out.println(count);
	             
	            String student_Id = "STD10" + count;
	            student.setStudent_Id(student_Id);
	            
	            session.persist(student);
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

		
	    public static void updateStudent(Student student) {
	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();
	            // save the student object
	            session.merge(student);
	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	    }

	    public void deleteStudent(long student_Id) {

	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();

	            // Delete a instructor object
	            Student student = session.get(Student.class, student_Id);
	            if (student != null) {
	                session.remove(student);
	                System.out.println("student  is deleted");
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

	    public Student getStudent(int student_Id) {

	        Transaction transaction = null;
	        Student student = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();
	            // get an instructor object
	           student = session.get(Student.class, student_Id);
	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        return  student;
	    }	
	
	    
	    public int getStudentCount() 
	    {
	        Transaction transaction = null;
	        long count = 0;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            count = (long) session.createQuery("select count(*) from Student").uniqueResult();
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        return (int) count;
	    }
	    
	    
	    public static long getcountStudent() 
	    {

	        Transaction transaction = null;
	        long count=0;
	        Student student = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();
	            // get an instructor object
	            Query<Long> query = session.createQuery("select count(*) from Student", Long.class);
	            
	            count = query.getFirstResult();
	            System.out.println("No. of students "+ count);
	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        return (int) count;
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

	    public static List<Job> getAppliedJobs(long student_Id)
	    {
	        Transaction transaction = null;
	        List<Job> appliedJobs = null;

	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();

	            // Assuming you have a Job entity with a property 'selected'
	            String hql = "SELECT j FROM Job j JOIN FETCH j.students s WHERE s.id = :student_Id";
	            Query<Job> query = session.createQuery(hql, Job.class);
	            query.setParameter("student_Id", student_Id);

	            appliedJobs = query.list();

	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }

	        return appliedJobs;
	    }
	    
	    public static void deleteStudent(Student student) {
	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();

	            // Delete the student object
	            session.delete(student);
	            System.out.println("Student is deleted");

	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	    }

	    public static List<Student> getAllStudents() {
	        List<Student> students = null;
	        Session session = null;
	        try {
	            session = Job_Search_Util.getSessionFactory().openSession();
	            session.beginTransaction();
	            Query<Student> query = session.createQuery("FROM Student", Student.class);
	            students = query.getResultList();
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
	        return students;
	    }
	    
	    public static Student getStudentById(String student_Id) {
	        Student student = null;
	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            student = session.get(Student.class, student_Id);
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        return student;
	    }
	    public static List<Applications> getAppliedJobsByStudent(String Email) {
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // Retrieve student information by email
	            Student student = getStudentByEmail(Email);
	            if (student == null) {
	                System.out.println("Student with Email " + Email + " does not exist.");
	                return null;
	            }

	            // Retrieve applied jobs for the student
	            String hql = "FROM Applications WHERE student = :student";
	            Query<Applications> query = session.createQuery(hql, Applications.class);
	            query.setParameter("student", student);
	            return query.list();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	
	    
	    public static Student getStudentByEmail(String Email) 
	    {
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            String hql = "FROM Student WHERE Email = :Email";
	            Query<Student> query = session.createQuery(hql, Student.class);
	            query.setParameter("Email", Email);
	            return query.uniqueResult();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
		
	    public boolean isPhoneNumberExists(String Phone_number) {
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            Query<Long> query = session.createQuery(
	                "SELECT COUNT(*) FROM Student WHERE Phone_number = :phoneNumber",
	                Long.class
	            );
	            query.setParameter("phoneNumber", Phone_number);
	            Long count = query.uniqueResult();
	            return count != null && count > 0;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

		
	
	}
