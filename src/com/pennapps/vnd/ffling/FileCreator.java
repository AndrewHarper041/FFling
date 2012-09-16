package com.pennapps.vnd.ffling;

import java.util.ArrayList;

public class FileCreator {

	private String oldFileText = null;
	private String destination = null;
	
	public FileCreator(String filePath){
		destination = filePath;
	}
	
	public String createNewPaperAirplane(String radius, String subject,
			String username,
			String timestamp,
			String latitude,
			String longitude,
			String comments){
		oldFileText = "Properties: " + radius + "~~~" + "Subject: " + subject + "\n";
		StringParser a = new StringParser();
		String newFileText = oldFileText + a.createParsedString(username, timestamp, 
				latitude, longitude, comments, 1);
		String newFileName = createFileNameFromCoordinates(latitude, longitude);
		//String directory = parseDirectoryPath(this.destination);
		return MyFileWriter.createFile(newFileName, this.destination, newFileText);
	}
	
	public String createNewPaperAirplane(String radius, String fileSubject, 
			Message userInput){
		return createNewPaperAirplane(radius, fileSubject, 
				userInput.getAuthor(),
				userInput.getTime(),
				userInput.getLatitude(),
				userInput.getLatitude(),
				userInput.getText());
	}
	
	public String updatePaperAirplane(
			String username, 
			String timestamp, 
			String latitude,
			String longitude,
			String comments){
		oldFileText = MyFileReader.readText(this.destination);
		int count = MyFileReader.getWholeFile(this.destination).size();
		StringParser a = new StringParser();
		String newFileInfo = a.createParsedString(username, timestamp, 
				latitude, longitude, comments, count + 1);
		String newFileText = oldFileText + newFileInfo;
		String newFileName = createFileNameFromCoordinates(latitude, longitude);
		String newPath = parseDirectoryPath(this.destination);
		String oldFileName = parseCurrentName(this.destination);
		if (oldFileName != null && newFileName != null && newFileName.equals(oldFileName)){
			return MyFileWriter.updateFile(newFileName, newPath, newFileText);
		}
		return MyFileWriter.createFile(newFileName, newPath, newFileText);
	}
	
	public static String parseCurrentName(String path){
		if (path == null)
			return null;
		int startIndex = path.lastIndexOf("\\");
		if (startIndex < 1 || startIndex >= path.length())
			return null;
		return path.substring(startIndex + 1);
	}
	
	public static String parseDirectoryPath(String path){
		if (path == null)
			return null;
		int endIndex = path.lastIndexOf("\\");
		if (endIndex < 1)
			return null;
		return path.substring(0, endIndex + 1);
	}
	
	public static String createFileNameFromCoordinates(String latitude, String longitude){
		if (latitude != null)
			latitude = latitude.trim();
		if (longitude != null)
			longitude = longitude.trim();
		String name = latitude + "~" + longitude + ".txt";
		return name;
	}
	
	public String updatePaperAirplane(Message e){
		return updatePaperAirplane(
				e.getAuthor(),
				e.getTime(),
				e.getLatitude(),
				e.getLongitude(),
				e.getText());
	}
	
	/** Use this method if you'd like to be able to get a data structure
	  	for any given file path.
	  	@author Kristian Sooklal
	 **/
	public static ArrayList<Message> getSingleFileDataStructure(String filePath){
		return MyFileReader.getWholeFile(filePath);
	}
	
	/** Use this method if you already have created an instance of the FileCreator
	 	Object and you'd like to be able to get a data structure
	  	for the current object.
	 **/
	public ArrayList<Message> getSingleFileDataStructure(){
		return MyFileReader.getWholeFile(this.destination);
	}
}
