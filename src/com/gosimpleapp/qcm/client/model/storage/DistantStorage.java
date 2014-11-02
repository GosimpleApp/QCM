package com.gosimpleapp.qcm.client.model.storage;





import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.gosimpleapp.qcm.client.PublicClassAndData;
import com.gosimpleapp.qcm.client.model.education.Component;
import com.gosimpleapp.qcm.client.model.education.Course;
import com.gosimpleapp.qcm.client.model.education.Education;
import com.gosimpleapp.qcm.client.model.education.Mateer;
import com.gosimpleapp.qcm.client.model.qcm.QCMItem;
import com.gosimpleapp.qcm.client.model.user.User;


public class DistantStorage {

	static String rootURL;
	public static void init(){
		String hostName=Window.Location.getHostName();
		if (hostName.equals("127.0.0.1")){
			rootURL="http://stephanegoyet.fr/qcm/db/index.php/";
		}else{
			rootURL="/qcm/db/index.php/";
		}
		
	}
	public static void create(Task task){
		task.component.setId(new Date().getTime());
		String data=task.component.getDataFields()+"&id="+task.component.getId();
		System.out.println("POST "+rootURL+task.component.getTableName()+"/ : "+data);
		 RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, rootURL+task.component.getTableName()+"/");
		
		      builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		      try {
		    	builder.sendRequest(data, new RequestCallback() {
		        public void onError(Request request, Throwable exception) {
		        	badResult(exception);
		        }
		        public void onResponseReceived(Request request, Response response) {
		        	fine(response);
		        }
		      });
		    } catch (RequestException e) {
		    	notTransmitted(e);
		    }
	}
	
	public static void update(Task task){
		System.out.println("PUT "+rootURL+task.component.getTableName()+"/"+task.component.getId()+"/"+" Postdata:"+task.component.getDataFields());
		 RequestBuilder builder = new RequestBuilder(RequestBuilder.PUT, rootURL+task.component.getTableName()+"/"+task.component.getId()+"/");
		    try {
		      builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		      	builder.sendRequest(task.component.getDataFields(), new RequestCallback() {
		        public void onError(Request request, Throwable exception) {
		        	badResult(exception);
		        }
		        public void onResponseReceived(Request request, Response response) {
		         	fine(response);
		        }
		      });
		    } catch (RequestException e) {
		    	notTransmitted(e);
		    }
	}
	
	public static void delete(Task task){
		System.out.println("DELETE "+rootURL+task.component.getTableName()+"/"+task.component.getId()+"/");
		 RequestBuilder builder = new RequestBuilder(RequestBuilder.DELETE, rootURL+task.component.getTableName()+"/"+task.component.getId()+"/");
		    try {
		      builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		      Request response = builder.sendRequest("", new RequestCallback() {

		        public void onError(Request request, Throwable exception) {
		        	badResult(exception);
		        }
		        public void onResponseReceived(Request request, Response response) {
		         	fine(response);
		        }
		      });
		    } catch (RequestException e) {
		    	notTransmitted(e);
		    }
	}
	
	public static void get(Task task){
		System.out.println("GET "+rootURL+task.component.getTableName()+"/"+task.component.getId()+"/");
		 RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, rootURL+task.component.getTableName()+"/"+task.component.getId()+"/");
		    try {
		      builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		      Request response = builder.sendRequest("", new RequestCallback() {
		        public void onError(Request request, Throwable exception) {
		        	badResult(exception);
		        }
		        public void onResponseReceived(Request request, Response response) {
		         	fine(response);
		        }
		      });
		    } catch (RequestException e) {
		    	notTransmitted(e);
		    }
	}
	public static void get(String tableName, RequestCallback requestCallBack){
		System.out.println("GET "+rootURL+tableName+"/");
		 RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, rootURL+tableName);
		    try {
		      builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		      builder.sendRequest("",requestCallBack);
		    } catch (RequestException e) {
		    	notTransmitted(e);
		    }
	}
	
	
	public static void get(String tableName, String field, String value,RequestCallback requestCallBack){
		System.out.println("GET "+rootURL+tableName+"/"+field+"/"+value+"/");
		 RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, rootURL+tableName+"/"+field+"/"+value+"/");
		    try {
		      builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		      builder.sendRequest("",requestCallBack);
		    } catch (RequestException e) {
		    	notTransmitted(e);
		    }
	}
	
	public static void getUser(String table,String field,String value,RequestCallback requestCallBack) {
		System.out.println("GET "+rootURL+table+"/"+field+"/"+value+"/");
		 RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, rootURL+table+"/"+field+"/"+value+"/");

		  builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		  try {
			builder.sendRequest("", requestCallBack);
		} catch (RequestException e) {
			badResult(e);
		}
	}
	
	public static void getChilds(Component component, String childTable,RequestCallback requestCallBack) {
		System.out.println("GET "+rootURL+childTable+"/"+"parent_id"+"/"+component.getId()+"/");
		 RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, rootURL+childTable+"/"+"parent_id"+"/"+component.getId()+"/");

		  builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		  try {
			builder.sendRequest("",requestCallBack);
		} catch (RequestException e) {
			badResult(e);
		}
	}
	
	public static boolean fine(Response response){
		String  message=response.getText() ;
		if (message!=null && message.contains("success")){
			if (message.contains("200") || message.contains("201")){
				PublicClassAndData.log("Request success");
				return true;
			}
		}
		Window.alert("Bad result: " + message);
		return false;
	}

	

	public static List<User> onReceiveUsers(String response){
 		List<User> users=new ArrayList<User>();
		//System.out.println("Received "+response	);
		JsArray<DistantUser> userResults;
 		try{
 			userResults=JsonUtils.<JsArray<DistantUser>>safeEval(response);
 		}catch(Exception e){
 			System.out.println("safeEval User error");
 			PublicClassAndData.setMessage("Aucun QCM public disponible");
 			return users;
 		}
 		System.out.println("Parsed"	);
 		int s=0;

 		try{
 			s=userResults.length();
 		}catch(Exception e){}
 		
 		for (int i=0;i<s;i++){
 			users.add(new User( userResults.get(i)));
 		}
		return users;
	}
	
	
	public static List<Mateer> onReceiveMateers(Education education,String response){
 		List<Mateer> mateers=new ArrayList<Mateer>();
		//System.out.println("Received "+response	);
		JsArray<DistantMateer> mateerResults;
 		try{
 			mateerResults=JsonUtils.<JsArray<DistantMateer>>safeEval(response);
 		}catch(Exception e){
 			System.out.println("safeEval User error");
 			PublicClassAndData.setMessage("Aucun QCM public disponible");
 			return mateers;
 		}
 		System.out.println("Parsed"	);
 		int s=0;

 		try{
 			s=mateerResults.length();
 		}catch(Exception e){}
 		
 		for (int i=0;i<s;i++){
 			mateers.add(new Mateer(education, mateerResults.get(i)));
 		}
		return mateers;
	}
	
	
	public static List<Course> onReceiveCourses(Mateer mateer,String response,boolean loadChilds){
 		List<Course> courses=new ArrayList<Course>();

		JsArray<DistantCourse> courseResults;
 		try{
 			courseResults=JsonUtils.<JsArray<DistantCourse>>safeEval(response);
 		}catch(Exception e){
 			System.out.println("safeEval User error : JsArray<DistantCourse>");
 			return courses;
 		}
 
 		int s=0;
 		try{
 			s=courseResults.length();
 		}catch(Exception e){}
 		
 		for (int i=0;i<s;i++){
 				Course course=new Course(mateer, courseResults.get(i));
 				courses.add(course);
 		}
		return courses;
	}
	public static Course onReceiveCourseForPlayer(Mateer mateer,String response,BrowserStorage browserStorage,boolean store){
 	
		JsArray<DistantCourse> courseResults;
 		try{
 			courseResults=JsonUtils.<JsArray<DistantCourse>>safeEval(response);
 		}catch(Exception e){
 			System.out.println("safeEval User error : JsArray<DistantCourse>");
 			return null;
 		}
 
 		int s=0;
 		try{
 			s=courseResults.length();
 			if (s>0){
 				Course course=new Course(mateer, courseResults.get(0),false);
 				if (store){
 					browserStorage.store("Course", ""+course.getId(), response);
 				}
 				return course;
 			}
				
 		}catch(Exception e){}
 
		return null;
	}
	

	
	public static List<QCMItem> onReceiveQCMItems(Course course,String  response){
		System.out.println("DistantStorage onReceiveQCMItems"	);
 		List<QCMItem> qcmItems=new ArrayList<QCMItem>();
		JsArray<DistantQCMItem> qcmItemResults;
 		try{
 			qcmItemResults=JsonUtils.<JsArray<DistantQCMItem>>safeEval(response);
 		}catch(Exception e){
 			System.out.println("safeEval User error");
 			PublicClassAndData.setMessage("Aucun QCM public disponible");
 			return qcmItems;
 		}
 		System.out.println("Parsed"	);
 		int s=0;

 		try{
 			s=qcmItemResults.length();
 		}catch(Exception e){}
 		
 		for (int i=0;i<s;i++){
 			qcmItems.add(new QCMItem(course, qcmItemResults.get(i)));
 		}
		return qcmItems;
	}
	
	public static List<QCMItem> onReceiveQCMItemsForPlayer(Course course,String  response,BrowserStorage browserStorage,boolean store){
		System.out.println("onReceiveQCMItems"	);
 		List<QCMItem> qcmItems=new ArrayList<QCMItem>();
		JsArray<DistantQCMItem> qcmItemResults;
 		try{
 			qcmItemResults=JsonUtils.<JsArray<DistantQCMItem>>safeEval(response);
 		}catch(Exception e){
 			System.out.println("safeEval User error");
 			PublicClassAndData.setMessage("Aucun QCM public disponible");
 			return qcmItems;
 		}
 		int s=0;

 		try{
 			s=qcmItemResults.length();
 		}catch(Exception e){}
 		
 		for (int i=0;i<s;i++){
 			System.out.println("Building qcm item for "+qcmItemResults.get(i).toString());
 			qcmItems.add(new QCMItem(course, qcmItemResults.get(i)));
 		}
 		if (s>0 && store){
 			browserStorage.storeChilds("Course", ""+course.getId(), response);
 		}
		return qcmItems;
	}
	
	
	public static void notTransmitted(RequestException e){
		Window.alert("Failed to send the request: " + e.getMessage());
	}
	
	public static void badResult(Throwable exception){
		Window.alert("Bad result: " + exception.getMessage());
	}
}
