package com.gosimpleapp.qcm.client.model.education;

import java.util.Date;
import java.util.List;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.gosimpleapp.qcm.client.views.edit.HasName;
import com.gosimpleapp.qcm.client.model.storage.DistantStorage;
import com.gosimpleapp.qcm.client.model.storage.Storable;
import com.gosimpleapp.qcm.client.model.storage.Task;


public abstract class Component  extends ListDataProvider<HasName> implements HasName, Storable, RequestCallback{


	public long id;
	public Component(){;}
	
	
	public void commonConstruct(Component parent,String name,Long id){

		setName(name);
		setId(id);
		System.out.println("Buidling "+getTableName()  +" "+name);
		if (parent !=null){
			setParent(parent);
			getParent().getList().add(this);
		}
		
	}
	public abstract void setName(String name);
	
	@Override
	public boolean isTemplaeForNew() {
		return getName().equals(getTemplateForName());
	}

	public abstract String  getTemplateForName() ;
	
	@Override
	public boolean hasChild() {
		return !getList().isEmpty();
	}

	@Override
	public abstract Component getParent() ;
	
	public abstract void setParent(Component component) ;
	
	@Override
	public abstract boolean IsLeaf() ;

	public abstract  boolean isTop();
	
	@Override
	public abstract void addChild() ;


	@Override
	public List<HasName> getChilds() {
		return getList();
	}

	@Override
	public ListDataProvider<HasName> getAsDataProvider() {
		return this;
	}


	public boolean shouldBeKept() {
		return isTop() 
				&& getParent().getChilds().size()==1;
	}
	
	public boolean canBeSaved() {
		return !isTemplaeForNew();
	}

	public boolean parentHadOnlyOneChild() {
		return (getParent()==null) || (getParent().getChilds().size()==1);
	}
	
	public boolean canBeRemoved() {
		if (shouldBeKept()) return false;
		if (parentHadOnlyOneChild()) return false;
		return 	( 
				!hasChild() ||  //no child 
				( getChilds().size()==1 && getChilds().get(0).isTemplaeForNew() )  ); //except template for new
	}

	

	public long  getId( ){return id;}
	
	public void  geteretateId( ){setId(new Date().getTime());}
	public void  setId(){setId(new Date().getTime());}
	public void  setId(long id){this.id=id;}
	
	public  abstract String getDataFields();
	
	
	public void save(){
		if (canBeSaved()){
			if (id<=0l){
				setId();
				create();
			}else{
				update();
			}
		}
	}
	public String getTableName(){
		return getClass().getSimpleName();
	}
	public void create(){
		DistantStorage.create(new Task("POST", this));
	}
	
	public void update(){
		DistantStorage.update(new Task("PUT", this));
	}
	public void delete(){
		DistantStorage.delete(new Task("DELETE", this));
	}
	public void delete(Component component,long id){
		DistantStorage.delete(new Task("DELETE", component));
	}
	public void get(Component component,long id){
		DistantStorage.get(new Task("GET", component));
	}
	
	public abstract String getChildSimpleName();
	public void loadChilds(){
		System.out.println("Loads childs : "+getChildSimpleName());
		DistantStorage.getChilds(this,getChildSimpleName(),this);
	}
	private HasName hasChildTemplate(){
		for (HasName child : getChilds()){
			if ( ((Component) child).isTemplaeForNew()){
				return child;
			}
		}
		return null;
	}
	
	private boolean hasChildNotTemplate(){
		for (HasName child : getChilds()){
			if (! ((Component) child).isTemplaeForNew()){
				return true;
			}
		}
		return false;
	}
	
	public void setNameAndClean(String name){
		
		setName(name);
		cleanChilds();
	}
	
	public void cleanChilds(){
		
		System.out.println("Cleaning  childs");
		if (isTemplaeForNew() ){ // template -> no child
			System.out.println("Build a template -> no child");
			getChilds().clear();
		}else if (hasChild()){ // has child
			if ( hasChildNotTemplate() ){  //one is not a template -> remove templates
				System.out.println("One child is not a template -> remove all child templates");
				HasName template=hasChildTemplate();
				while (template!=null){
					System.out.println("remove "+template.getName());
					getChilds().remove(template);
					template=hasChildTemplate();
				}
			}else{ // all are templates -> keep only one
				System.out.println("Keep only one template");
				while (getChilds().size()!=1){
					System.out.println("remove one child template");
					getChilds().remove(0);
				}
			}
		}else if (!hasChild()){ 
			System.out.println("Build a template");
			addChild();
		}
	
		if (getParent()!=null){
			getParent().refresh();
		}
		refresh();
		
	}
	
	public void onError(Request request, Throwable exception) {
		DistantStorage.badResult(exception);
	}
	

}
