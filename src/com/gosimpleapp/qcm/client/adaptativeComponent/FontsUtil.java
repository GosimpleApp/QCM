package com.gosimpleapp.qcm.client.adaptativeComponent;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;


public class FontsUtil {

	public static Map<String,Map<Integer,Double>> widthPerFont=new HashMap<String,Map<Integer,Double>>();
	public static Map<String,Integer> widthReference =new HashMap<String,Integer>();
	public static Map<String,Integer> heightReference=new HashMap<String,Integer>();
	public static Map<String,Integer> interLineReference=new HashMap<String,Integer>();

	public static Map<String,Integer> containerWidth=new HashMap<String,Integer>();
	public static Map<String,Integer> containerHeight=new HashMap<String,Integer>();
	
	
	
	public static  Map<Integer,Double> checkSet(String font){
		if (!widthPerFont.containsKey(font)){
			return build( font);
		}else{
			return widthPerFont.get(font);
		}

	}
	public static Map<Integer,Double> build(String font){
		Map<Integer,Double> widthes=new HashMap<Integer,Double>();
		RootPanel testZone=RootPanel.get("testZone") ;
		if (testZone==null) return null;

		testZone.getElement().getStyle().setProperty("fontFamily", font);
		
		
		testZone.getElement().setInnerHTML("A<br>B");
		int twoLine= testZone.getOffsetHeight();
		int maxWidth=0;
		Map<Integer,Integer> widthMap =new HashMap<Integer,Integer>();
		Character maxChar=Character.toChars(0)[0];
		
		for(int i=32;i<127;++i) {

			Character c=Character.toChars(i)[0] ;
			testZone.getElement().setInnerHTML(""+c);
		
			int w = testZone.getOffsetWidth(); // pixel
		
			if (!heightReference.containsKey(font) && testZone.getOffsetHeight()!=0){
				heightReference.put(font,testZone.getOffsetHeight());
				System.out.println("*** Height "+heightReference.get(font));
			}
			if (w>maxWidth){
				maxWidth=w;
				maxChar=c;
			}
			widthMap.put(i, w);

		}
		testZone.getElement().setInnerHTML(""+maxChar);
		containerWidth.put(font, testZone.getOffsetWidth());
		containerHeight.put(font, testZone.getOffsetHeight());
		
		
		System.out.println("*** 1 character = "+testZone.getOffsetWidth()+","+testZone.getOffsetWidth()+" px");
		
		System.out.println("*** Max width "+maxWidth);
		if (maxWidth==0) return null;
		
		widthReference.put(font, maxWidth);
		for(int i=32;i<127;++i) {
			int w=widthMap.get(i);
			if (w!=maxWidth){
				widthes.put(i,(double)w / (double) maxWidth);
			}
		}
		interLineReference.put(font,twoLine-2*(testZone.getOffsetHeight()));
		System.out.println("*** Interline "+interLineReference.get(font));
		widthPerFont.put(font, widthes);
		testZone.getElement().setInnerHTML("Type your text here");
		System.out.println("*** Final size "+testZone.getElement().getOffsetWidth());
		return widthes;

	}

	public static void setSize (String font,String text,HTML html,ResizeLayoutPanel resizeLayoutPanel){

		System.out.println("---------- Text "+text);
		if (checkSet( font)==null) return;
		double maxWidth=0;
		double currentWidth=0;
		int maxHeight=1;

		Map<Integer,Double> currentWidthes=widthPerFont.get(font);
		double widthRef=(double) widthReference.get(font);
		
		for (int i=0;i<text.length();i++){

			int aChar = (int) text.charAt(i);
			if (aChar=='\n'){
				maxHeight++;
				if (currentWidth>maxWidth){
					maxWidth=currentWidth;
					currentWidth=0;
				}
				currentWidth=0;
			}
			if (currentWidthes.containsKey(aChar)){
				currentWidth+=currentWidthes.get(aChar);
				//System.out.println("+* "+currentWidthes.get(aChar)+ " -> "+currentWidth);
			}else{
				currentWidth+=1;

			}

		}
		if (maxWidth==0) maxWidth=currentWidth;
		int interLineSize=interLineReference.get(font);
		

		double expectedWidth=	(double) containerWidth.get(font)*maxWidth;
		double brutHeight=(double) containerHeight.get(font)*(double) maxHeight;
		double interHeight=((double) interLineSize)*(maxHeight-1);
		double expectedHeight=	brutHeight+interHeight;
	
		
//		expectedWidth character = "+expectedWidth+" px");
		

		int widgetWidth=resizeLayoutPanel.getOffsetWidth();
		int widgetHeight=resizeLayoutPanel.getOffsetHeight();

		System.out.println("One c 12 px give "+containerWidth.get(font)+" , "+containerHeight.get(font));
		
		System.out.println("Nb chars "+maxWidth+" ," +maxHeight);
		System.out.println("At 12 px it would be "+expectedWidth+" , "+
				brutHeight+ "/"+interHeight+ "("+interLineSize
				);
		
//      1 character is 12px give containerWidth px
//		real width is  = "+widgetWidth+" px");
//      at 12px it gives expectedWidth
//      at 12/expectedWidth it gives 1
		
		

		double ratioWidth=(double) widgetWidth/  expectedWidth;
		double ratioHeight=(double) widgetHeight/ expectedHeight;

		System.out.println("Container size  =" +widgetWidth+" ," +widgetHeight);
		System.out.println("Ratios =" +ratioWidth+" ," +ratioHeight);
		
		double correctRatio= Math.min(ratioWidth, ratioHeight);

		if (correctRatio<1) correctRatio=1;
		if (correctRatio>4) correctRatio=4;
		System.out.println("Set font size to "+12.0*correctRatio);
		

		html.getElement().getStyle().setFontSize(12.0*correctRatio, Style.Unit.PX);

		//double newWidth=html.getElement().getFirstChildElement().getOffsetWidth();
		//double newHeight=html.getElement().getFirstChildElement().getOffsetHeight();
		
		//double remainingWidth=(widgetWidth-newWidth)/2.0;
		//double remainingHeight=(widgetHeight-newHeight)/2.0;
		
		//html.getElement().getFirstChildElement().getStyle().setProperty("paddingLeft",  remainingWidth, Style.Unit.PX);
		//html.getElement().getFirstChildElement().getStyle().setProperty("paddingTop",  remainingHeight, Style.Unit.PX);
		
	
		//System.out.println("Current size  "+html.getElement().getFirstChildElement().getOffsetWidth()+" , "+html.getElement().getFirstChildElement().getOffsetHeight());
	}


}
