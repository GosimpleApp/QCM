package com.gosimpleapp.qcm.client.views.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.gosimpleapp.qcm.client.model.qcm.Answer;

public class AnswerEdit extends Composite implements HasValue<Answer>{

	private static AnswerEditUiBinder uiBinder = GWT
			.create(AnswerEditUiBinder.class);
	//@UiField TextArea reponseTextArea;
	@UiField TextArea justificationTextArea;
	Answer answer;
	interface AnswerEditUiBinder extends UiBinder<Widget, AnswerEdit> {
	}

	public AnswerEdit() {
		initWidget(uiBinder.createAndBindUi(this));
		setValue(new Answer());
		 setChangeEvents();

	}
	public AnswerEdit(Answer answer){
		initWidget(uiBinder.createAndBindUi(this));
		setValue( answer);
		setChangeEvents();
	}
	
	
	private void setChangeEvents()
	{
	
		
		justificationTextArea.addValueChangeHandler(new ValueChangeHandler<String>(){
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				answer.explanation=event.getValue();
				ValueChangeEvent.fire(AnswerEdit.this, getValue());
			}});
		
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<Answer> handler) {
		return addHandler(handler,ValueChangeEvent.getType());
	}
	@Override
	public Answer getValue() {
		return answer;
	}
	@Override
	public void setValue(Answer answer) {
		this.answer=answer;
		
		justificationTextArea.setText(answer.explanation);
	}
	@Override
	public void setValue(Answer answser, boolean fireEvents) {
		setValue( answer);
		if (fireEvents)
		{
			ValueChangeEvent.fire(this, getValue());
		}
		
	}
	
}
