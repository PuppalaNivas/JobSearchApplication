package com.JobSearch.Entity;
import java.util.Date;

import com.JobSearch.Entity.Companies;
import com.JobSearch.Entity.Job;
import com.JobSearch.Entity.Student;

import jakarta.persistence.*;

@Entity
@Table(name = "Applications")
public class Applications {

    @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String Application_Id;

    @ManyToOne
    @JoinColumn(name = "Job_Id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "student_Id")
    private Student student;

    @Temporal(TemporalType.DATE)
    @Column(name = "application_date", nullable = false)
    private Date applicationDate;

    // Constructors
    public Applications() {
        // Default constructor
    }

    public Applications(Job job, Student student, Date applicationDate) {
        this.job = job;
        this.student = student;
        this.applicationDate = applicationDate;
    }

   

   

	public String getApplication_Id() {
		return Application_Id;
	}

	public void setApplication_Id(String application_Id) {
		Application_Id = application_Id;
	}

	public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    // Additional methods
    public String getStudentFirstName() {
        return student.getFirst_Name();
    }

    public String getStudentLastName() {
        return student.getLast_Name();
    }

    public String getStudentEmail() {
        return student.getEmail();
    }

    // toString method
    @Override
    public String toString() {
        return "Application ID: " + Application_Id+
                "\nJob Title: " + job.getTitle() +
                "\nStudent Name: " + student.getFirst_Name() + " " + student.getLast_Name() +
                "\nStudent Email: " + student.getEmail() +
                "\nApplication Date: " + applicationDate;
    }
}
