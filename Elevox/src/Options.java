import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Options {
	// lade 3 Chunks in jede Richtung
	public static int viewDistance = 3;
	
	// Die Sprache
	public static String language = "de_de";
	
	static {
		if(!new File("options.txt").exists()) {
			saveOptions();
		}
		else {
			loadOptions();
		}
	}
	
	public static void saveOptions() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("options.txt")));
			
			bw.append("# Don´t try to edit this file manually. If it fail\n");
			bw.append("# it will be resetted!\n");
			bw.append("viewDistance="+viewDistance+"\n");
			bw.append("language="+language+"\n");
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void loadOptions() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("options.txt")));
			
			String line = "";
			while((line = br.readLine()) != null) {
				if(line.startsWith("#")) continue;
				
				if(line.split("=")[0].equals("viewDistance")) {
					Options.viewDistance = Integer.valueOf(line.split("=")[1]);
				}
				
				if(line.split("=")[0].equals("language")) {
					Options.language = line.split("=")[1];
				}
			}
			
			br.close();
		} catch (Exception e) {
			saveOptions();
		}
	}
}
