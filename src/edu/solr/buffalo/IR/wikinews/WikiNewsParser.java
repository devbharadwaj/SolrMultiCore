package edu.solr.buffalo.IR.wikinews;



import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.Normalizer;


public class WikiNewsParser {
	/* TODO */
	/**
	 * Method to parse section titles or headings.
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Sections
	 * @param titleStr: The string to be parsed
	 * @return The parsed string with the markup removed
	 */
	public static ArrayList<String> parse(String titleStr) {
	
			ArrayList<String> text_links = new ArrayList<String>();			
			titleStr=Pattern.compile("[=]+[ ]*([a-zA-Z0-9\\- ,]+)[ ]*[=]+").matcher(titleStr).replaceAll("").trim();
			text_links.add(parseListItem(titleStr));
			String iLinks = parseInternalLinks(titleStr);
			text_links.add(iLinks);
			String eLinks = parseExternalLinks(titleStr);
			text_links.add(eLinks);
			return text_links;
 
	}
	
	/* TODO */
	/**
	 * Method to parse list items (ordered, unordered and definition lists).
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Lists
	 * @param itemText: The string to be parsed
	 * @return The parsed string with markup removed
	 */
	public static String parseListItem(String itemText) {
		
		
			itemText=Pattern.compile("\\*").matcher(itemText).replaceAll("").trim();
			return parseTextFormatting(itemText);
	}
	
	/* TODO */
	/**
	 * Method to parse text formatting: bold and italics.
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Text_formatting first point
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTextFormatting(String boldText) {
			boldText=Pattern.compile("'*").matcher(boldText).replaceAll("").trim();
			boldText=Pattern.compile("\\\\\"").matcher(boldText).replaceAll("");
			boldText=Pattern.compile("\\:\\[^:|/\\]*?\\:").matcher(boldText).replaceAll("");
			return parseAccent(boldText);
		
	}
	
	
	
	/* TODO */
	/**
	 * Method to parse *any* HTML style tags like: <xyz ...> </xyz>
	 * For most cases, simply removing the tags should work.
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed.
	 */
	public static String parseAccent(String accentText) {
	
		accentText = Normalizer.normalize(accentText, Normalizer.Form.NFD);
		accentText = accentText.replaceAll("[\\p{InCombiningDiacriticalMarks}]+", "") ;
			
			return parseTagFormatting(accentText);
	}
	
	
	/* TODO */
	/**
	 * Method to parse *any* HTML style tags like: <xyz ...> </xyz>
	 * For most cases, simply removing the tags should work.
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed.
	 */
	public static String parseTagFormatting(String htmlText) {
	
			htmlText = Pattern.compile("&lt;(.*?)&gt;",Pattern.MULTILINE | Pattern.DOTALL).matcher(htmlText).replaceAll("");
			htmlText = Pattern.compile("&mdash;").matcher(htmlText).replaceAll("");
			htmlText = Pattern.compile("&ndash;").matcher(htmlText).replaceAll("");
			htmlText = Pattern.compile("&quot;").matcher(htmlText).replaceAll("");
			htmlText = Pattern.compile("&amp;").matcher(htmlText).replaceAll("");
			htmlText = Pattern.compile("<br.*?/>").matcher(htmlText).replaceAll("");
			htmlText = Pattern.compile("<br>").matcher(htmlText).replaceAll("");
			htmlText = Pattern.compile("<hr[ ]*?/>").matcher(htmlText).replaceAll("");
			return linksText(htmlText);
	}
	
	/* TODO */
	/**
	 * Method to parse wikipedia templates. These are *any* {{xyz}} tags
	 * For most cases, simply removing the tags should work.
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	
	
	
	public static String linksText(String linksText) {
		linksText = Pattern.compile("\\[\\[([^:=|]*?)\\]\\]").matcher(linksText).replaceAll(" $1 ");
		linksText = Pattern.compile("\\[\\[.*?\\|([^|]*?)\\]\\]").matcher(linksText).replaceAll(" $1 ");
		linksText = Pattern.compile("\\[\\[File:.*\\|(.*?)\\]\\]").matcher(linksText).replaceAll("");
		linksText = Pattern.compile("\\[\\[[cC]ategory:(.*?)\\]\\]").matcher(linksText).replaceAll(" $1 ");
		linksText = Pattern.compile("http.*? ").matcher(linksText).replaceAll(" ");
		return parseTemplates(linksText);
		
	}
	
	
	public static String parseTemplates(String textTemplate) {
		
		textTemplate=Pattern.compile("\\{\\{date.*?\\}\\}").matcher(textTemplate).replaceAll("");
		
		textTemplate=Pattern.compile("\\{\\{.*?\\|[= ]*([^:|]*?)\\}\\}").matcher(textTemplate).replaceAll(" $1 ");	
		
		textTemplate=Pattern.compile("\\{\\{([^=:|]+?)\\}\\}").matcher(textTemplate).replaceAll("");
		textTemplate=Pattern.compile("\\{\\{.*").matcher(textTemplate).replaceAll("");
		textTemplate=Pattern.compile("\\}\\}").matcher(textTemplate).replaceAll("");
		textTemplate=Pattern.compile("\\|[^=]+=").matcher(textTemplate).replaceAll("");
		
		textTemplate=Pattern.compile("\\n").matcher(textTemplate).replaceAll(" ");
		textTemplate=Pattern.compile("\\| .*?=").matcher(textTemplate).replaceAll(" ");
		return linksRemove(textTemplate);
		
	}
	
	
	
	public static String linksRemove(String linksRemove) {
		linksRemove = Pattern.compile("\\[+.*?\\]+").matcher(linksRemove).replaceAll(" ");
		return linksRemove;
	}
	/* TODO */
	/**
	 * Method to parse links and URLs.
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Links_and_URLs
	 * @param text: The text to be parsed
	 * @return An array containing two elements as follows - 
	 *  The 0th element is the parsed text as visible to the user on the page
	 *  The 1st element is the link url
	 */
	
	public static String parseInternalLinks(String internalLinks) {
		
		internalLinks = Normalizer.normalize(internalLinks, Normalizer.Form.NFD);
		internalLinks = internalLinks.replaceAll("[\\p{InCombiningDiacriticalMarks}]+", "") ;
		
		String interLinks = new String();
	    
		internalLinks = Pattern.compile("\\[\\[File:.*?\\|.*?\\]\\]").matcher(internalLinks).replaceAll("");
		internalLinks = Pattern.compile("\\[\\[w:([^|:]+)\\|.*?\\]\\]").matcher(internalLinks).replaceAll("##en.wikipedia.org/wiki/$1##");
		internalLinks = Pattern.compile("\\[\\[([^:|]+)\\]\\]").matcher(internalLinks).replaceAll("[[[en.wikinews.org/wiki/$1]]]");
		
		Pattern pattern = Pattern.compile("##(.*?)##");
		Matcher matcher = pattern.matcher(internalLinks);
		while (matcher.find()) 
		{			
				interLinks += matcher.group(1)+",";
		}
		
	    
		Pattern pattern1 = Pattern.compile("\\[\\[\\[(.*?)\\]\\]\\]");
		Matcher matcher1 = pattern1.matcher(internalLinks);
		while (matcher1.find()) 
		{			
				interLinks += matcher1.group(1)+",";
		}	
		
		interLinks=interLinks.replaceAll(" ", "_");
		interLinks = Pattern.compile("\\|[^,]*?,").matcher(interLinks).replaceAll("");
		
		if (interLinks.length() > 0) {
			interLinks = interLinks.substring(0, interLinks.length()-1);
			return interLinks;
		} else return "";
	}
	
	
	public static String parseExternalLinks(String externalLinks) {
		
		externalLinks = Normalizer.normalize(externalLinks, Normalizer.Form.NFD);
		externalLinks = externalLinks.replaceAll("[\\p{InCombiningDiacriticalMarks}]+", "") ;
		
		externalLinks = Pattern.compile("\\{\\{source\\|url=(\\S+)").matcher(externalLinks).replaceAll("[[[$1]]]");
		externalLinks = Pattern.compile("\\[(http:\\S+) \\S+\\]").matcher(externalLinks).replaceAll("[[[$1]]]");
		String make = Pattern.compile("\\[\\[[cC]ategory:(.+)\\]\\]").matcher(externalLinks).replaceAll("[[[en.wikinews.org/wiki/Category:$1]]]");
		externalLinks=make.replaceAll(" ", "_");
		
		String externLinks = new String();
		Pattern pattern = Pattern.compile("\\[\\[\\[(.+)\\]\\]\\]");
		Matcher matcher = pattern.matcher(externalLinks);
		while (matcher.find())
		{			
				externLinks += matcher.group(1)+",";
		}
		if (externLinks.length() > 0) {
			externLinks = externLinks.substring(0, externLinks.length()-1);
			return externLinks;
		}
		else return ""; 
		 
		
	}
		
		
}
