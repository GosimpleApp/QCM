package com.gosimpleapp.qcm.client.model.storage;

import com.google.gwt.core.client.JavaScriptObject;

public class DistantUser extends JavaScriptObject {
	protected DistantUser() {} 
	
	public final native String getMail() /*-{ return this.email; }-*/;
	public final native String getId() /*-{ return this.id; }-*/;
	public final native String getPass() /*-{ return this.password; }-*/;

}
