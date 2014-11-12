package com.gosimpleapp.qcm.client.views.edit;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.gosimpleapp.qcm.client.EditQCMEntry;
import com.gosimpleapp.qcm.client.model.education.Component;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;



public class AddAndRemove extends Composite  implements HasValue<HasName>{

	private static AddAndRemoveUiBinder uiBinder = GWT
			.create(AddAndRemoveUiBinder.class);

	interface AddAndRemoveUiBinder extends UiBinder<Widget, AddAndRemove> {}

	@UiField PushButton removePushButton;
	@UiField PushButton newPushButton;
	HasName value;
	public AddAndRemove() {
		initWidget(uiBinder.createAndBindUi(this));
		setChangeEvents();
	}
	public AddAndRemove(HasName value) {
		initWidget(uiBinder.createAndBindUi(this));
		setValue(value);
		setChangeEvents();
	}
	
	@SuppressWarnings("deprecation")
	private void setChangeEvents(){
		removePushButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				setValue(new Message(Message.DELETE), true);
			}});
		newPushButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				setValue(new Message(Message.NEW), true);
			}});
		
		removePushButton.addMouseListener(
			        new ToolTypeListenner(
			          "Cliquer pour supprimer", 5000 /* timeout in milliseconds*/,"yourcssclass"));
		 
		newPushButton.addMouseListener(
		        new ToolTypeListenner(
		          "Cliquer pour dupliquer cet élément", 5000 /* timeout in milliseconds*/,"yourcssclass"));
	}
	
	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<HasName> handler) {
		return addHandler(handler,ValueChangeEvent.getType());
	}

	@Override
	public HasName getValue() {
		return value;
	}

	@Override
	public void setValue(HasName value) {
		this.value=value;
		
		if (value==null || value.getParent()==null) return;

		removePushButton.setVisible(((Component) value).canBeRemoved());
		newPushButton.setVisible( !value.isTemplaeForNew());

	}

	@Override
	public void setValue(HasName value, boolean fireEvents) {
		this.value=value;
		if (fireEvents){
			ValueChangeEvent.fire(this, getValue());
		}
		
	}



	
}
