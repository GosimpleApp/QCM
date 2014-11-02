package com.gosimpleapp.qcm.client.views.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.gosimpleapp.qcm.client.model.qcm.Answer;
import com.gosimpleapp.qcm.client.model.qcm.Proposal;


public class ProposalEdit extends Composite implements HasValue<Proposal>{

	private static ProposalEditUiBinder uiBinder = GWT
			.create(ProposalEditUiBinder.class);
	Proposal proposal;
	@UiField public TextArea proposalTextArea;
	@UiField public AnswerEdit answerEdit;
	@UiField public CheckBox checkBox;
	interface ProposalEditUiBinder extends UiBinder<Widget, ProposalEdit> {
	}

	public ProposalEdit() {
		
		initWidget(uiBinder.createAndBindUi(this));
		setValue( new Proposal());
		setChangeEvents();
	}
	public ProposalEdit(Proposal proposal) {

		initWidget(uiBinder.createAndBindUi(this));
		setValue( proposal);
		setChangeEvents();
	}
	

	
	private void setChangeEvents()
	{
		proposalTextArea.addValueChangeHandler(new ValueChangeHandler<String>(){
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				proposal.statement=event.getValue();
				ValueChangeEvent.fire(ProposalEdit.this, getValue());
			}});
		
		answerEdit.addValueChangeHandler(new ValueChangeHandler<Answer>(){
			@Override
			public void onValueChange(ValueChangeEvent<Answer> event) {
				ValueChangeEvent.fire(ProposalEdit.this, getValue());
			}});
		
		checkBox.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				proposal.answer.toggle();
				answerEdit.setValue(proposal.answer);
				ValueChangeEvent.fire(ProposalEdit.this, getValue());
			}});
	}
	

	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<Proposal> handler) {
		return addHandler(handler,ValueChangeEvent.getType());
	}
	@Override
	public Proposal getValue() {
		return proposal;
	}
	@Override
	public void setValue(Proposal proposal) {
		this.proposal=proposal;
		proposalTextArea.setText(proposal.statement);
		answerEdit.setValue(proposal.answer);
		checkBox.setValue(proposal.answer.isASolution);
		
	}
	@Override
	public void setValue(Proposal value, boolean fireEvents) {
		if (fireEvents)
		{
			ValueChangeEvent.fire(this, getValue());
		}
		
	}
	
}
