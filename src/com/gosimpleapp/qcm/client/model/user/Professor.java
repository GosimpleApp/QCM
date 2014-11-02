package com.gosimpleapp.qcm.client.model.user;


import java.util.HashSet;
import java.util.Set;

import com.gosimpleapp.qcm.client.model.education.Education;
import com.gosimpleapp.qcm.client.model.education.Mateer;
import com.gosimpleapp.qcm.client.model.education.School;
import com.gosimpleapp.qcm.client.model.qcm.QCM;



public class Professor extends User
{

	private static final long serialVersionUID = 1L;
	public Set<School> schools= new HashSet<School>();
	public Set<Education> educations =new HashSet<Education>();
	public Set<Student> students= new HashSet<Student>();
	

	
	public Professor(String email, String  password,String school, String mateer){
		super(email,password);
	}
	
	
}

