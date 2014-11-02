package com.gosimpleapp.qcm.client.model.education;


import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.gosimpleapp.qcm.client.model.qcm.QCMItem;
import com.gosimpleapp.qcm.client.model.storage.DistantCourse;
import com.gosimpleapp.qcm.client.model.storage.DistantQCMItem;
import com.gosimpleapp.qcm.client.model.storage.DistantStorage;
import com.gosimpleapp.qcm.client.model.storage.MemoryStorage;
import com.gosimpleapp.qcm.client.views.edit.HasName;

public class Course extends Component {

	public String name="";
	Mateer parent;
	public Course(){;}
	
	
	public Course(Component parent,DistantCourse courseFromServer){
		super();
		commonConstruct(parent,courseFromServer.getName(),Long.parseLong(courseFromServer.getId()));
		loadChilds();
		refresh();
	}
	public Course(Component parent,DistantCourse courseFromServer,boolean doNotLoadChild){
		super();
		commonConstruct(parent,courseFromServer.getName(),Long.parseLong(courseFromServer.getId()));
	}
	
	public Course(Component parent){
		super();
		commonConstruct(parent,MemoryStorage.constants.template_name_new_education(),-1l);
		refresh();
	}
	public Course(Component parent,String name){
		super();
		commonConstruct(parent,MemoryStorage.constants.template_name_new_course(),-1l);
		refresh();
	}
	


	@Override
	public String getName() {
		return name;
	}
	@Override
	public String getTemplateForName() {
		return MemoryStorage.constants.template_name_new_course();
	}
	@Override
	public HasName build() {
		Course course= new Course(getParent(),getTemplateForName());
		getParent().refresh();
		return course;
	}
	@Override
	public void addChild() {
		new QCMItem(this);
	}
	@Override
	public void setName(String name) {
		this.name=name;
		
	}
	@Override
	public boolean isTop() {
		return false;
	}
	@Override
	public Component getParent() {
		return (Component) parent;
	}
	@Override
	public void setParent(Component hasName) {
		parent=(Mateer) hasName;
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
		sb.append("parent_id");
		sb.append("=");
		sb.append(parent.id);
		return sb.toString();
		
	}


	
	@Override
	public void onResponseReceived(Request request, Response response) {
	

		JsArray<DistantQCMItem> itemResults=null;
 		try{
 			itemResults=JsonUtils.<JsArray<DistantQCMItem>>safeEval(response.getText());

 		}catch(Exception e){
 			System.out.println("safeEval error");
 			DistantStorage.badResult(e);
 			return;
 		}
 		int s=0;
 		try{
 			s=itemResults.length();
 		}catch(Exception e){}
 		
 		for (int i=0;i<s;i++){
 			new QCMItem(this, itemResults.get(i));
 		}
 	
 		cleanChilds();
	}


	@Override
	public String getChildSimpleName() {
		return "QCMItem";
	}









}
