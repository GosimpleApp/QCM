package com.gosimpleapp.qcm.client.model.education;

import java.util.ArrayList;



public class School extends ArrayList<Education>
{

	private static final long serialVersionUID = 1L;
	public String name="";
	static String template_name="Nouvelle école";

	public School() {
		super();
		name=template_name;
	}
	public School(String name) {
		super();
		this.name=name;;
	}

}

