package com.pennapps.vnd.ffling;


public class Preview {
	private String filePath = null;
	private String recentAuthor = null;
	private String timeStamp = null;
	private String preview = null;
	private String subject = null;
	private int postNumber = -1;
	private String fullText = null;
	
	public Preview(String file){
		if (file != null){
			this.filePath = file;
			this.fullText = MyFileReader.readText(this.filePath);
			this.subject = MyFileReader.getFileSubject(this.filePath);
			this.timeStamp = MyFileReader.getTimeStampLastModified(this.filePath);
			this.recentAuthor = MyFileReader.getLastAuthor(this.filePath);
		}
	}

	public String getTimeStamp(){
		return timeStamp;
	}
	
	public Integer getPostNumber(){
		return postNumber;
	}
	
	public String getSubject(){
		return subject;
	}
	
	public String getMostRecentAuthor(){
		return recentAuthor;
	}

	public String getFullText(){
		return fullText;
	}
	
	public String getTimeStampLastModified(){
		return timeStamp;
	}
	
	public String getPreviewMostRecentComment(){
		return preview;
	}
	
	public String getFirstTwoLinesOfMostRecentComment(){
		return preview;
	}
	
	public String getPreview(){
		return preview;
	}
	
	public String toString(){
		return "File: " + filePath + " Time Last Modified: " + timeStamp + 
				" Last Contributor: " + recentAuthor + " Subject: " + subject
				+ " Preview: " + preview;
	}
}
