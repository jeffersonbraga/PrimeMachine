package br.com.opsocial.server.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WBRFormatter {
	
	private StringBuffer stringFormatted;
	private Integer maxLengthOfString;
	
	private Integer lengthAux;
	private Integer lengthStringAux;
	private Integer realStringLength;
	private int cLinks;
	private int cSpan;
	private String strBeginAux;
	private String strEndAux;
	private Integer lengthOfWbrTag;
	private Integer k;
	private StringBuffer[] stringsBuffer;
	
	private boolean isInsertInSpans;
	private List<Integer> startIndexSpan;
	private List<Integer> endIndexSpan;
	private List<Integer> countOfWordsSpan;
	
	private boolean isInsertInLinks;
	private List<Integer> startIndexLinks;
	private List<Integer> endIndexLinks;
	private List<Integer> countOfWordsLinks;
	
	public WBRFormatter(String stringToFormat, Integer maxLength) {
		StringBuffer stringFormatted = new StringBuffer(stringToFormat);
		maxLengthOfString = maxLength + 10;
		
		startIndexLinks = new ArrayList<Integer>();
		endIndexLinks = new ArrayList<Integer>();
		countOfWordsLinks = new ArrayList<Integer>();
		isInsertInLinks = false;
		
		startIndexSpan = new ArrayList<Integer>();
		endIndexSpan = new ArrayList<Integer>();
		countOfWordsSpan = new ArrayList<Integer>();
		isInsertInSpans = false;
	}
	
	public String applyWBRInAllText() { 
		
		String[] strings = stringFormatted.toString().split(" ");
		stringsBuffer = new StringBuffer[strings.length];
		for(int i = 0; i < strings.length; i++) {
			stringsBuffer[i] = new StringBuffer(strings[i]);
		}

		lengthAux = 0;
		cLinks = 0;
		cSpan = 0;
		strBeginAux = "";
		strEndAux = "";
		lengthOfWbrTag = 0;
		k = 0;

		for(int i = 0; i < stringsBuffer.length; i++) {
			
			if(cLinks < startIndexLinks.size()) {
				if(lengthAux < (startIndexLinks.get(cLinks)+lengthOfWbrTag) && 
						(startIndexLinks.get(cLinks)+lengthOfWbrTag) <= (lengthAux+stringsBuffer[i].length())) {
					strBeginAux = stringsBuffer[i].substring(0, ((startIndexLinks.get(cLinks)+lengthOfWbrTag) - lengthAux));
					lengthAux = lengthAux + strBeginAux.length();
					k = 0;
					if(strBeginAux.length() > maxLengthOfString) {
						lengthStringAux = strBeginAux.length();
						realStringLength = strBeginAux.length()+((strBeginAux.length()/maxLengthOfString)*10);
						for(int j = maxLengthOfString; j <= realStringLength; j = j + maxLengthOfString) {
							stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
							stringFormatted.insert((lengthAux-lengthStringAux)+maxLengthOfString, "<wbr>");
							lengthStringAux = lengthStringAux - maxLengthOfString;
							k++;
						}
					}
					lengthAux = lengthAux + 
							((endIndexLinks.get(cLinks) + lengthOfWbrTag) - lengthAux) + 1;
					lengthAux = lengthAux + (10*k);
					lengthOfWbrTag = lengthOfWbrTag + (10*k);
					i = i + countOfWordsLinks.get(cLinks) - 1;
					cLinks++;
				} else if(startIndexLinks.get(cLinks) + lengthOfWbrTag == lengthAux) {
					lengthAux = lengthAux + 
							((endIndexLinks.get(cLinks) + lengthOfWbrTag) - lengthAux) + 1;
					i = i + countOfWordsLinks.get(cLinks) - 1;
					cLinks++;
				} else if (cSpan < startIndexSpan.size()){
					if(startIndexSpan.get(cSpan) + lengthOfWbrTag == lengthAux) {
						lengthAux = lengthAux + 
								((endIndexSpan.get(cSpan) + lengthOfWbrTag) - lengthAux) + 1;
						i = i + countOfWordsSpan.get(cSpan) - 1;
						cSpan++;
					} else if (lengthAux < (startIndexSpan.get(cSpan)+lengthOfWbrTag) && 
							(startIndexSpan.get(cSpan)+lengthOfWbrTag) <= (lengthAux+stringsBuffer[i].length())) {
						strBeginAux = stringsBuffer[i].substring(0, ((startIndexSpan.get(cSpan)+lengthOfWbrTag) - lengthAux));
						lengthAux = lengthAux + strBeginAux.length();
						k = 0;
						if(strBeginAux.length() > maxLengthOfString) {
							k = 0;
							lengthStringAux = strBeginAux.length();
							realStringLength = strBeginAux.length()+((strBeginAux.length()/maxLengthOfString)*10);
							for(int j = maxLengthOfString; j <= realStringLength; j = j + maxLengthOfString) {
								stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
								stringFormatted.insert((lengthAux-lengthStringAux)+maxLengthOfString, "<wbr>");
								lengthStringAux = lengthStringAux - maxLengthOfString;
								k++;
							}
						}
						lengthAux = lengthAux + 
								((endIndexSpan.get(cSpan) + lengthOfWbrTag) - lengthAux) + 1;
						lengthAux = lengthAux + (10*k);
						lengthOfWbrTag = lengthOfWbrTag + (10*k);
						i = i + countOfWordsSpan.get(cSpan) - 1;
						cSpan++;
					} else {
						lengthAux = lengthAux + stringsBuffer[i].length() + 1;
						if(stringsBuffer[i].length() > maxLengthOfString) {
							k = 0;
							lengthStringAux = stringsBuffer[i].length();
							realStringLength = stringsBuffer[i].length()+((stringsBuffer[i].length()/maxLengthOfString)*10);
							for(int j = maxLengthOfString; j <= realStringLength; j = j + maxLengthOfString) {
								stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
								stringFormatted.insert((lengthAux-lengthStringAux)+maxLengthOfString, "<wbr>");
								lengthStringAux = lengthStringAux - maxLengthOfString;
								k++;
							}
							lengthAux = lengthAux + (10*k);
							lengthOfWbrTag = lengthOfWbrTag + (10*k);
						}
					}
				} else {
					lengthAux = lengthAux + stringsBuffer[i].length() + 1;
					if(stringsBuffer[i].length() > maxLengthOfString) {
						k = 0;
						lengthStringAux = stringsBuffer[i].length();
						realStringLength = stringsBuffer[i].length()+((stringsBuffer[i].length()/maxLengthOfString)*10);
						for(int j = maxLengthOfString; j <= realStringLength; j = j + maxLengthOfString) {
							stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
							stringFormatted.insert((lengthAux-lengthStringAux)+maxLengthOfString, "<wbr>");
							lengthStringAux = lengthStringAux - maxLengthOfString;
							k++;
						}
						lengthAux = lengthAux + (10*k);
						lengthOfWbrTag = lengthOfWbrTag + (10*k);
					}
				}
			} else if (cSpan < startIndexSpan.size()) {
				if(startIndexSpan.get(cSpan) + lengthOfWbrTag == lengthAux) {
					lengthAux = lengthAux + 
							((endIndexSpan.get(cSpan) + lengthOfWbrTag) - lengthAux) + 1;
					i = i + countOfWordsSpan.get(cSpan) - 1;
					cSpan++;
				} else if (lengthAux < (startIndexSpan.get(cSpan)+lengthOfWbrTag) && 
						(startIndexSpan.get(cSpan)+lengthOfWbrTag) <= (lengthAux+stringsBuffer[i].length())) {
					strBeginAux = stringsBuffer[i].substring(0, ((startIndexSpan.get(cSpan)+lengthOfWbrTag) - lengthAux));
					lengthAux = lengthAux + strBeginAux.length();
					k = 0;
					if(strBeginAux.length() > maxLengthOfString) {
						lengthStringAux = strBeginAux.length();
						realStringLength = strBeginAux.length()+((strBeginAux.length()/maxLengthOfString)*10);
						for(int j = maxLengthOfString; j <= realStringLength; j = j + maxLengthOfString) {
							stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
							stringFormatted.insert((lengthAux-lengthStringAux)+maxLengthOfString, "<wbr>");
							lengthStringAux = lengthStringAux - maxLengthOfString;
							k++;
						}
					}
					lengthAux = lengthAux + 
							((endIndexSpan.get(cSpan) + lengthOfWbrTag) - lengthAux) + 1;
					lengthAux = lengthAux + (10*k);
					lengthOfWbrTag = lengthOfWbrTag + (10*k);
					i = i + countOfWordsSpan.get(cSpan) - 1;
					cSpan++;
				}
				else if (cLinks < startIndexLinks.size()){
					if(lengthAux < (startIndexLinks.get(cLinks)+lengthOfWbrTag) && 
							(startIndexLinks.get(cLinks)+lengthOfWbrTag) <= (lengthAux+stringsBuffer[i].length())) {
						strBeginAux = stringsBuffer[i].substring(0, ((startIndexLinks.get(cLinks)+lengthOfWbrTag) - lengthAux));
						lengthAux = lengthAux + strBeginAux.length();
						k = 0;
						if(strBeginAux.length() > maxLengthOfString) {
							lengthStringAux = strBeginAux.length();
							realStringLength = strBeginAux.length()+((strBeginAux.length()/maxLengthOfString)*10);
							for(int j = maxLengthOfString; j <= realStringLength; j = j + maxLengthOfString) {
								stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
								stringFormatted.insert((lengthAux-lengthStringAux)+maxLengthOfString, "<wbr>");
								lengthStringAux = lengthStringAux - maxLengthOfString;
								k++;
							}
						}
						lengthAux = lengthAux + 
								((endIndexLinks.get(cLinks) + lengthOfWbrTag) - lengthAux) + 1;
						lengthAux = lengthAux + (10*k);
						lengthOfWbrTag = lengthOfWbrTag + (10*k);
						i = i + countOfWordsLinks.get(cLinks) - 1;
						cLinks++;
					} else if(startIndexLinks.get(cLinks) + lengthOfWbrTag == lengthAux) {
						lengthAux = lengthAux + 
								((endIndexLinks.get(cLinks) + lengthOfWbrTag) - lengthAux) + 1;
						i = i + countOfWordsLinks.get(cLinks) - 1;
						cLinks++;
					} else {
						lengthAux = lengthAux + stringsBuffer[i].length() + 1;
						if(stringsBuffer[i].length() > maxLengthOfString) {
							k = 0;
							lengthStringAux = stringsBuffer[i].length();
							realStringLength = stringsBuffer[i].length()+((stringsBuffer[i].length()/maxLengthOfString)*10);
							for(int j = maxLengthOfString; j <= realStringLength; j = j + maxLengthOfString) {
								stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
								stringFormatted.insert((lengthAux-lengthStringAux)+maxLengthOfString, "<wbr>");
								lengthStringAux = lengthStringAux - maxLengthOfString;
								k++;
							}
							lengthAux = lengthAux + (10*k);
							lengthOfWbrTag = lengthOfWbrTag + (10*k);
						}
					}
				} else {
					lengthAux = lengthAux + stringsBuffer[i].length() + 1;
					if(stringsBuffer[i].length() > maxLengthOfString) {
						k = 0;
						lengthStringAux = stringsBuffer[i].length();
						realStringLength = stringsBuffer[i].length()+((stringsBuffer[i].length()/maxLengthOfString)*10);
						for(int j = maxLengthOfString; j <= realStringLength; j = j + maxLengthOfString) {
							stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
							stringFormatted.insert((lengthAux-lengthStringAux)+maxLengthOfString, "<wbr>");
							lengthStringAux = lengthStringAux - maxLengthOfString;
							k++;
						}
						lengthAux = lengthAux + (10*k);
						lengthOfWbrTag = lengthOfWbrTag + (10*k);
					}
				}
			} else {
				lengthAux = lengthAux + stringsBuffer[i].length() + 1;
				if(stringsBuffer[i].length() > maxLengthOfString) {
					k = 0;
					lengthStringAux = stringsBuffer[i].length();
					realStringLength = stringsBuffer[i].length()+((stringsBuffer[i].length()/maxLengthOfString)*10);
					for(int j = maxLengthOfString; j <= realStringLength; j = j + maxLengthOfString) {
						stringFormatted.insert((lengthAux-lengthStringAux)+10, "<wbr>");
						stringFormatted.insert((lengthAux-lengthStringAux)+maxLengthOfString, "<wbr>");
						lengthStringAux = lengthStringAux - maxLengthOfString;
						k++;
					}
					lengthAux = lengthAux + (10*k);
					lengthOfWbrTag = lengthOfWbrTag + (10*k);
				}
			}
		}
		
		return stringFormatted.toString();
	}
	
	public void insertWBRInLinks() { 

		Pattern pattern = Pattern.compile("<a(.*?)</a>");
		Matcher matcherLink = pattern.matcher(stringFormatted.toString());

		Integer lengthOfWbrTag = 0;
		if(matcherLink.matches()) {
			isInsertInLinks = true;
			while (matcherLink.find()) {

				Document doc = Jsoup.parseBodyFragment(matcherLink.group());
				Element element = doc.getElementsByTag("a").first();

				StringBuffer elementText = new StringBuffer(element.text());
				StringBuffer elementString = new StringBuffer(matcherLink.group());
				Integer elementLengthAux;
				Integer realStringLength;
				Integer elementLength = 0;
				Integer j = 0;
				if(element.text().length() > maxLengthOfString) {
					elementLengthAux = element.text().length();
					elementLength = element.text().length();
					realStringLength = element.text().length()+((element.text().length()/maxLengthOfString)*10);
					for(int i = maxLengthOfString; i <= realStringLength; i = i + maxLengthOfString) {
						elementText.insert((elementLength-elementLengthAux)+10, "<wbr>");
						elementText.insert((elementLength-elementLengthAux)+maxLengthOfString, "<wbr>");
						elementLengthAux = elementLengthAux - maxLengthOfString;
						j++;
					}

					elementString.replace(elementString.length()-element.text().length()-4, elementString.length()-4, elementText.toString());

					stringFormatted.replace(matcherLink.start()+lengthOfWbrTag, matcherLink.end()+lengthOfWbrTag, elementString.toString());

				}
				startIndexLinks.add(matcherLink.start()+lengthOfWbrTag);
				lengthOfWbrTag = lengthOfWbrTag + (10*j);
				endIndexLinks.add(matcherLink.end()+lengthOfWbrTag);
				countOfWordsLinks.add(matcherLink.group().split(" ").length);
			}
		}
	}

	public void insertWBRInSpans() {

		Pattern patternSpan = Pattern.compile("<span(.*?)</span>");
		Matcher matcherSpan = patternSpan.matcher(stringFormatted.toString());

		Integer lengthOfWbrTag = 0;
		if(matcherSpan.matches()) {
			isInsertInSpans = true;
			while (matcherSpan.find()) {

				Document doc = Jsoup.parseBodyFragment(matcherSpan.group());
				Element element = doc.getElementsByTag("span").first();

				StringBuffer elementText = new StringBuffer(element.text());
				StringBuffer elementString = new StringBuffer(matcherSpan.group());
				Integer elementLengthAux;
				Integer realStringLength;
				Integer elementLength = 0;
				Integer j = 0;
				if(element.text().length() > maxLengthOfString) {
					elementLengthAux = element.text().length();
					elementLength = element.text().length();
					realStringLength = element.text().length()+((element.text().length()/maxLengthOfString)*10);
					for(int i = maxLengthOfString; i <= realStringLength; i = i + maxLengthOfString) {
						elementText.insert((elementLength-elementLengthAux)+10, "<wbr>");
						elementText.insert((elementLength-elementLengthAux)+maxLengthOfString, "<wbr>");
						elementLengthAux = elementLengthAux - maxLengthOfString;
						j++;
					}

					elementString.replace(elementString.length()-element.text().length()-7, elementString.length()-7, elementText.toString());

					stringFormatted.replace(matcherSpan.start()+lengthOfWbrTag, matcherSpan.end()+lengthOfWbrTag, elementString.toString());

				}
				startIndexSpan.add(matcherSpan.start()+lengthOfWbrTag);
				lengthOfWbrTag = lengthOfWbrTag + (10*j);
				endIndexSpan.add(matcherSpan.end()+lengthOfWbrTag);
				countOfWordsSpan.add(matcherSpan.group().split(" ").length);
			}
		}
	}
	
	
}
