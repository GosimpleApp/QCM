package com.gosimpleapp.qcm.client.model.education;


import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.gosimpleapp.qcm.client.model.storage.DistantEducation;
import com.gosimpleapp.qcm.client.model.storage.DistantMateer;
import com.gosimpleapp.qcm.client.model.storage.DistantStorage;
import com.gosimpleapp.qcm.client.model.storage.MemoryStorage;
import com.gosimpleapp.qcm.client.model.user.User;
import com.gosimpleapp.qcm.client.views.edit.HasName;


public class Education extends Component
{

	public int year=0;
	
	public String name="";
	User parent;
	public Education(){;}
	
	public Education(Component parent,DistantEducation educationFromServer){
		super();
		commonConstruct(parent,educationFromServer.getName(),Long.parseLong(educationFromServer.getId()));
		this.year=Integer.parseInt(educationFromServer.getYear());
		loadChilds();
		getParent().refresh();
	}

	public Education(Component parent){
		super();
		commonConstruct(parent,MemoryStorage.constants.template_name_new_education(),-1l);
		getParent().refresh();
	}
	public Education(Component parent,String name){
		super();
		commonConstruct(parent,MemoryStorage.constants.template_name_new_education(),-1l);
		getParent().refresh();
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public String getTemplateForName() {
		return MemoryStorage.constants.template_name_new_education();
	}
	@Override
	public HasName build() {
		Education education= new Education(getParent(),getTemplateForName());
		getParent().refresh();
		return education;
	}
	@Override
	public void addChild() {
		new Mateer(this);
	}
	@Override
	public void setName(String name) {
		this.name=name;
		
	}
	@Override
	public boolean isTop() {
		return true;
	}
	@Override
	public Component getParent() {
		return (Component) parent;
	}
	@Override
	public void setParent(Component hasName) {
		parent=(User) hasName;
	}

	@Override
	public boolean IsLeaf() {
		return false;
	}

	@Override
	public String getDataFields() {
		StringBuffer sb = new StringBuffer();
		sb.append("name");
		sb.append("=");
		sb.append(URL.encodeQueryString(name));
		sb.append("&");
		sb.append("year");
		sb.append("=");
		sb.append(""+year);
		
		sb.append("&");
		sb.append("parent_id");
		sb.append("=");
		sb.append(getParent().getId());
		return sb.toString();
		
	}



	
	@Override
	public void onResponseReceived(Request request, Response response) {
		//System.out.println("Received "+response.getText()	);
		JsArray<DistantMateer> mateerResults=null;
 		try{
 			mateerResults=JsonUtils.<JsArray<DistantMateer>>safeEval(response.getText());
 		}catch(Exception e){
 			System.out.println("safeEval error");
 			DistantStorage.badResult(e);
 			return;
 		}
 
 		int s=0;
 		try{
 			s=mateerResults.length();
 		}catch(Exception e){}
 		for (int i=0;i<s;i++){
 			new Mateer(this, mateerResults.get(i));
 		}
 	
 		cleanChilds();
		
	}

	@Override
	public String getChildSimpleName() {
		return "Mateer";
	}





	
}

