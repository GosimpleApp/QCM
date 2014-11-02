package com.gosimpleapp.qcm.client.views.edit;

import java.util.List;

import com.google.gwt.view.client.ListDataProvider;


public interface HasName {
	HasName getParent();
	public String getName();
	public  HasName build();
	public boolean isTemplaeForNew();
	public List<HasName> getChilds();
	public boolean hasChild();
	public void addChild();
	boolean IsLeaf();
	ListDataProvider<HasName> getAsDataProvider();


	
}
