package com.JobSearch.Entity;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import com.JobSearch.Entity.Job;

@Entity
@Table(name = "companies")
public class Companies 
{
	@Id
	@Column(name = "company_Id")
    private String company_Id;
    
	@Column(name = "company_Name",nullable = false )
    private String company_Name;

    @Column(name = "company_Email",nullable = false,unique = true)
    private String company_Email;
    
    
    @Column(name = "company_Address",nullable = false)
    private String company_Address;
    
     
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Job> jobs;

    // Existing getters and setters...

    public List<Job> getJobs() {
        return jobs;
    }

	



	public String getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(String company_Id) {
		this.company_Id = company_Id;
	}


	public String getCompany_Name() {
		return company_Name;
	}

	public void setCompany_Name(String company_Name) {
		this.company_Name = company_Name;
	}

	public String getCompany_Email() {
		return company_Email;
	}

	public void setCompany_Email(String company_Email) {
		this.company_Email = company_Email;
	}

	public String getCompany_Address() {
		return company_Address;
	}

	public void setCompany_Address(String company_Address) {
		this.company_Address = company_Address;
	}



	@Override
	public String toString() {
		return "Companies [company_Id=" + company_Id + ", company_Name=" + company_Name + ", company_Email="
				+ company_Email + ", company_Address=" + company_Address + "]";
	}



	


	public Companies(String company_Id, String company_Name, String company_Email, String company_Address) {
		super();
		this.company_Id = company_Id;
		this.company_Name = company_Name;
		this.company_Email = company_Email;
		this.company_Address = company_Address;		
	}





	public Companies() {
		super();
		// TODO Auto-generated constructor stub
	}

}