package StringAlgorithms;

import java.io.FileWriter;
import java.io.IOException;

public class MyFileWriter {

	public static String createFile(String fileName, String filePath, String fileText){
		String currFile = filePath + fileName;
		try {
			boolean append = false;
			FileWriter fileWriter = new FileWriter(currFile, append);
			fileWriter.write(fileText);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return currFile;
	}
	
	public static String updateFile(String name, String filePath, String fileText){
		String currFile = filePath + name;
		try {
			boolean append = true;
			FileWriter fileWriter = new FileWriter(currFile, append);
			fileWriter.write(fileText);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return currFile;
	}

}
