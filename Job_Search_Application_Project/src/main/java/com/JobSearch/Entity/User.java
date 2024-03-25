package com.JobSearch.Entity;
import jakarta.persistence.*;
@Entity
@Table(name = "user")
public class User {
		
	    @Id
	    private String username;
	    public User(String username, String password, String usertype) {
			super();
			this.username = username;
			this.password = password;
			this.usertype = usertype;
		}

		private String password;
	    public User() {
			super();
			// TODO Auto-generated constructor stub
		}

		private String usertype;

	    public String getUsertype() {
			return usertype;
		}

		public void setUsertype(String usertype) {
			this.usertype = usertype;
		}

		// Getters and setters
	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }
	}