package com.gosimpleapp.qcm.client;


import com.google.gwt.user.client.ui.RootPanel;

public class PublicClassAndData {
	//public static Logger logger = Logger.getLogger("PublicClassAndData");
	public static void log(String message){
		System.out.println(message);
	}
	
	public static void setMessage(String message){
		RootPanel.get("message").getElement().setInnerHTML(message);
	}
}
