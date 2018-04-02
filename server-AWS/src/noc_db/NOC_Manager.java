/*package noc_db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class NOC_Manager {

	private HashMap<String, String> superlatives;

	public NOC_Manager(){

	}

	public void setup() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\user\\EclipseWorkspace\\SEProject\\server-AWS\\src\\noc_db\\TSV Lists\\superlatives.txt"));
		String line = br.readLine();

		while (line != null) {
			String[] data = line.split("\t");
			if(data.length == 2){
				superlatives.put(data[0], data[1]);
				//System.out.println(data[0] + " , " + data[1]);
			}
			line = br.readLine();
		}

		for(String s : superlatives.keySet()){
			System.out.println(s);
		}

		br.close();
	}
}
*/