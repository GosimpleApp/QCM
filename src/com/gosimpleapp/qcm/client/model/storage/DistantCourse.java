package com.gosimpleapp.qcm.client.model.storage;

import com.google.gwt.core.client.JavaScriptObject;

public class DistantCourse extends JavaScriptObject{
	protected DistantCourse() {} 
	
	public final native String getName() /*-{ return this.name; }-*/;
	public final native String getId() /*-{ return this.id; }-*/;
	public final native String getParentId() /*-{ return this.parent_id; }-*/;
}
