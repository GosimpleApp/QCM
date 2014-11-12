package com.gosimpleapp.qcm.client.views.components;


import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;

public  class HTMLAdatativeFontSizeWidget extends Composite implements HasText, HasResizeLayoutPanel,HasClickHandlers{

	
	static int NB_CHAR_MAX=30;
	static double RATIO_WIDTH=0.8;
	static double RATIO_HEIGHT=1.8;
	
	String text="";
	String textMaxWord="";


	int characterWidth;
	int characterHeight;

	double estimatedWidth;
	double estimatedHeight;

	double containerWidth;
	double containerHeight;

	HTML htmlWidget;


	ResizeLayoutPanel resizeLayoutPanel;

	public HTMLAdatativeFontSizeWidget() {
		htmlWidget=new HTML();
		setText("");
		commonSettings();

	}

	public HTMLAdatativeFontSizeWidget(String data) {
		super();
		htmlWidget=new HTML();
		setText(data);
	}

	public void commonSettings(){


		htmlWidget.setWidth("100%");
		htmlWidget.setHeight("100%");


		resizeLayoutPanel=new ResizeLayoutPanel();
		resizeLayoutPanel.add(htmlWidget);

		initWidget(resizeLayoutPanel);
		
		htmlWidget.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		//htmlWidget.getElement().getStyle().setMargin(5, Style.Unit.PT);
		
		getResizeLayoutPanel().addResizeHandler(new ResizeHandler(){

			@Override
			public void onResize(ResizeEvent event) {
				setFontSize();

			}});
		
	}

	@Override
	public void onLoad() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				setFontSize();
			}
		});
	}


	@Override
	public String getText() {
		return text;
	}


	public  void setContent(){
	
		String t=textMaxWord;
		while (t.endsWith("\n")){
			t=t.substring(0, t.length()-1);
		}
		htmlWidget.setHTML(t.replaceAll("\n", "<br>"));
	
	}

	@Override
	public void setText(String text) {
		this.text=text;	
		setTextBox(getText());
		setFontSize() ;
		setContent();
	}

	void setFontSize() 
	{
		if (text.length()==0) return;

		containerWidth=getOffsetWidth();
		containerHeight=getOffsetHeight();

	
		
		double expectedFontWidth=containerWidth/ (characterWidth*RATIO_WIDTH);
		double expectedFontHeight=containerHeight/ (characterHeight*RATIO_HEIGHT);

		double fontSize=Math.min(expectedFontWidth,expectedFontHeight);

		double expectedWidth=characterWidth*fontSize*RATIO_WIDTH;
		double expectedHeight=characterHeight*RATIO_HEIGHT*fontSize;

		Style elementStyle=getElement().getStyle();
		elementStyle.setFontSize(fontSize, Style.Unit.PT);
		
	//	System.out.println("For text "+text);
		
		double padding_top=Math.max(3,(containerHeight-expectedHeight)/2);
		double padding_left=Math.max(3,(containerWidth-expectedWidth)/2);

	//	System.out.println("Expected padding top "+padding_top);
	//	System.out.println("Expected padding left "+padding_left);
		
		htmlWidget.getElement().getStyle().setPaddingTop(padding_top, Style.Unit.PT);
		//htmlWidget.getElement().getStyle().setPaddingBottom(padding_top, Style.Unit.PT);
		
		htmlWidget.getElement().getStyle().setPaddingLeft(padding_left, Style.Unit.PT);
		//htmlWidget.getElement().getStyle().setPaddingRight(padding_left, Style.Unit.PT);
		

	//	System.out.println("Width "+containerWidth);
	//	System.out.println("Expected width "+expectedWidth);
	//	System.out.println("Padding left "+padding_left);

	//	System.out.println("Height "+containerHeight);
	//	System.out.println("Expected height "+expectedHeight);
	//	System.out.println("Padding top "+padding_top);


		setContent();

	}


	void setTextBox(String text){
		
		textMaxWord=format( text,NB_CHAR_MAX);
		while (textMaxWord.startsWith("\n")){
			textMaxWord=textMaxWord.substring(1, textMaxWord.length());
		}
		while (textMaxWord.endsWith("\n")){
			textMaxWord=textMaxWord.substring(0, textMaxWord.length()-1);
		}
		
	//	System.out.println("-----------------Formatted text = \n"+textMaxWord);
		String[] lines = textMaxWord.split("\r\n|\r|\n");
	
		characterHeight= lines.length;

		characterWidth=0;
		for (int i=0;i<characterHeight;i++)
		{
			String line=lines[i].replaceAll("\t", "&nbsp;&nbsp;&nbsp;");
			
			int length;
			if (lines[i].endsWith("\n")){
				length=line.length()-1;
			}else{
				length=line.length();
			}
			System.out.println(line+":"+length);
			
			if (length>characterWidth){
				characterWidth=length;
			}

		}
		//System.out.println("////////// Size is "+characterWidth+","+characterHeight);
	

	}

	@Override
	public ResizeLayoutPanel getResizeLayoutPanel() {
		return resizeLayoutPanel;
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {

		return htmlWidget.addClickHandler(handler);
	}

	private static final char NEWLINE = '\n';
	private static final String SPACE_SEPARATOR = " ";
	
	
	public  String format(String input, int maxLineLength) {
		String[] tokens = input.split("\r\n|\r|\n");
		String out="";
		for (int i = 0; i < tokens.length; i++) {
			String line_out=addLinebreaks(tokens[i]);
			if (!line_out.endsWith("\n")){
				line_out+="\n";
			}
			out+= line_out;
		}
		return out;
	}
	
	public String addLinebreaks(String input) {
		String[] tok =  input.split( " ");
	    StringBuilder output = new StringBuilder(input.length());
	    int lineLen = 0;
		for (int i = 0; i < tok.length; i++) {
	        String word = tok[i];

	        if (lineLen + word.length() > NB_CHAR_MAX) {
	            output.append("\n");
	            lineLen = 0;
	        }
	        output.append(word+" ");
	        lineLen += word.length();
	    }
		//System.out.println("++++++ Line formatted "+output.toString());
	    return output.toString();
	}
	


}
