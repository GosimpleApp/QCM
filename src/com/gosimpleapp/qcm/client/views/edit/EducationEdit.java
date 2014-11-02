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
import com.gosimpleapp.qcm.client.model.education.Education;

public class EducationEdit extends Composite  implements HasValue<HasName>{

	private static EducationEditUiBinder uiBinder = GWT
			.create(EducationEditUiBinder.class);

	interface EducationEditUiBinder extends UiBinder<Widget, EducationEdit> {
	}
	@UiField TextBox nameTextBox;
	@UiField TextBox yearTextBox;
	@UiField AddAndRemove addAndRemove;
	Education education;
	
	public EducationEdit() {
		initWidget(uiBinder.createAndBindUi(this));
		setValue(new Education());
		setChangeEvents();
	}

	public EducationEdit(Education education) {
		initWidget(uiBinder.createAndBindUi(this));
		setValue( education);
		setChangeEvents();
		
	}
	
	private void setChangeEvents()
	{
		nameTextBox.addValueChangeHandler(new ValueChangeHandler<String>(){
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				education.setNameAndClean(event.getValue());
				ValueChangeEvent.fire(EducationEdit.this, getValue());
			}});
		
		yearTextBox.addValueChangeHandler(new ValueChangeHandler<String>(){
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				education.year=Integer.parseInt(event.getValue());
				education.setNameAndClean(education.getName());
				ValueChangeEvent.fire(EducationEdit.this, getValue());
			}});
		addAndRemove.addValueChangeHandler(new ValueChangeHandler<HasName>(){
			@Override
			public void onValueChange(ValueChangeEvent<HasName> event) {
					ValueChangeEvent.fire(EducationEdit.this,addAndRemove.getValue());
			
			}});

	}
	
	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<HasName> handler) {
		return addHandler(handler,ValueChangeEvent.getType());
	}

	@Override
	public Education getValue() {
		return education;
	}

	@Override
	public void setValue(HasName value) {
		this.education=(Education) value;
		nameTextBox.setText(education.name);
		yearTextBox.setText(""+education.year);
		addAndRemove.setValue(education);
	}

	@Override
	public void setValue(HasName education, boolean fireEvents) {
		setValue( education);
		if (fireEvents)
		{
			ValueChangeEvent.fire(this, getValue());
		}
		
	}




}
