package StringAlgorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class MyFileReader {

	public static String readText(String path) {
		String sourceFile = new String();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String inputLine;
			while ((inputLine = reader.readLine()) != null){
				sourceFile += inputLine + "\n";
			}
			/* Removes the last extra newline character that is created by the while loop. */
			sourceFile = sourceFile.substring(0, sourceFile.lastIndexOf("\n"));
		}catch (Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		return sourceFile;
	}

	
	public static ArrayList<Message> getWholeFile(String filePath){
		ArrayList<Message> oneFile = new ArrayList<Message>();
		String sourceFile = readText(filePath);
		while (sourceFile != null && sourceFile.length() > 5) {
			int beginning = sourceFile.indexOf(">>>");
			int end = sourceFile.indexOf("<<<");
			if (beginning < 0 || end < 0){
				break;
			}
			String temp = sourceFile.substring(beginning + 4, end - 1);
			int newLine = temp.indexOf("\n");
			if (newLine < 1){
				break;
			}
			String firstLine = temp.substring(0, newLine);
			String text = temp.substring(newLine + 1);
			String [] properties = firstLine.split("~~~");
			if (properties == null || properties.length < 4){
				break;
			}
			String author = properties[0];
			String time = properties[1];
			String lat = properties[2];
			String longitude = properties[3];
			Message message = new Message(author, time, lat, longitude, text);
			oneFile.add(message);
			if (sourceFile.length() > (end + 5)){
				sourceFile = sourceFile.substring(end + 4);
			} else {
				sourceFile = null;
			}
		}
		return oneFile;
	}


	public static String getFileSubject(String filePath) {
		String a = readText(filePath);
		String firstLine = a.substring(0, a.indexOf(">>>") - 1);
		if (firstLine == null)
			return null;
		String [] fileProperties = firstLine.split("~~~");
		if (fileProperties == null || fileProperties.length < 2){
			return null;
		}
		String subject = fileProperties[1].replace("Subject: ", "");
		return subject.trim();
	}


	public static String getLastAuthor(String filePath) {
		String a = readText(filePath);
		int start = a.lastIndexOf(">>>") + 4;
		int end = a.lastIndexOf("<<<") - 1;
		if (start < 4 || end < 1 || (start + 5) > a.length())
			return null;
		String lastSection = a.substring(start, end);
		if (lastSection == null)
			return null;
		int subEnd = lastSection.indexOf("~~~");
		if (subEnd >= lastSection.length() || subEnd < 1)
			return null;
		return lastSection.substring(0, subEnd);
	}


	public static String getTimeStampLastModified(String filePath) {
		String a = readText(filePath);
		int start = a.lastIndexOf(">>>") + 4;
		int end = a.lastIndexOf("<<<") - 1;
		if (start < 4 || end < 1 || (start + 5) > a.length())
			return null;
		String lastSection = a.substring(start, end);
		if (lastSection == null)
			return null;
		int subStart = lastSection.indexOf("~~~") + 3;
		int subEnd = lastSection.substring(subStart).indexOf("~~~") + subStart;
		if (subStart >= lastSection.length() || subEnd >= lastSection.length()
				|| subStart < 0 || subEnd < 0)
			return null;
		return lastSection.substring(subStart, subEnd);
	}
	
	public static String getLastComment(String filePath) {
		String a = readText(filePath);
		int start = a.lastIndexOf("~~~") + 5;
		int end = a.lastIndexOf("<<<") - 1;
		if (start < 5 || end < 1 || (start + 5) > a.length() || end > a.length())
			return null;
		return a.substring(start, end);
	}
}
