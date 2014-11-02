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
import com.gosimpleapp.qcm.client.model.education.Mateer;

public class MateerEdit extends Composite implements HasValue<HasName>{

	private static MateerEditUiBinder uiBinder = GWT
			.create(MateerEditUiBinder.class);

	interface MateerEditUiBinder extends UiBinder<Widget, MateerEdit> {
	}

	Mateer mateer;

	@UiField TextBox nameTextBox;
	@UiField AddAndRemove addAndRemove;
	
	public MateerEdit() {
		initWidget(uiBinder.createAndBindUi(this));
		//setValue(new Mateer("Matière"));
		setChangeEvents();
	}

	public MateerEdit(Mateer mateer) {
		initWidget(uiBinder.createAndBindUi(this));
		setValue( mateer);
		setChangeEvents();
		
	}
	
	private void setChangeEvents()
	{
		nameTextBox.addValueChangeHandler(new ValueChangeHandler<String>(){
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				mateer.setNameAndClean(event.getValue());
				ValueChangeEvent.fire(MateerEdit.this, getValue());
			}});
		addAndRemove.addValueChangeHandler(new ValueChangeHandler<HasName>(){
			@Override
			public void onValueChange(ValueChangeEvent<HasName> event) {
					ValueChangeEvent.fire(MateerEdit.this, event.getValue());
			}});
	}
	
	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<HasName> handler) {
		return addHandler(handler,ValueChangeEvent.getType());
	}

	@Override
	public Mateer getValue() {
		return mateer;
	}

	@Override
	public void setValue(HasName value) {
		this.mateer=(Mateer) value;

		nameTextBox.setText(mateer.name);
		addAndRemove.setValue(mateer);
	}

	@Override
	public void setValue(HasName mateer, boolean fireEvents) {
		setValue( mateer);
		if (fireEvents)
		{
			ValueChangeEvent.fire(this, getValue());
		}
		
	}



}
