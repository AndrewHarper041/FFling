package com.pennapps.vnd.ffling;

public class StringParser {
	private String parsedLine = new String();
	
	public StringParser(){
		parsedLine = new String();
	}
	
	public String createParsedString(
			String username,
			String timestamp,
			String latitude,
			String longitude,
			String comments,
			int postNumber){
		try{
			if (latitude == null || longitude == null || timestamp == null ||
					comments == null || postNumber < 0){
				throw new NullPointerException();
			}
		} catch (NullPointerException e){
			e.printStackTrace();
			System.exit(0);
		}
		parsedLine = ">>>\n";
		parsedLine += username.trim() + "~~~" + timestamp.trim() + "~~~"
				+ latitude.trim() + "~~~" + longitude.trim() + "~~~" + postNumber + "\r\n";
		parsedLine += comments + "\n";
		parsedLine += "<<<\n";
		return parsedLine;
	}
	
	public String getParsedLine(){
		return parsedLine;
	}
	
}
