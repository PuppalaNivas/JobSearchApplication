package com.JobSearch.Entity;
import jakarta.persistence.*;


@Entity
@Table(name = "admin")
public class Admin {
	
      
     @Id
    @Column(name = "admin_Id")
    private String admin_Id;

    @Column(name = "first_name",nullable = false)
    private String first_Name;

    @Column(name = "last_name",nullable = false)
    private String last_Name;
    

    @Column(name = "email",unique = true,nullable = false)
    private String email;
    
  
    
    public Admin() {
    }


	public String getAdmin_Id() {
		return admin_Id;
	}

	public void setAdmin_Id(String admin_Id) {
		this.admin_Id = admin_Id;
	}

	public Admin(String admin_Id, String first_Name, String last_Name, String email) {
		super();
		this.admin_Id = admin_Id;
		this.first_Name = first_Name;
		this.last_Name = last_Name;
		this.email = email;
	}



	public String getFirst_Name() {
		return first_Name;
	}

	public void setFirst_Name(String first_Name) {
		this.first_Name = first_Name;
	}

	public String getLast_Name() {
		return last_Name;
	}

	public void setLast_Name(String last_Name) {
		this.last_Name = last_Name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "Admin [admin_Id=" + admin_Id + ", firstName=" + first_Name + ", lastName=" + last_Name + ", email=" + email
				+ "]";
	}
//
//	public Admin(String username, String password) {
//        // Constructor logic
//    }
//
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}