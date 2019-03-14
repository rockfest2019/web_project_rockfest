package com.semernik.rockfest.tag;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VideoTag extends TagSupport{

	private static Logger logger = LogManager.getLogger();
	private static Pattern youtubePattern = Pattern.compile("www.youtube.com");
	private static String urlPartToReplace = "watch?v=";
	private static String substitution = "embed/";
	private static String videoTagStart = "<iframe width='420' height='315' src='";
	private static String videoTagEnd = "'></iframe>";
	private String url;

	public void setUrl(String url){
		this.url = url;
	}

	@Override
	public int doStartTag(){
		Matcher matcher = youtubePattern.matcher(url);
		if (matcher.find()){
			printVideoOnPage();
		}
		return SKIP_BODY;
	}

	private void printVideoOnPage() {
		String videoUrl = this.url.replace(urlPartToReplace, substitution);
		JspWriter writer = pageContext.getOut();
		try {
			writer.write(videoTagStart + videoUrl + videoTagEnd);
		} catch (IOException e) {
			logger.error("Video tag wasn't send to page", e);
		}
	}

	@Override
	public int doEndTag()  {
		return EVAL_PAGE;
	}
}
