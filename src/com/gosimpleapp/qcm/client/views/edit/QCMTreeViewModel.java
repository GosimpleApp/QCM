package com.gosimpleapp.qcm.client.views.edit;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.gosimpleapp.qcm.client.model.education.Course;
import com.gosimpleapp.qcm.client.model.education.Education;
import com.gosimpleapp.qcm.client.model.education.Mateer;
import com.gosimpleapp.qcm.client.model.qcm.QCMItem;
import com.gosimpleapp.qcm.client.model.user.User;

public class QCMTreeViewModel extends ArrayList<HasName> implements TreeViewModel {

	
	private static final long serialVersionUID = 1L;
	public final SingleSelectionModel<HasName> selectionModel = new SingleSelectionModel<HasName>();
	User user;
	public QCMTreeViewModel(User user){
		this.user=user;
	}
	
	
	Object selectedObject;
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			System.out.println( "NodeInfo Null");
			AbstractCell<HasName> cell = new AbstractCell<HasName>("click") {
				@Override
				public void render(
						com.google.gwt.cell.client.Cell.Context context,
						HasName value, SafeHtmlBuilder sb) {
					sb.appendEscaped(value.getName());
					Education education=(Education) value;
					if (education.year!=0){
						sb.appendEscaped(" ");
						sb.appendEscaped(""+education.year);
					}
				}
			};
			return new DefaultNodeInfo<HasName>(user, cell);

		} else if (value instanceof Education) {
			System.out.println( "NodeInfo "+((HasName) value).getName());

			select((HasName) value);
			Cell<HasName> cell = new AbstractCell<HasName>() {

				@Override
				public void render(
						com.google.gwt.cell.client.Cell.Context context,
						HasName value, SafeHtmlBuilder sb) {
					sb.appendEscaped(value.getName());
				}

			};
			return new DefaultNodeInfo<HasName>((Education) value, cell);

		} else if (value instanceof Mateer) {
			System.out.println( "NodeInfo "+((HasName) value).getName());
			select((HasName) value);

			Cell<HasName> cell = new AbstractCell<HasName>()  {
				@Override
				public void render(
						com.google.gwt.cell.client.Cell.Context context,
						HasName value, SafeHtmlBuilder sb) {
					sb.appendEscaped(value.getName());
				}

			};
			return new DefaultNodeInfo<HasName>((Mateer) value, cell);

		}else if (value instanceof Course) {
			System.out.println( "NodeInfo "+((HasName) value).getName());
			select((HasName) value);			
			Cell<HasName> cell = new AbstractCell<HasName>("click") {
				@Override
				public void render(
						com.google.gwt.cell.client.Cell.Context context,
						HasName value, SafeHtmlBuilder sb) {
					sb.appendEscaped(value.getName());
				}
				 @Override
				    public void onBrowserEvent(Context context, Element parent, HasName qcmItem, NativeEvent event,
				        ValueUpdater<HasName> valueUpdater) {
				      super.onBrowserEvent(context, parent, qcmItem, event, valueUpdater);
				      if ("click".equals(event.getType())) {
				    	  	select(qcmItem);
				    	  	System.out.println( "Click qcmQCMItem");
					      }
				    }	
			};
			return new DefaultNodeInfo<HasName>((Course) value, cell);

		}

		return null;
	}

	@Override
	public boolean isLeaf(Object value) {
		if (value instanceof QCMItem) {
	         return true;
	     }
		return false;
	}

	void select (HasName hasName)
	{
		selectionModel.setSelected(hasName, true);
		
	}
	
}
