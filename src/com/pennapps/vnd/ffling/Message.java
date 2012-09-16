package com.pennapps.vnd.ffling;

public class Message {

	private String subject = null;
	private String comments = null;
	private String author = null;
	private String timeStamp = null;
	private String postNumber = null;
	private String latitude = null;
	private String longitude = null;
	
	public Message(String subject, String text, String user, String time, String post){
		this.subject = subject;
		comments = text;
		author = user;
		timeStamp = time;
		postNumber = post;
		latitude = null;
		longitude = null;
	}
	
	/** This constructor can be used to create a Message object from the 
	 * front-end from the user's input to the GUI, which can be used to 
	 * submit to the back-end.
	 * **/
	public Message(String subject, String user, String time, String lat,
			String currLong, String text){
		this.subject = subject;
		comments = text;
		author = user;
		timeStamp = time;
		postNumber = null;
		latitude = lat;
		longitude = currLong;
	}
	
	public String getComments(){
		return comments;
	}
	
	public String getUserName(){
		return author;
	}
	
	public String getTimeStamp(){
		return timeStamp;
	}
	
	public String getLatitude(){
		return latitude;
	}
	
	public String getLongitude(){
		return longitude;
	}
	
	public String getTime(){
		return timeStamp;
	}
	
	public String getText(){
		return comments;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public String getPostNumber(){
		return postNumber;
	}
	public String toString(){
		return subject + "\n" + getTime();
	}
}
