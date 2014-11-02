package com.gosimpleapp.qcm.client.views.QCM.old;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.gosimpleapp.qcm.client.model.user.Professor;
import com.gosimpleapp.qcm.client.model.user.User;

public class CourseSelection extends Composite {

	private static CourseSelectionUiBinder uiBinder = GWT
			.create(CourseSelectionUiBinder.class);

	interface CourseSelectionUiBinder extends UiBinder<Widget, CourseSelection> {
	}

	public CourseSelection() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public CourseSelection(Professor professor) {
		initWidget(uiBinder.createAndBindUi(this));
	
	}


	public CourseSelection(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

}
