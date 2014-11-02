package com.gosimpleapp.qcm.client.views.edit;

import java.util.ArrayList;

import com.google.gwt.view.client.ListDataProvider;

public class Message implements HasName{
	String name;
	public final static String DELETE="delete";
	public final static String NEW="new";
	public  final static String GOOD="good";
	public final static String BAD="bad";
	public Message(String name){
		this.name=name;
	}
	@Override
	public HasName getParent() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public HasName build() {
		return null;
	}

	@Override
	public boolean isTemplaeForNew() {
		return false;
	}

	@Override
	public ArrayList<HasName> getChilds() {
		return null;
	}

	@Override
	public boolean hasChild() {
		return false;
	}

	@Override
	public void addChild() {
		
	}

	@Override
	public boolean IsLeaf() {
		return false;
	}
	@Override
	public ListDataProvider<HasName> getAsDataProvider() {
		return null;
	}


	
}
