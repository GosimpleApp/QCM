package com.gosimpleapp.qcm.client.views.QCM;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gosimpleapp.qcm.client.PublicClassAndData;
import com.gosimpleapp.qcm.client.model.education.Course;
import com.gosimpleapp.qcm.client.model.education.Mateer;
import com.gosimpleapp.qcm.client.model.storage.BrowserStorage;
import com.gosimpleapp.qcm.client.views.edit.HasName;
import com.gosimpleapp.qcm.client.views.edit.Message;

public class QCMSelection extends Composite  implements HasValue<HasName>{

	private static QCMSelectionUiBinder uiBinder = GWT
			.create(QCMSelectionUiBinder.class);

	interface QCMSelectionUiBinder extends UiBinder<Widget, QCMSelection> {
	}
	BrowserStorage browserStorage;
	List<Course> courses=new ArrayList<Course>();
	Mateer mateer=new Mateer();
	public String courseId=null;
	@UiField TextBox courseIdTextBox;
	@UiField ListBox selectionListBox;

	
	public QCMSelection() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public QCMSelection(BrowserStorage browserStorage) {
		initWidget(uiBinder.createAndBindUi(this));
		refreshCourses();
	}
	
	public void refreshCourses(){
		System.out.println("Loading courses in local");
		PublicClassAndData.setMessage("Loading courses in local storage ....");
		courses=browserStorage.loadCourses();
		selectionListBox.clear();
		for (Course course:courses){
			selectionListBox.addItem(course.getName());
			selectionListBox.addItem(course.getName(), ""+course.getId());
		}
	}
	public void refreshCoursesWithoutChangingSelection(){
		refreshCourses();
		for (int i=0;i<selectionListBox.getItemCount();i++){
			if (selectionListBox.getValue(i).equals(courseId)){
				selectionListBox.setSelectedIndex(i);
				break;
			}
		}
	}
	
	public Course getCourse(String courseId){
		for (Course course:courses){
			if (courseId.equals(""+course.getId())){
				return course;
			}
		}
		return null;
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<HasName> handler) {
		return addHandler(handler,ValueChangeEvent.getType());
	}

	@Override
	public HasName getValue() {
		return null;
	}

	@Override
	public void setValue(HasName value) {
	}

	@Override
	public void setValue(HasName value, boolean fireEvents) {
	}
	

	@UiHandler("courseIdTextBox")
	void onCourseIdTextBoxValueChange(ValueChangeEvent<String> event) {
		courseId=event.getValue();
		ValueChangeEvent.fire(QCMSelection.this,new Message(courseId));
	}
	
	@UiHandler("selectionListBox")
	void onSelectionListBoxChange(ChangeEvent event) {
		
		if (courseId!=selectionListBox.getValue(selectionListBox.getSelectedIndex())){
			courseId=selectionListBox.getValue(selectionListBox.getSelectedIndex());
			Course course=getCourse(courseId);
			if (course !=null){
				ValueChangeEvent.fire(QCMSelection.this,getCourse(courseId));
			}
		}
		

	}
}
