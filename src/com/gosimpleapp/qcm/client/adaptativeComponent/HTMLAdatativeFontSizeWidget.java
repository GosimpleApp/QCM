package com.gosimpleapp.qcm.client.adaptativeComponent;


import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;


public  class HTMLAdatativeFontSizeWidget extends Composite implements HasText, HasResizeLayoutPanel,HasClickHandlers{

	

	String text="";
	HTML htmlWidget;

	String font="Amarante-Regular";

	ResizeLayoutPanel resizeLayoutPanel;

	public HTMLAdatativeFontSizeWidget() {
		htmlWidget=new HTML();

		
		commonSettings();
		setText("");
		
	}
	public HTMLAdatativeFontSizeWidget(String data) {
		super();
		htmlWidget=new HTML();
	
		commonSettings();
		setText(data);
	}
	
	public void setFont(String font){
		this.font=font;
		htmlWidget.getElement().getStyle().setProperty("fontFamily", font);

	}


	public void commonSettings(){


		//htmlWidget.setWidth("100%");
		//htmlWidget.setHeight("100%");

		resizeLayoutPanel=new ResizeLayoutPanel();
		resizeLayoutPanel.add(htmlWidget);

		initWidget(resizeLayoutPanel);
		
		htmlWidget.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		getResizeLayoutPanel().addResizeHandler(new ResizeHandler(){

			@Override
			public void onResize(ResizeEvent event) {
				setFontSize(font) ;

			}});
		
	}

	@Override
	public void onLoad() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				setFontSize(font) ;
			}
		});
	}


	@Override
	public String getText() {
		return text;
	}


	public  void setContent(){
		htmlWidget.setHTML("<span>"+text.replaceAll("\n", "<br>")+"</span>");
	}

	@Override
	public void setText(String text) {
		this.text=text;	
		setFontSize(font) ;
		setContent();
	}
	void setFontSize(String font) {
		FontsUtil.setSize(font, text, htmlWidget,resizeLayoutPanel);
	}
	
	@Override
	public ResizeLayoutPanel getResizeLayoutPanel() {
		return resizeLayoutPanel;
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {

		return htmlWidget.addClickHandler(handler);
	}


	

	


}
