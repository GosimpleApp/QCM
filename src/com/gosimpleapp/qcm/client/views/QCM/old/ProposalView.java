package com.gosimpleapp.qcm.client.views.QCM.old;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gosimpleapp.qcm.client.model.qcm.Proposal;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;


public class ProposalView extends Composite{

	private static ProposalViewUiBinder uiBinder = GWT
			.create(ProposalViewUiBinder.class);
	@UiField HTMLPanel proposal_htmlPanel;
	@UiField public CheckBox checkBox;

	interface ProposalViewUiBinder extends UiBinder<Widget, ProposalView> {
	}

	public ProposalView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public ProposalView(Proposal proposal) {
		initWidget(uiBinder.createAndBindUi(this));
		proposal_htmlPanel.add(new HTMLPanel(proposal.statement));
	}
	@UiHandler("checkBox")
	void onCheckBoxClick(ClickEvent event) {
	}

}
