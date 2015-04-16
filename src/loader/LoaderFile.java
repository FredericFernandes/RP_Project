package loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoaderFile {

	protected static  ArrayList<String[]> loadFile(String path) 
	{
		System.out.println("Loading "+path+"...");
		BufferedReader br = null;
		String buffer = "";
		String cvsSplitBy = " ";
		ArrayList<String[]> listLines = new ArrayList<String[]>();
		try {

			br = new BufferedReader(new FileReader(path));
			while ((buffer = br.readLine()) != null) 
			{
				String[] line = buffer.split(cvsSplitBy);
				listLines.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					System.out.println(path+" correctly loaded.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return listLines;


	}
	

}
