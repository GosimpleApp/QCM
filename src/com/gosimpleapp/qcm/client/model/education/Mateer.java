package com.gosimpleapp.qcm.client.model.education;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.gosimpleapp.qcm.client.model.storage.DistantCourse;
import com.gosimpleapp.qcm.client.model.storage.DistantMateer;
import com.gosimpleapp.qcm.client.model.storage.DistantStorage;
import com.gosimpleapp.qcm.client.model.storage.MemoryStorage;
import com.gosimpleapp.qcm.client.views.edit.HasName;




public class Mateer extends Component
{


	public String name="";
	Education parent;
	public Mateer(){;}
	
	public Mateer(Component parent,DistantMateer mateerFromServer){
		super();
		commonConstruct(parent,mateerFromServer.getName(),Long.parseLong(mateerFromServer.getId()));
		loadChilds();
		refresh();
	}

	public Mateer(Component parent){
		super();
		commonConstruct(parent,MemoryStorage.constants.template_name_new_course(),-1l);
		refresh();
	}
	public Mateer(Component parent,String name){
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
		return MemoryStorage.constants.template_name_new_mateer();
	}
	@Override
	public HasName build() {
		Mateer mateer= new Mateer(getParent(),getTemplateForName());
		getParent().refresh();
		return mateer;
	}
	@Override
	public void addChild() {
		new Course(this);
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
		parent=(Education) hasName;
		
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
 		
		System.out.println("Received "+response.getText()	);
		JsArray<DistantCourse> courseResults=null;
 		try{
 			courseResults=JsonUtils.<JsArray<DistantCourse>>safeEval(response.getText());
 		}catch(Exception e){
 			System.out.println("safeEval error");
 			DistantStorage.badResult(e);
 			return;
 		}
 		int s=0;
 		try{
 			s=courseResults.length();
 		}catch(Exception e){}
 		
 		for (int i=0;i<s;i++){
 			new Course(this, courseResults.get(i));
 		}
 	
 		cleanChilds();
 		
 			
		
	}

	@Override
	public String getChildSimpleName() {
		return "Course";
	}




	
	
	
}

