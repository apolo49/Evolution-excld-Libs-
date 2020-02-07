package EngineTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
			Quit_Type.close();
			return Line;
		}catch (IOException e) {
			Logger.IOSevereErrorHandler(e,file);
		}
		return null;
	}
}
