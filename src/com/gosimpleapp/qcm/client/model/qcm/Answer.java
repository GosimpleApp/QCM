package com.gosimpleapp.qcm.client.model.qcm;


public class Answer {
	public  String response;
	public String explanation;
	public boolean isASolution=false;
	public Proposal proposal;
	public static String SOLUTION_RESPONSE="C'est une bonne réponse";
	public static String WRONG_RESPONSE="Réponse incorrecte";
	
	public Answer(){
		this.explanation=".";
		setAsSolution();
	}
	
	public Answer(String response, String explanation, boolean isASolution){
		this.response=response;
		this.explanation=explanation;
		this.isASolution=isASolution;
	}
	public void toggle(){
		if (isASolution){
			setAsWrongAnswer();
		}else{
			setAsSolution();
		}
	}
	public void setAsSolution(){
		isASolution=true;
		response=SOLUTION_RESPONSE;
	}
	public void setAsWrongAnswer(){
		isASolution=false;
		response=WRONG_RESPONSE;
	}


}
