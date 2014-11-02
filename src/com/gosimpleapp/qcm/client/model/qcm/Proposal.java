package com.gosimpleapp.qcm.client.model.qcm;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.URL;
import com.gosimpleapp.qcm.client.model.storage.Storable;


public class Proposal 
{
	public long id;
	public String statement="";
	public Answer answer;

	static String template_statement="Proposition";
	public Proposal(){
		statement=template_statement;
		answer=new Answer();
	}
	public Proposal(String statement,Answer answer){
		this.statement=statement;
		this.answer=answer;

	}


}

