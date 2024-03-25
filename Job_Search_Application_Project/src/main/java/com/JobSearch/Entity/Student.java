package com.JobSearch.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {


	@Id
    @Column(name = "student_Id")
    private String student_Id;

    @Column(name = "First_Name",nullable = false)
    private String First_Name;
 

    @Column(name = "Last_Name",nullable = false)
    private String Last_Name;
    
    @Column(name = "Phone_number",unique = true,nullable = false)
    private String Phone_number; 
    
    @Column(name = "Email",unique = true,nullable = false)
    private String Email;

    @Column(name = "Education",nullable = false)
    private String Education;

    @Column(name = "Skills",nullable = false)
    private String Skills;

    @Column(name = "Experience")
    private String Experience;

    @Column(name = "Resume",nullable = false)
    private String Resume;
    
    public Student()
    {
	// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Student [student_Id=" + student_Id + ",First_Name=" + First_Name + ", Last_Name="
				+ Last_Name + ", phone_number=" + Phone_number + ", Email=" + Email + ", Education=" + Education
				+ ", Skills=" + Skills + ", Experience=" + Experience + ", Resume=" + Resume + "]";
	}


	

	public String getStudent_Id() {
		return student_Id;
	}


	public void setStudent_Id(String student_Id) {
		this.student_Id = student_Id;
	}


	public String getFirst_Name() {
		return First_Name;
	}


	public void setFirst_Name(String first_Name) {
		First_Name = first_Name;
	}


	public String getLast_Name() {
		return Last_Name;
	}


	public void setLast_Name(String last_Name) {
		Last_Name = last_Name;
	}


	public String getPhone_number() {
		return Phone_number;
	}


	public void setPhone_number(String phone_number) {
		this.Phone_number = phone_number;
	}


	public String getEmail() {
		return Email;
	}


	public void setEmail(String email) {
		Email = email;
	}


	public String getEducation() {
		return Education;
	}


	public void setEducation(String education) {
		Education = education;
	}


	public String getSkills() {
		return Skills;
	}


	public void setSkills(String skills) {
		Skills = skills;
	}


	public String getExperience() {
		return Experience;
	}


	public void setExperience(String experience) {
		Experience = experience;
	}


	public String getResume() {
		return Resume;
	}


	public void setResume(String resume) {
		Resume = resume;
	}




	public Student(String student_Id, String first_Name, String last_Name, String phone_number, String email,
			String education, String skills, String experience, String resume)
	{
		super();
		this.student_Id = student_Id;
		First_Name = first_Name;
		Last_Name = last_Name;
		Phone_number = phone_number;
		Email = email;
		Education = education;
		Skills = skills;
		Experience = experience;
		Resume = resume;
	}


	public Student( String firstName, String lastName, String phoneNumber,String email, String education,
			String skills, String experience, String resume) 
	{
		
	}


	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}


}
