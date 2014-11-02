package com.gosimpleapp.qcm.client.model.storage;


import com.google.gwt.core.client.GWT;
import com.gosimpleapp.qcm.client.QCMConstants;
import com.gosimpleapp.qcm.client.model.education.Course;
import com.gosimpleapp.qcm.client.model.education.Education;
import com.gosimpleapp.qcm.client.model.education.Mateer;
import com.gosimpleapp.qcm.client.model.education.School;
import com.gosimpleapp.qcm.client.model.user.User;
import com.gosimpleapp.qcm.client.views.edit.HasName;

public class MemoryStorage {

	public User user;
	public static  QCMConstants constants = GWT.create(QCMConstants.class);
	public MemoryStorage(User user)
	 {
		 this.user=user;
		// ensureHasChild(user);
	 }
	public void testCase()
	{
	
		user= new User();;
		School school=new School("Lycée Gabriel Fauré");
	
		Education education=new Education(user,"BTS SIO");


		Mateer si7=new Mateer(education,"Integration  service SI7");
		Mateer slam5=new Mateer(education,"Developpement de logiciel SLAM5");


		new Course(si7,"Gestion de version");
		new Course(si7,"Indicateurs et tableaux de bord");
		new Course(si7,"Deploiement  service");
		new Course(slam5,"Les cycles  projet");


		school.add(education);
		user.school=school;
		ensureHasChild(user);
		
	}
	
	public void ensureHasChild(HasName hasName)
	{
		System.out.println(hasName.getName()+" ensure haschild "+hasName.getChilds().size());
		if (!hasName.IsLeaf() && !hasName.hasChild() && !hasName.isTemplaeForNew() ){
			System.out.println( "Adding child");
			hasName.addChild();
		}else if(hasName.IsLeaf()){
			System.out.println("No child required cause is leaf");
		}else if(hasName.hasChild()){
			System.out.println("No child required cause has child");
		}else if(hasName.isTemplaeForNew() ){
			System.out.println("No child required cause template for new");
		}
		for (HasName child:hasName.getChilds()){
				ensureHasChild(child);
		}
		
	}


}
