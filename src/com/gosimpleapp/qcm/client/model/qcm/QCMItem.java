package com.gosimpleapp.qcm.client.model.qcm;


import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.gosimpleapp.qcm.client.model.education.Component;
import com.gosimpleapp.qcm.client.model.education.Course;
import com.gosimpleapp.qcm.client.model.storage.DistantQCMItem;
import com.gosimpleapp.qcm.client.model.storage.MemoryStorage;
import com.gosimpleapp.qcm.client.views.edit.HasName;


public class QCMItem extends Component{


	public String question="";
	public Proposal proposal_0;
	public Proposal proposal_1;
	public Proposal proposal_2;
	public Proposal proposal_3;
	Course parent;
	
	
	public  QCMItem(){
		super();
		this.proposal_0=new Proposal();
		this.proposal_1=new Proposal();
		this.proposal_2=new Proposal();
		this.proposal_3=new Proposal();
	}
	public QCMItem(Component parent,DistantQCMItem itemFromServer){
		super();
		commonConstruct(parent,itemFromServer.getQuestion(),Long.parseLong(itemFromServer.getId()));

		Answer anwser_0=new Answer(itemFromServer.getAnswer_0(),itemFromServer.getExplanation_0(),itemFromServer.getIsASolution_0().equals("1"));
		Answer anwser_1=new Answer(itemFromServer.getAnswer_1(),itemFromServer.getExplanation_1(),itemFromServer.getIsASolution_1().equals("1"));
		Answer anwser_2=new Answer(itemFromServer.getAnswer_2(),itemFromServer.getExplanation_2(),itemFromServer.getIsASolution_2().equals("1"));
		Answer anwser_3=new Answer(itemFromServer.getAnswer_3(),itemFromServer.getExplanation_3(),itemFromServer.getIsASolution_3().equals("1"));
		proposal_0=new Proposal(itemFromServer.getStatement_0(),anwser_0);
		proposal_1=new Proposal(itemFromServer.getStatement_1(),anwser_1);
		proposal_2=new Proposal(itemFromServer.getStatement_2(),anwser_2);
		proposal_3=new Proposal(itemFromServer.getStatement_3(),anwser_3);
		refresh();
	}

	public QCMItem(Component parent){
		super();
		commonConstruct(parent,MemoryStorage.constants.template_name_new_qcm_item(),-1l);
		this.proposal_0=new Proposal();
		this.proposal_1=new Proposal();
		this.proposal_2=new Proposal();
		this.proposal_3=new Proposal();
		refresh();
	}

	


	
	@Override
	public String getName() {
		return question;
	}
	@Override
	public HasName build() {
		QCMItem qcmItem= new QCMItem(parent);
		getParent().refresh();
		return qcmItem;
	}


	@Override
	public boolean hasChild() {
		return false;
	}

	@Override
	public Component getParent() {
		return parent;
	}

	@Override
	public boolean IsLeaf() {
		return true;
	}

	@Override
	public void addChild() {
	}
	@Override
	public void setName(String name) {
		question=name;
	}
	@Override
	public String getTemplateForName() {
		return MemoryStorage.constants.template_name_new_qcm_item();
	}
	@Override
	public void setParent(Component component) {
		parent=(Course) component;
		
	}
	@Override
	public boolean isTop() {
		return false;
	}
	
	@Override
	public String getDataFields()
	{
		StringBuffer sb = new StringBuffer();
		 
		sb.append("question");
		sb.append("=");
		sb.append(URL.encodeQueryString(getName()));
		sb.append("&");
		sb.append("parent_id");
		sb.append("=");
		sb.append(parent.id);
		
		//0
		sb.append("&");
		sb.append("statement_0");
		sb.append("=");
		sb.append(URL.encodeQueryString(proposal_0.statement));

		sb.append("&");
		sb.append("answer_0");
		sb.append("=");
		sb.append(URL.encodeQueryString(proposal_0.answer.response));
		
		sb.append("&");
		sb.append("explanation_0");
		sb.append("=");
		sb.append(URL.encodeQueryString(proposal_0.answer.explanation));
		
		
		sb.append("&");
		sb.append("isASolution_0");
		sb.append("=");
		sb.append(proposal_0.answer.isASolution?1:0);
		
		
		//1
		sb.append("&");
		sb.append("statement_1");
		sb.append("=");
		sb.append(URL.encodeQueryString(proposal_1.statement));

		sb.append("&");
		sb.append("answer_1");
		sb.append("=");
		sb.append(URL.encodeQueryString(proposal_1.answer.response));
		
		sb.append("&");
		sb.append("explanation_1");
		sb.append("=");
		sb.append(URL.encodeQueryString(proposal_1.answer.explanation));
		
		
		sb.append("&");
		sb.append("isASolution_1");
		sb.append("=");
		sb.append(proposal_1.answer.isASolution?1:0);
		
		
		//2
		sb.append("&");
		sb.append("statement_2");
		sb.append("=");
		sb.append(URL.encodeQueryString(proposal_2.statement));

		sb.append("&");
		sb.append("answer_2");
		sb.append("=");
		sb.append(URL.encodeQueryString(proposal_2.answer.response));
		
		sb.append("&");
		sb.append("explanation_2");
		sb.append("=");
		sb.append(URL.encodeQueryString(proposal_2.answer.explanation));
		
		
		sb.append("&");
		sb.append("isASolution_2");
		sb.append("=");
		sb.append(proposal_2.answer.isASolution?1:0);
		
		
		//3
		sb.append("&");
		sb.append("statement_3");
		sb.append("=");
		sb.append(URL.encodeQueryString(proposal_3.statement));

		sb.append("&");
		sb.append("answer_3");
		sb.append("=");
		sb.append(URL.encodeQueryString(proposal_3.answer.response));
		
		sb.append("&");
		sb.append("explanation_3");
		sb.append("=");
		sb.append(URL.encodeQueryString(proposal_3.answer.explanation));
		
		
		sb.append("&");
		sb.append("isASolution_3");
		sb.append("=");
		sb.append(proposal_3.answer.isASolution?1:0);
		return sb.toString();
	}

	@Override
	public void onResponseReceived(Request request, Response response) {
	}

	@Override
	public void loadChilds() {
	}
	@Override
	public String getChildSimpleName() {
		return null;
	}
	@Override
	public void cleanChilds(){
		refresh();
	}
	@Override
	public void setNameAndClean(String name){
		setName(name);
		getParent().refresh();
	}

	
}
