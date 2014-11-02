package com.gosimpleapp.qcm.client.model.storage;

import com.google.gwt.core.client.JavaScriptObject;

public class MessageFromServer  extends JavaScriptObject  {

	protected MessageFromServer() {} 
	
	public final native String getCode() /*-{ return this.code; }-*/;
	public final native String getStatus() /*-{ return this.status; }-*/;
}
