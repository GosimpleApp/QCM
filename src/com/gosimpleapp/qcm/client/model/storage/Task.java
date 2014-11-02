package com.gosimpleapp.qcm.client.model.storage;

import com.gosimpleapp.qcm.client.model.education.Component;

public class Task {

		public Component component;
		public String method;
		
		public Task(String method,Component component){
			this.component=component;
			this.method=method;
		}
		public void run(){
			if (method.equals("PUT")){
				DistantStorage.update( this);
			}else if (method.equals("POST")){
				DistantStorage.create(this);
			}else if (method.equals("DELETE")){
				DistantStorage.delete( this);
			}else if (method.equals("GET")){
				DistantStorage.get( this);
			}
		}

}
