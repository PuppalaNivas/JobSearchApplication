package com.JobSearch.Entity;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.JobSearch.Entity.Student;
import com.JobSearchDao.*;

import jakarta.persistence.Column;
import jakarta.persistence.*;

@Entity
@Table(name = "Job")
public class Job 
{

    @Id   
    @Column(name = "Job_Id")
    private String Job_Id;
    
  
	@Column(name = "title",nullable = false)
    private String title;
    
    @Column(name = "CompanyName",nullable = false )
    private String CompanyName;

    @Column(name = "Job_description", length = 1000,nullable = false)
    private String Job_description;
    
    @Column(name = "Skills_required",nullable = false)
    private String Skills_required;
    
    @Column(name = "Location",nullable = false)
    private String Location;

    @Temporal(TemporalType.DATE)
    @Column(name = "postedDate",nullable = false)
    private Date postedDate;
   
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_Id")
    private Companies company;

	public String getJob_Id() {
		return Job_Id;
	}

	public void setJob_Id(String job_Id) {
		Job_Id = job_Id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	public String getJob_description() {
		return Job_description;
	}

	public void setJob_description(String job_description) {
		Job_description = job_description;
	}

	public String getSkills_required() {
		return Skills_required;
	}

	public void setSkills_required(String skills_required) {
		Skills_required = skills_required;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	@Override
	public String toString() {
		return "Job [Job_Id=" + Job_Id + ", title=" + title + ", CompanyName=" + CompanyName + ", Job_description="
				+ Job_description + ", Skills_required=" + Skills_required + ", Location=" + Location + ", postedDate="
				+ postedDate + " \n\ncompany=" + company + "]";
	}

	public Job(String job_Id, String title, String companyName, String job_description, String skills_required,
			String location, Date postedDate,Companies company) {
		super();
		Job_Id = job_Id;
		this.title = title;
		CompanyName = companyName;
		Job_description = job_description;
		Skills_required = skills_required;
		Location = location;
		this.postedDate = postedDate;
		this.company = company;
	}

	public Companies getCompany() {
		return company;
	}

	public void setCompany(Companies company) {
		this.company = company;
	}

	public Job() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	}