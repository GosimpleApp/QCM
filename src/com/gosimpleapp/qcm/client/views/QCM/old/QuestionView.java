package com.gosimpleapp.qcm.client.views.QCM.old;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;

public class QuestionView extends Composite {

	private static QuestionViewUiBinder uiBinder = GWT
			.create(QuestionViewUiBinder.class);
	@UiField HTMLPanel htmlPanel_question;

	interface QuestionViewUiBinder extends UiBinder<Widget, QuestionView> {
	}

	public QuestionView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
