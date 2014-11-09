package com.gosimpleapp.qcm.client;


import com.google.gwt.user.client.ui.RootPanel;

public class PublicClassAndData {
	//public static Logger logger = Logger.getLogger("PublicClassAndData");
	public static void log(String message){
		System.out.println(message);
	}
	
	public static void setMessage(String message){
		if (message.equals("")){
			RootPanel.get("loading").setVisible(false);
			RootPanel.get("message").setVisible(false);
		}else{
			RootPanel.get("loading").setVisible(true);
			RootPanel.get("message").setVisible(true);
			RootPanel.get("message").getElement().setInnerHTML(message);
		}
		
	}
}
