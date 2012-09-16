package com.pennapps.vnd.ffling;

public class Message {

	private String comments = null;
	private String author = null;
	private String timeStamp = null;
	private String postNumber = null;
	private String latitude = null;
	private String longitude = null;
	private String subject = null;
	private String radius = null;
	
	
	public Message(){
		/* Default constructor */
	}
	
	public Message(String user, String time, String lat, String longtude, String text){
		comments = text;
		author = user;
		timeStamp = time;
		postNumber = null;
		latitude = lat;
		longitude = longtude;
	}
	
	public Message(String user, String time, String text, String post){
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
	public Message(String user, String time, String lat,
			String currLong, String text, String subject, String radius){
		comments = text;
		author = user;
		timeStamp = time;
		postNumber = null;
		latitude = lat;
		longitude = currLong;
	}
	
	public void setRadius(String radius){
		this.radius = radius;
	}
	
	public void setSubject(String subj){
		this.subject = subj;
	}
	
	public void setComments(String newText){
		if (newText != null)
			this.comments = newText;
	}
	
	public void setAuthor(String newGuy){
		if (newGuy != null)
			this.author = newGuy;
	}
	
	public void setTime(String newTime){
		if (newTime != null)
			this.timeStamp = newTime;
	}
	
	public void setLatitude(String newLat){
		if (newLat != null)
			this.latitude = newLat;
	}
	
	public void setLongitude(String newLong){
		if (newLong != null)
			this.longitude = newLong;
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
	
	public String getSubject(){
		return subject;
	}
	
	public String getRadius(){
		return radius;
	}
	
	public String toString(){
		return "Subject: " + subject + " Radius: " + radius;
	}

}
