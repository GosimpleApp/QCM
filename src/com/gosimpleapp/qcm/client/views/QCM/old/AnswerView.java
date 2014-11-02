package com.gosimpleapp.qcm.client.views.QCM.old;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gosimpleapp.qcm.client.model.qcm.Answer;

public class AnswerView extends Composite {

	private static AnswerViewUiBinder uiBinder = GWT
			.create(AnswerViewUiBinder.class);
	@UiField HTMLPanel informationHtmlPanel;
	@UiField HTMLPanel explanationHtmlPanel;

	interface AnswerViewUiBinder extends UiBinder<Widget, AnswerView> {
	}

	public AnswerView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public void setAnswer(Answer answer){
		informationHtmlPanel.clear();
		informationHtmlPanel.add(new HTMLPanel(answer.response));
		informationHtmlPanel.add(new HTMLPanel(answer.explanation));
	}
	public void show(){
		informationHtmlPanel.setVisible(true);
	}
	public void hide(){
		informationHtmlPanel.setVisible(false);
	}

}
