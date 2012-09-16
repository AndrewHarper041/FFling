package com.pennapps.vnd.ffling;

public class Demo {

	/** Demo for the FileParser/File Creator Process 
	 	@author Kristian Sooklal
	 **/
	public static void main(String[] args) {

		String path = "C:\\Users\\Kristian Sooklal\\Desktop\\a.txt";
		
		String newPath = "C:\\Users\\Kristian Sooklal\\Desktop\\";
		testFileCreation(newPath);
		//testFileUpdate(path);
		//	String latitude = "92 19 01";
		//String longitude = "02 01 00";
		//testStringFunctions(path, latitude, longitude);
		
	}
	
	/** Must specify a valid path or app will CRASH 
	 	@author Kristian Sooklal
	 	@param path
	 */
	public static void testFileUpdate(String path){
		FileCreator fc = new FileCreator(path);
		String author = "Kristian Sooklal";
		String timeStamp = "01 Jan 2012 19:20:23";
		String lat = "23 39 28";
		String longitude = "88 22 12";
		String contribution = "The view from the 6th floor was awesome.";
		Message message = new Message(author, timeStamp, lat, longitude, contribution);
		path = fc.updatePaperAirplane(message);
		System.out.print(MyFileReader.readText(path));
		System.out.print("\n" + path);
	}

	public static void testStringFunctions(String path, String testLat, String testLong){
		System.out.println("Given coordinates: \"" + testLat + "\" and \"" + 
				testLong + "\", and current file path: \"" + path + "\", \nthe resulting " +
						"new file name and current file name and directory are as follows:\n");	
		System.out.println("Current File Name: \"" + 
				FileCreator.parseCurrentName(path) + "\"");
		System.out.println("Current Directory: \"" + 
				FileCreator.parseDirectoryPath(path) + "\"");
		System.out.println("New File Name: \"" + 
				FileCreator.createFileNameFromCoordinates(testLat, testLong) + "\"");
		
		System.out.println("File Subject is: " + MyFileReader.getFileSubject(path));
		System.out.println("Time Stamp Last Modified is: " + MyFileReader.getTimeStampLastModified(path));
		System.out.println("Most Recent Contributor is: " + MyFileReader.getLastAuthor(path));
	}
	
	public static void testFileCreation(String newPath){
		FileCreator fc = new FileCreator(newPath);
		String subject = "Blah";
		String radius = "1243m";
		String author = "Kristian Sooklal";
		String timeStamp = "01 Jan 2012 19:20:23";
		String lat = "66 39 29";
		String longitude = "21 22 12";
		String contribution = "The view from the 6th floor was awesome.";
		Message message = new Message(author, timeStamp, lat, longitude, contribution);
		String path = fc.createNewPaperAirplane(radius, subject, message);
		System.out.print(MyFileReader.readText(path));
		System.out.print("\n" + path);
	}
	
	/** Must specify a valid path or the app will CRASH.
	 	@author Kristian Sooklal
	 	@param path
	 **/
	public static void testFileReader(String path){
		System.out.print(MyFileReader.getWholeFile(path).size());
	}
	
	/** Testing the string parser's ability to generate correct text 
	 	@author Kristian Sooklal
	 **/
	public static void basicTest(){
		System.out.println("Radius: 15m|||Subject: \"CheeseSteaks\"");
		StringParser a = new StringParser();
		a.createParsedString("ksooklal", "01 Jan 2011", "42 42 11", 
				"23 34 90", "I went to Philadelphia today.", 1);
		System.out.print(a.getParsedLine());
		a.createParsedString("kbryant", "02 Jan 2011", "42 42 31", 
				"23 34 90", "I went to Philadelphia today.", 2);
		System.out.print(a.getParsedLine());
	}
}
