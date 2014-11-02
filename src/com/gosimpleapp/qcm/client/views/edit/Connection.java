package com.gosimpleapp.qcm.client.views.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gosimpleapp.qcm.client.model.storage.CookiesStorage;
import com.gosimpleapp.qcm.client.model.storage.MemoryStorage;
import com.gosimpleapp.qcm.client.model.user.User;

public class Connection extends Composite implements HasValue<HasName>{

	private static ConnectionUiBinder uiBinder = GWT
			.create(ConnectionUiBinder.class);

	interface ConnectionUiBinder extends UiBinder<Widget, Connection> {
	}
	User user=new User();
	public CookiesStorage cookies;
	@UiField TextBox emailTextBox;
	@UiField public PasswordTextBox passwordPasswordTextBox;
	@UiField  Button okButton;
	@UiField  Label passLabel ;
	public Connection() {
		initWidget(uiBinder.createAndBindUi(this));
		cookies=new CookiesStorage();
		user.email=cookies.name;
		user.password=cookies.pass;
		setWithUser();
		emailTextBox.addValueChangeHandler(new ValueChangeHandler<String>(){
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				user.email=event.getValue();
			}});
		
		passwordPasswordTextBox.addValueChangeHandler(new ValueChangeHandler<String>(){
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				user.password=event.getValue();
			}});
	}
	void setWithUser(){
		emailTextBox.setText(user.email==null?"":user.email);
		passwordPasswordTextBox.setText(user.password==null?"":user.password);
	}
	public void setConnected(){
		passwordPasswordTextBox.setVisible(false);
		passLabel.setVisible(false);
		okButton.setText(MemoryStorage.constants.disconnect());
	}
	public void setDisconnected(){
		passwordPasswordTextBox.setVisible(true);
		passLabel.setVisible(true);
		okButton.setText(MemoryStorage.constants.connect());
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<HasName> handler) {
		return addHandler(handler,ValueChangeEvent.getType());
	}

	@Override
	public User getValue() {
		return user;
	}

	@Override
	public void setValue(HasName value) {
		user=(User) value;
		setWithUser();
	}

	@Override
	public void setValue(HasName value, boolean fireEvents) {
		setValue( value) ;
		if (fireEvents){
			ValueChangeEvent.fire(Connection.this, getValue());
		}
	}

	@UiHandler("okButton")
	void onOkButtonClick(ClickEvent event) {
		System.out.println("Click");
		if (passwordPasswordTextBox.isVisible()){
			ValueChangeEvent.fire(Connection.this, getValue());
		}else{
			System.out.println("deconnection");
			ValueChangeEvent.fire(Connection.this,(HasName) new Message(Message.DELETE));
			setDisconnected();
		}
	}
}
