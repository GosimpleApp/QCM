package com.gosimpleapp.qcm.client.model.user;


import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.view.client.ListDataProvider;
import com.gosimpleapp.qcm.client.PublicClassAndData;
import com.gosimpleapp.qcm.client.model.education.Component;
import com.gosimpleapp.qcm.client.model.education.Education;
import com.gosimpleapp.qcm.client.model.education.School;
import com.gosimpleapp.qcm.client.model.storage.DistantEducation;
import com.gosimpleapp.qcm.client.model.storage.DistantStorage;
import com.gosimpleapp.qcm.client.model.storage.DistantUser;
import com.gosimpleapp.qcm.client.model.storage.MemoryStorage;
import com.gosimpleapp.qcm.client.views.edit.HasName;



public class User extends   Component implements RequestCallback
{



	public String password;
	public String email;
	public long id;
	public School school;
	
	public boolean connected;
	public boolean identified;
	
	
	public User(DistantUser userFromServer){
		super();
		commonConstruct(null,userFromServer.getMail(),Long.parseLong(userFromServer.getId()));
		password=userFromServer.getPass();
		loadChilds();
	
		refresh();
		
	}

	public User(){
		super();
		commonConstruct(null,MemoryStorage.constants.template_name_new_user(),-1l);
		password=getTemplateForName() ;
		refresh();
	}

	public User(String  email, String password){
		super();
		commonConstruct(null,email,-1l);
		this.password=password;
		refresh();
	}
	
	public boolean connection() {
		return false;	
	}

	public boolean identification() {
		return false;	
	}
	@Override
	public String getName() {
		return email;
	}
	@Override
	public HasName build() {
		return new User();
	}

	@Override
	public boolean IsLeaf() {
		return false;
	}
	@Override
	public void addChild() {
		 new Education(this);
	}
	@Override
	public ListDataProvider<HasName> getAsDataProvider() {
		return this;
	}
	@Override
	public Component getParent() {
		return null;
	}

	@Override
	public void setName(String name) {
		email=name;
		
	}
	@Override
	public String getTemplateForName() {
		return MemoryStorage.constants.template_name_new_user();
	}
	@Override
	public void setParent(Component component) {
	}
	@Override
	public boolean isTop() {
		return true;
	}


	@Override
	public String getDataFields() {
		return null;
	}


	
	@Override
	public void onResponseReceived(Request request, Response response) {
		System.out.println("Received "+response.getText()	);
		JsArray<DistantEducation> educationResults=null;
 		try{
 			educationResults=JsonUtils.<JsArray<DistantEducation>>safeEval(response.getText());
 		}catch(Exception e){
 			System.out.println("safeEval error");
 			DistantStorage.badResult(e);
 			return;
 		}
 	
 		
 		int s=0;
 		try{
 			s=educationResults.length();
 		}catch(Exception e){}
 		
 		for (int i=0;i<s;i++){
 			new Education(this, educationResults.get(i));
 		}
 		cleanChilds();
 		PublicClassAndData.setMessage("");
 
	}

	@Override
	public String getChildSimpleName() {
		return "Education";
	}





}

