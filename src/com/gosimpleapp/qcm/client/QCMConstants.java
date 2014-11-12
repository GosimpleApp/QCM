package com.gosimpleapp.qcm.client;
import com.google.gwt.i18n.client.Constants;

public interface QCMConstants extends Constants {
	
	@DefaultStringValue("New course")
	String template_name_new_course();

	@DefaultStringValue("New education")
	String template_name_new_education();
	
	@DefaultStringValue("New mateer")
	String template_name_new_mateer();
	
	@DefaultStringValue("New question")
	String template_name_new_qcm_item();
	
	@DefaultStringValue("Test user")
	String template_name_new_user();
	
	@DefaultStringValue("Connect")
	String connect();
	
	@DefaultStringValue("Disconnect")
	String disconnect();
	
	@DefaultStringValue("Course is missing")
	String noCourseSpecified();
	
	@DefaultStringValue("Correct answer")
	String reponseCorrecte();
	
	@DefaultStringValue("Wrong")
	String reponseIncCorrecte();
	
	@DefaultStringValue("1.1")
	String version();
}
