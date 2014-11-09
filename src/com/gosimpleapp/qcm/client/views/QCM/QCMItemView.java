package com.gosimpleapp.qcm.client.views.QCM;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gosimpleapp.qcm.client.model.qcm.Proposal;
import com.gosimpleapp.qcm.client.model.qcm.QCMItem;
import com.gosimpleapp.qcm.client.views.components.HTMLAdatativeFontSizeWidget;
import com.gosimpleapp.qcm.client.views.edit.HasName;
import com.gosimpleapp.qcm.client.views.edit.Message;

public class QCMItemView extends Composite implements  HasValue<HasName> {

	private static QCMItemViewUiBinder uiBinder = GWT
			.create(QCMItemViewUiBinder.class);

	interface QCMItemViewUiBinder extends UiBinder<Widget, QCMItemView> {
	}
	QCMItem qcmItem;

	@UiField HTMLAdatativeFontSizeWidget question;
	@UiField HTML explanation;
	
	@UiField HTMLAdatativeFontSizeWidget proposal_0;
	@UiField HTMLAdatativeFontSizeWidget proposal_1;
	@UiField HTMLAdatativeFontSizeWidget proposal_2;
	@UiField HTMLAdatativeFontSizeWidget proposal_3;

	int nb_clicked=0;
	
	public QCMItemView() {
		initWidget(uiBinder.createAndBindUi(this));
		explanation.setText("");
		setValue(new QCMItem());
	}
	public QCMItemView(QCMItem qcmItem) {
		initWidget(uiBinder.createAndBindUi(this));
		explanation.setText("");
		setValue(qcmItem);
	}

	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<HasName> handler) {
		return addHandler(handler,ValueChangeEvent.getType());
	}

	@Override
	public HasName getValue() {
		return qcmItem;
	}

	@Override
	public void setValue(HasName value) {
		qcmItem =(QCMItem) value;
		question.setText(qcmItem.question);
		proposal_0.setText(qcmItem.proposal_0.statement);
		proposal_1.setText(qcmItem.proposal_1.statement);
		proposal_2.setText(qcmItem.proposal_2.statement);
		proposal_3.setText(qcmItem.proposal_3.statement);
		explanation.setText("");

		
		proposal_0.getElement().getStyle().clearBackgroundColor();
		proposal_1.getElement().getStyle().clearBackgroundColor();
		proposal_2.getElement().getStyle().clearBackgroundColor();
		proposal_3.getElement().getStyle().clearBackgroundColor();
		
	}

	@Override
	public void setValue(HasName value, boolean fireEvents) {
		setValue(value);
		if (fireEvents){
			ValueChangeEvent.fire(this, getValue());
		}
		
	}

	
	void manageClick(Widget proposal_html,Proposal propsal){
		nb_clicked++;
		boolean responseIsGood=propsal.answer.isASolution;
		if (responseIsGood){
			System.out.println("Réponse correcte");
	
		}else{

			System.out.println("Réponse incorrecte");
		}
		if (propsal.answer.isASolution){
			proposal_html.getElement().getStyle().setBackgroundColor("#99FF99");
			
		}else{
			proposal_html.getElement().getStyle().setBackgroundColor("#FF6666");
		}


			
		explanation.setHTML(propsal.answer.explanation);
		
		if (responseIsGood){
			ValueChangeEvent.fire(this, new Message(Message.GOOD));
		}else{
			ValueChangeEvent.fire(this, new Message(Message.BAD));
		}
		
	}
	

	@UiHandler("next")
	void onNextClick(ClickEvent event) {
		if (nb_clicked>=1){
			ValueChangeEvent.fire(this,(HasName) new Message(Message.NEW));
		}
	}

	@UiHandler("proposal_0")
	void onProposal_0Click(ClickEvent event) {
		manageClick(proposal_0,qcmItem.proposal_0);
	}

	@UiHandler("proposal_1")
	void onProposal_1Click(ClickEvent event) {
		manageClick(proposal_1,qcmItem.proposal_1);
	}
	@UiHandler("proposal_2")
	void onProposal_2Click(ClickEvent event) {
		manageClick(proposal_2,qcmItem.proposal_2);
	}
	@UiHandler("proposal_3")
	void onProposal_3Click(ClickEvent event) {
		manageClick(proposal_3,qcmItem.proposal_3);
	}
}
