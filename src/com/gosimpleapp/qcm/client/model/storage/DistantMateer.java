package com.gosimpleapp.qcm.client.model.storage;

import com.google.gwt.core.client.JavaScriptObject;

public class DistantMateer extends JavaScriptObject{
	protected DistantMateer() {} 
	
	public final native String getName() /*-{ return this.name; }-*/;
	public final native String getId() /*-{ return this.id; }-*/;
	public final native String getParentId() /*-{ return this.parent_id; }-*/;
}
