package com.gosimpleapp.qcm.client.model.storage;

import com.google.gwt.user.client.Cookies;
import com.gosimpleapp.qcm.client.model.user.User;

public class CookiesStorage {
	public String name;
	public String pass;
	public CookiesStorage(){
		name=Cookies.getCookie("name");
		if (name!=null){
			pass=Cookies.getCookie("password");
		}else{
			name="test@sgoyet.fr";
			pass="test";
		}
	}
	public void setUuser(User user){
		Cookies.setCookie("name", user.getName());
		Cookies.setCookie("password", user.password);
	}
}
