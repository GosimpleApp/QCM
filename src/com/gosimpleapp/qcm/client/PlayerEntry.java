package com.gosimpleapp.qcm.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.gosimpleapp.qcm.client.model.education.Course;
import com.gosimpleapp.qcm.client.model.education.Mateer;
import com.gosimpleapp.qcm.client.model.qcm.QCMItem;
import com.gosimpleapp.qcm.client.model.storage.BrowserStorage;
import com.gosimpleapp.qcm.client.model.storage.DistantStorage;
import com.gosimpleapp.qcm.client.views.QCM.QCMPlayer;
import com.gosimpleapp.qcm.client.views.edit.HasName;
import com.gosimpleapp.qcm.client.views.edit.Message;



public class PlayerEntry implements EntryPoint , RequestCallback {

	Course course=null;
	String courseId;
	int nbQCM=0;
	double points=0;
	QCMPlayer qcmPlayer;
	List<HasName> itemList;
	Random random=new Random();
	BrowserStorage browserStorage=new BrowserStorage();
	@Override
	public void onModuleLoad() {
		System.out.println("New start "+(new Date()).toString());
		DistantStorage.init();
		qcmPlayer=new QCMPlayer(browserStorage);
		qcmPlayer.qcmItemView.setVisible(false);
		
		RootLayoutPanel.get().add(qcmPlayer);
		
		qcmPlayer.addValueChangeHandler(new ValueChangeHandler<HasName>(){
			
			@Override
			public void onValueChange(ValueChangeEvent<HasName> event) {
				System.out.println("Change from player");
				HasName hasName=event.getValue();
				if (hasName instanceof Message){
					
					Message message=(Message) event.getValue();
					switch (message.getName()){
				    	case Message.BAD: 
				    		System.out.println("Event bad answer");
				    		break;
				    	case Message.GOOD: 
				    		System.out.println("Event good answer");
				    		points+=1;
				    		displayScore();
				    		break;
				    	case Message.DELETE: 
				    		System.out.println("Event detete");
				    		break;
				    	case Message.NEW: 
				    		System.out.println("Event new question");
				    		nextItem();
				    		break;
				    	default:
				    		System.out.println("Event course changed");
				    		courseId=hasName.getName();
							getFromDistantStorage( courseId);
					}
					
				}else if (hasName instanceof Course){
					getFromDistantStorage("" +((Course)hasName).getId());
				}
				
			}});
		
		courseId = com.google.gwt.user.client.Window.Location.getParameter("courseId");
		if (courseId==null){
			courseId="1414607419002";
		}
		
		course= qcmPlayer.qcmSelection.getCourse(courseId); //check if in local
		
		if (course!=null){
			PublicClassAndData.setMessage("");
			 qcmPlayer.qcmSelection.courseId=courseId;
			startQCM();
		}else{
			PublicClassAndData.setMessage("Loading course "+courseId+" from the server");
			getFromDistantStorage(courseId);
		}
		

	}
		
	void getFromDistantStorage(String courseId)
	{
		System.out.println("Loading course from distant");
		PublicClassAndData.setMessage("Load QCM "+courseId+" from server ...");
		DistantStorage.get("Course","id",courseId,new RequestCallback(){
			@Override
			public void onResponseReceived(Request request,
					Response response) {
				 course= DistantStorage.onReceiveCourseForPlayer( new Mateer(), response.getText(), browserStorage,true);
				 if (course!=null){
					 System.out.println("Course is not null geting items");
					 DistantStorage.get("QCMItem","parent_id",""+course.getId(),PlayerEntry.this);
				 }
			}
			@Override
			public void onError(Request request, Throwable exception) {
				DistantStorage.badResult(exception);	
			}});
			
	}
	
	
	void startQCM(){
		System.out.println("Start qcm");
		nbQCM=course.getChilds().size();
		itemList=new ArrayList<HasName> (course.getChilds());
		qcmPlayer.qcmItemView.setVisible(true);
		nextItem();
		
	
	}
	
	void nextItem(){
		if (itemList.size()>0)
		{
			QCMItem qcmItem=(QCMItem) itemList.get(random.nextInt(itemList.size()));
			itemList.remove(qcmItem);
	
			qcmPlayer.qcmItemView.setValue(qcmItem);
			
		}else{
			Window.alert("Il n'y a plus de question");
		}
	}

	void displayScore()
	{	
		double score=0;
		System.out.println("Points "+points+" , Score "+score);
		
		score=points*100.0/(((double ) (nbQCM)));
		qcmPlayer.score.setScore(score);

	}


	@Override
	public void onResponseReceived(Request request, Response response) {
		System.out.println("Geting items reponse");
		DistantStorage.onReceiveQCMItemsForPlayer(course, response.getText(), browserStorage,true);
	
	
		if (course.getChilds().size()>0){
			browserStorage.storeChilds("Course", ""+course.getId(),  response.getText());
			qcmPlayer.qcmSelection.refreshCoursesWithoutChangingSelection();
			startQCM();
		}else{
			Window.alert("Ce cours ne contient pas de QCM");
			browserStorage.remove("Course", ""+course.getId());
			qcmPlayer.qcmSelection.refreshCoursesWithoutChangingSelection();
		}
 		PublicClassAndData.setMessage("");
 		
	}

	@Override
	public void onError(Request request, Throwable exception) {
			System.out.println("Player on Error");
			DistantStorage.badResult(exception);
		
	}



}
