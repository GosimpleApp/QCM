package com.gosimpleapp.qcm.client;


import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.gosimpleapp.qcm.client.model.education.Component;
import com.gosimpleapp.qcm.client.model.education.Course;
import com.gosimpleapp.qcm.client.model.education.Education;
import com.gosimpleapp.qcm.client.model.education.Mateer;
import com.gosimpleapp.qcm.client.model.qcm.QCMItem;
import com.gosimpleapp.qcm.client.model.storage.DistantStorage;
import com.gosimpleapp.qcm.client.model.storage.DistantUser;
import com.gosimpleapp.qcm.client.model.storage.MemoryStorage;
import com.gosimpleapp.qcm.client.model.user.User;
import com.gosimpleapp.qcm.client.views.edit.Connection;
import com.gosimpleapp.qcm.client.views.edit.CourseEdit;
import com.gosimpleapp.qcm.client.views.edit.EducationEdit;
import com.gosimpleapp.qcm.client.views.edit.HasName;
import com.gosimpleapp.qcm.client.views.edit.MateerEdit;
import com.gosimpleapp.qcm.client.views.edit.Message;
import com.gosimpleapp.qcm.client.views.edit.QCMTreeViewModel;
import com.gosimpleapp.qcm.client.views.edit.QuestionAndProposalsEdit;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EditQCMEntry implements EntryPoint,ValueChangeHandler<HasName>,RequestCallback {


	SplitLayoutPanel splitLayoutPanel;
	
	Widget northChild;
	Widget southChild;
	QCMTreeViewModel qcmTreeViewModel;
	CellBrowser browser ;
	public static User user;
	HorizontalPanel tagsPanel=new HorizontalPanel();
	SimplePanel qcmPanel=new SimplePanel();
	Map<HasName,Widget> hasNameWidget=new HashMap<HasName,Widget>();
	
	Map<String,Widget> classWidget=new HashMap<String,Widget>();
	MemoryStorage memoryStorage;

	
	Connection connection=new Connection();
	
	public void onModuleLoad() {
		DistantStorage.init();
		
		if (connection.getValue().email!=null && connection.getValue().password!=null){
			DistantStorage.getUser("User", "email", connection.getValue().email,EditQCMEntry.this);
		}
		connection=new Connection();
		connection.addValueChangeHandler(new ValueChangeHandler<HasName>(){

			@Override
			public void onValueChange(ValueChangeEvent<HasName> event) {
				
				HasName hasName=event.getValue();
				if (hasName instanceof User){
					User fake_user=(User) hasName;
					if (! fake_user.email.equals(fake_user.getTemplateForName()) &&   ! fake_user.password.equals(fake_user.getTemplateForName())){
						PublicClassAndData.setMessage("Connection ...");
						DistantStorage.getUser("User", "email", fake_user.email,EditQCMEntry.this);
					}
				}else if (hasName instanceof Message){
					unLoadBrowser();
	
				}	
			}});
	
		RootLayoutPanel.get().add(connection);
		RootLayoutPanel.get().setWidgetRightWidth(connection, 0, Unit.EM, 35, Unit.EM);
		PublicClassAndData.setMessage("Identification ...");

	}
	

	public void unLoadBrowser(){
		splitLayoutPanel.remove(tagsPanel);
		splitLayoutPanel.remove(browser);
		splitLayoutPanel.remove(qcmPanel);
		RootPanel.get("browser").remove(splitLayoutPanel);

	}
	
	public void loadBrowser(User logged_user){
		connection.setConnected();

		RootPanel.get("title").setVisible(false);
		user=logged_user;
		memoryStorage=new MemoryStorage(user);	
		qcmTreeViewModel=new QCMTreeViewModel(user);
		initWidgets();

		browser = new CellBrowser.Builder<HasName>(qcmTreeViewModel, null).build();

		
		browser.addCloseHandler(new CloseHandler<TreeNode>(){

			@Override
			public void onClose(CloseEvent<TreeNode> event) {
				System.out.println( "Close " +((HasName) event.getTarget().getValue()).getName());
				close( (HasName) event.getTarget().getValue());
				
			}});
		browser.addOpenHandler(new OpenHandler<TreeNode>(){

			@Override
			public void onOpen(OpenEvent<TreeNode> event) {
				System.out.println("Open"+ ((HasName) event.getTarget().getValue()).getName());	
			}

			});

		qcmTreeViewModel.selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					@SuppressWarnings("unchecked")
					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						System.out.println("Selection changed , size is "+qcmTreeViewModel.selectionModel.getSelectedSet().size());
						if (qcmTreeViewModel.selectionModel.getSelectedSet().size() == 1) {
							Object value = qcmTreeViewModel.selectionModel.getSelectedObject();

							if (value != null) {
								System.out.println(((HasName) value).getName()+ "is selectcted");
								System.out.println("class is "+ value.getClass().getName());
								HasValue<HasName> widget=(HasValue<HasName>) classWidget.get(value.getClass().getName());
								widget.setValue((HasName) value);
								changeEditWidget((Widget) widget);
								
							} else {
								System.out.println("Selection changed but Null value");
							}
						}

					}});

		
		splitLayoutPanel=new SplitLayoutPanel();
		splitLayoutPanel.addNorth(tagsPanel,100);
		splitLayoutPanel.addWest(browser,800);
		splitLayoutPanel.add(qcmPanel);


		RootPanel.get("browser").add(splitLayoutPanel);

	
		RootLayoutPanel.get().add(splitLayoutPanel);
		RootLayoutPanel.get().setWidgetTopBottom(splitLayoutPanel, 1.5, Unit.EM, 0, Unit.EM);
		RootLayoutPanel.get().setWidgetRightWidth(connection, 0, Unit.EM, 24, Unit.EM);
		
		//RootPanel.get("browser").setWidth("100%");
		//RootPanel.get("browser").setHeight("100%");
	}
	

	
	void initWidgets(){
		EducationEdit educationEdit=new EducationEdit(new Education());
		educationEdit.addValueChangeHandler(this);
		classWidget.put(new Education().getClass().getName(), educationEdit);
		
		MateerEdit mateerEdit=new MateerEdit(new Mateer());
		mateerEdit.addValueChangeHandler(this);
		classWidget.put(new Mateer().getClass().getName(), mateerEdit);
		
		CourseEdit courseEdit=new CourseEdit(new Course());
		courseEdit.addValueChangeHandler(this);
		classWidget.put(new Course().getClass().getName(), courseEdit);
		
		QuestionAndProposalsEdit qcmItemEdit=new QuestionAndProposalsEdit( new QCMItem());
		qcmItemEdit.addValueChangeHandler(this);
		classWidget.put(new QCMItem().getClass().getName(), qcmItemEdit);
	}

	
	@Override
	public void onValueChange(ValueChangeEvent<HasName> event) {

		HasName hasName=event.getValue();

		if (hasName instanceof Message){
			if (hasName.getName().equals(Message.DELETE)){
				System.out.println( "Delete from source : "+event.getSource().getClass().getName());
				@SuppressWarnings("unchecked")
				HasValue<HasName> hasValue=(HasValue<HasName>) event.getSource();
				System.out.println( "I need to remove  : "+hasValue.getValue().getName());
				removeFromData(hasValue.getValue());
			}else if((hasName.getName().equals(Message.NEW))){
				HasValue<HasName> hasValue=(HasValue<HasName>) event.getSource();
				System.out.println( "I need to create from  : "+hasValue.getValue().getName());
				createNewFrom(hasValue.getValue());
			}
		}else{
			
			((Component) hasName).save();
			/*
			if (hasName instanceof Education){
				memoryStorage.ensureHasChild(hasName);
				System.out.println("Refreshing education");
				((Education) hasName).refresh();
			}else{
				memoryStorage.ensureHasChild(hasName);
				Class parentClass=hasName.getParent().getClass();
				System.out.println( "Parent class : "+parentClass.toString());
				hasName.getParent().getAsDataProvider().refresh();
			}
			*/
		}
	}
	
	void removeFromData(HasName hasName){
		((Component) hasName).delete();
		hasName.getParent().getChilds().remove(hasName);
		removeEdit( hasName);
		qcmTreeViewModel.selectionModel.setSelected(hasName.getParent(), true);
	}
	void createNewFrom(HasName hasName){
		
		close(hasName);
		
		HasName newHasName=hasName.build();
		//memoryStorage.ensureHasChild(newHasName);
		qcmTreeViewModel. getNodeInfo(null);
		qcmTreeViewModel.selectionModel.setSelected(newHasName, true);
	}
	

	void changeEditWidget(Widget widget){
		
		if (!hasNameWidget.containsKey(widget)){
			hasNameWidget.put( ((HasName) ((HasValue)widget).getValue()), widget);
		}
		
		if (widget instanceof QuestionAndProposalsEdit){
			qcmPanel.clear();
			qcmPanel.add(widget);
			//splitLayoutPanel.setWidgetSize(qcmPanel, widget.getOffsetHeight());
		}else{
	
			tagsPanel.add(widget);
		}
		southChild=widget;
	}
	
	
	void close(HasName hasName)
	{
		for (HasName child:hasName.getChilds()){
			close(child);
		}
		if (hasNameWidget.containsKey(hasName)){
			removeEdit( hasName);
		}		
	}

	void removeEdit(HasName hasName){
		if (!tagsPanel.remove((Widget) (hasNameWidget.get(hasName)))){
			qcmPanel.remove((Widget) (hasNameWidget.get(hasName)));
		}
	}



	@Override
	public void onResponseReceived(Request request, Response response) {
			System.out.println("Received "+response.getText()	);
			JsArray<DistantUser> userResults;
     		try{
     			userResults=JsonUtils.<JsArray<DistantUser>>safeEval(response.getText());
     		}catch(Exception e){
     			System.out.println("safeEval User error");
     			Window.alert("Ce mail n'a pas été pas reconnu");
     			DistantStorage.badResult(e);
     			return;
     		}
     		System.out.println("Parsed"	);
     		
     		int s=0;
     		try{
     			s=userResults.length();
     		}catch(Exception e){
    			Window.alert("Ce mail n'a pas été pas reconnu");
			}
     		
     		if (s>=1){

         		User user=new User( userResults.get(0));
         		System.out.println("Check pass"	);
         		if (user.password.equals(connection.passwordPasswordTextBox.getText())){
         			System.out.println("Pass is fine"	);
         			
         			connection.cookies.setUuser(user);
         			PublicClassAndData.setMessage("Chargement des qcm");
         			loadBrowser(user) ;
         			
	         	}else{
	         		Window.alert("Mot de pass incorrect");
	         	}
         	}

	}



	@Override
	public void onError(Request request, Throwable exception) {
		System.out.println("on Error");
		DistantStorage.badResult(exception);
		
	}



}
