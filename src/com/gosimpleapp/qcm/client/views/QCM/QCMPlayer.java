package com.gosimpleapp.qcm.client.views.QCM;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.gosimpleapp.qcm.client.model.storage.BrowserStorage;
import com.gosimpleapp.qcm.client.views.edit.HasName;

public class QCMPlayer extends Composite implements HasValue<HasName> {

	private static QCMPlayerUiBinder uiBinder = GWT
			.create(QCMPlayerUiBinder.class);
	@UiField public QCMSelection  qcmSelection;
	@UiField public QCMItemView  qcmItemView;
	@UiField public Score  score;
	
	interface QCMPlayerUiBinder extends UiBinder<Widget, QCMPlayer> {
	}

	public QCMPlayer() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public QCMPlayer(BrowserStorage borwserStorage) {
		initWidget(uiBinder.createAndBindUi(this));
		qcmSelection.browserStorage=borwserStorage;
		qcmSelection.refreshCourses();
	}

	@UiHandler("qcmSelection")
	void onQcmSelectionValueChange(ValueChangeEvent<HasName> event) {
		ValueChangeEvent.fire(QCMPlayer.this,event.getValue());
	}
	@UiHandler("qcmItemView")
	void onQcmItemViewValueChange(ValueChangeEvent<HasName> event) {
		ValueChangeEvent.fire(QCMPlayer.this,event.getValue());
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<HasName> handler) {
		return addHandler(handler,ValueChangeEvent.getType());
	}

	@Override
	public HasName getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(HasName value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(HasName value, boolean fireEvents) {
		
	}
}
