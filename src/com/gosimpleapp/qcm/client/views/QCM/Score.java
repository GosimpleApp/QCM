package com.gosimpleapp.qcm.client.views.QCM;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.gosimpleapp.qcm.client.views.components.HTMLAdatativeFontSizeWidget;

public class Score extends Composite {

	private static ScoreUiBinder uiBinder = GWT.create(ScoreUiBinder.class);

	interface ScoreUiBinder extends UiBinder<Widget, Score> {
	}
	@UiField HTMLAdatativeFontSizeWidget scoreLabel;
	
	public Score() {
		initWidget(uiBinder.createAndBindUi(this));
		setScore(0);
	}

	public void setScore(double score){
		scoreLabel.setText("Score "+Math.round(score*100.0)/100.0+" / 100");
	}

}
