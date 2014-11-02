package com.gosimpleapp.qcm.client.model.storage;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.storage.client.Storage;
import com.google.gwt.storage.client.StorageMap;
import com.gosimpleapp.qcm.client.model.education.Course;
import com.gosimpleapp.qcm.client.model.education.Mateer;


public class BrowserStorage {

	Storage stockStore;
	
	public BrowserStorage(){
		
		stockStore = Storage.getLocalStorageIfSupported();
		
		System.out.println(stockStore==null?"stockstore null":"stockstore not null");
		if (supported()){

		}else{
			System.out.println("Local storage is not supported");
		}
	}
	public boolean supported(){
		return stockStore!=null;
	}
	
	public void store(String table,String id,String jsonString){
		if (supported()){
			stockStore.setItem(table+"."+id, jsonString);
		}
	}
	public void storeChilds(String table,String id,String jsonString){
		if (supported()){
			stockStore.setItem(table+".childs."+id, jsonString);
		}
	}
	
	boolean containsKey(String key){
		if (supported()){
			System.out.println("Looking for key "+key);
			StorageMap stockMap = new StorageMap(stockStore);
			return (stockMap.containsKey(key));
		}else {
			return false;
		}
	}
	
	
	public void remove(String table,String id){
		if (supported()){
			stockStore.removeItem(table+"."+id);
			stockStore.removeItem(table+".childs."+id);
		}
	}
	public List<Course> loadCourses(){
		System.out.println("Loading courses in Local");
		List<Course> courses=new ArrayList<Course> ();
		StorageMap stockMap = new StorageMap(stockStore);
		for (String key:stockMap.keySet()){
			if (key.startsWith("Course.") && !key.startsWith("Course.childs")){
				System.out.println("Key "+key +" is ok");
				Course course = DistantStorage.onReceiveCourseForPlayer( new Mateer(),stockStore.getItem(key),this,false);
				if (course!=null){
					System.out.println("course "+course.getId()+" is created, get QCM");
					getQCMItems( course) ;
					courses.add (course);
				}
			}
		}
		return courses;
	}

	
	void getQCMItems(Course course) {

		String key = "Course" + ".childs." + course.getId();
		if (containsKey(key)) {
			System.out.println("Load course child from ocal ");
			DistantStorage.onReceiveQCMItemsForPlayer(course, stockStore.getItem(key), this,false);
		}

	}
	
}
