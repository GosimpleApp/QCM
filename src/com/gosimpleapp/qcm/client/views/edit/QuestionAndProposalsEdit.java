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
import com.gosimpleapp.qcm.client.model.qcm.Proposal;
import com.gosimpleapp.qcm.client.model.qcm.QCMItem;
import com.gosimpleapp.qcm.client.model.user.User;


public class QuestionAndProposalsEdit extends Composite  implements HasValue<HasName>
{

	private static QuestionAndProposalsEditUiBinder uiBinder = GWT
			.create(QuestionAndProposalsEditUiBinder.class);
	@UiField TextArea questionTextArea;

	
	@UiField ProposalEdit proposalEdit_00;
	@UiField ProposalEdit proposalEdit_01;
	@UiField ProposalEdit proposalEdit_10;
	@UiField ProposalEdit proposalEdit_11;
	@UiField AddAndRemove addAndRemove;

	QCMItem qcmItem;

	interface QuestionAndProposalsEditUiBinder extends
			UiBinder<Widget, QuestionAndProposalsEdit> {
	}
	public QuestionAndProposalsEdit() {
		initWidget(uiBinder.createAndBindUi(this));
		setChangeEvents();
	}
	public QuestionAndProposalsEdit(User user) {
		initWidget(uiBinder.createAndBindUi(this));
		setChangeEvents();
	}
	
	public QuestionAndProposalsEdit(QCMItem qcmItem) {
		initWidget(uiBinder.createAndBindUi(this));
		setValue(qcmItem);
		setChangeEvents();
	}
	
	private void setChangeEvents()
	{
		questionTextArea.addValueChangeHandler(new ValueChangeHandler<String>(){
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				qcmItem.setNameAndClean(event.getValue());
				setValue(getValue(),true);
			}});
		
		proposalEdit_00.addValueChangeHandler(new ValueChangeHandler<Proposal>(){
			@Override
			public void onValueChange(ValueChangeEvent<Proposal> event) {
				setValue(getValue(),true);
			}});
		proposalEdit_01.addValueChangeHandler(new ValueChangeHandler<Proposal>(){
			@Override
			public void onValueChange(ValueChangeEvent<Proposal> event) {
				setValue(getValue(),true);;
			}});
		proposalEdit_10.addValueChangeHandler(new ValueChangeHandler<Proposal>(){
			@Override
			public void onValueChange(ValueChangeEvent<Proposal> event) {
				setValue(getValue(),true);
			}});
		proposalEdit_11.addValueChangeHandler(new ValueChangeHandler<Proposal>(){
			@Override
			public void onValueChange(ValueChangeEvent<Proposal> event) {
				setValue(getValue(),true);
				
			}});
		
		addAndRemove.addValueChangeHandler(new ValueChangeHandler<HasName>(){
			@Override
			public void onValueChange(ValueChangeEvent<HasName> event) {
				ValueChangeEvent.fire(QuestionAndProposalsEdit.this,event.getValue());
			}});
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<HasName> handler) {
		
		return addHandler(handler,ValueChangeEvent.getType());
		
	}


	@Override
	public QCMItem getValue() {
		return qcmItem;
	}


	@Override
	public void setValue(HasName value) {
		qcmItem=(QCMItem) value;
		questionTextArea.setText(qcmItem.question);
		proposalEdit_00.setValue(qcmItem.proposal_0);
		proposalEdit_01.setValue(qcmItem.proposal_1);
		proposalEdit_10.setValue(qcmItem.proposal_2);
		proposalEdit_11.setValue(qcmItem.proposal_3);
		addAndRemove.setValue(qcmItem);
	}


	@Override
	public void setValue(HasName qcmItem, boolean fireEvents) {
		setValue( qcmItem);
		if (fireEvents)
		{
			ValueChangeEvent.fire(this, getValue());
		}
	
		
	}



}
