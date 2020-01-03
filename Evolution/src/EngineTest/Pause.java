package EngineTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import fileHandling.log.Logger;
import python.PyExecuter;

public class Pause {

	public static String main() {
		File file = new File(System.getenv("APPDATA")+"\\Evolution\\logs\\Latest.txt");
		PyExecuter.main(null, "Pause.py");
		BufferedReader Quit_Type;
		try {
			Quit_Type = new BufferedReader(new FileReader("src//python//obj//Quit_Type.txt"));
			String Line;
			Line = Quit_Type.readLine();
			return Line;
		}catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Logger.main("[SEVERE]"+exceptionAsString,-1,file);
			System.exit(-1);
		}
		return null;
	}
}
