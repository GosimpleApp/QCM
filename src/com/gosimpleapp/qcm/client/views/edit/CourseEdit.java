package com.gosimpleapp.qcm.client.views.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gosimpleapp.qcm.client.model.education.Course;

public class CourseEdit extends Composite implements HasValue<HasName> {

	private static CourseEditUiBinder uiBinder = GWT
			.create(CourseEditUiBinder.class);

	interface CourseEditUiBinder extends UiBinder<Widget, CourseEdit> {}

	Course course;
	@UiField TextBox nameTextBox;
	@UiField AddAndRemove addAndRemove;
	public CourseEdit() {
		initWidget(uiBinder.createAndBindUi(this));
		setChangeEvents();
	}

	public CourseEdit(Course course) {
		initWidget(uiBinder.createAndBindUi(this));
		setValue( course);
		setChangeEvents();
		
	}
	
	private void setChangeEvents()
	{
		nameTextBox.addValueChangeHandler(new ValueChangeHandler<String>(){
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				course.setNameAndClean(event.getValue());
				ValueChangeEvent.fire(CourseEdit.this, getValue());
			}});
		addAndRemove.addValueChangeHandler(new ValueChangeHandler<HasName>(){
			@Override
			public void onValueChange(ValueChangeEvent<HasName> event) {
					ValueChangeEvent.fire(CourseEdit.this,addAndRemove.getValue());
			}});
	}
	
	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<HasName> handler) {
		return addHandler(handler,ValueChangeEvent.getType());
	}

	@Override
	public Course getValue() {
		return course;
	}

	@Override
	public void setValue(HasName course) {
		this.course=(Course) course;
		addAndRemove.setValue(course);
		nameTextBox.setText(course.getName());
	}

	@Override
	public void setValue(HasName course, boolean fireEvents) {
		setValue(course);
		if (fireEvents)
		{
			ValueChangeEvent.fire(this, getValue());
		}
		
	}





}
