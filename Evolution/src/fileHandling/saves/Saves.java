package fileHandling.saves;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import fileHandling.log.Logger;
import terrains.Terrain;

public class Saves {
	
	static File logfile = new File(System.getenv("APPDATA")+"\\Evolution\\logs\\Latest.txt");
	static String savesPath = System.getenv("APPDATA")+"\\Evolution\\saves\\";

	public static void CreateDirectory(){
			try {
				if (Files.isDirectory(Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves")) == false) {
					Files.createDirectories(Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves"));
				}
			} catch (IOException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				Logger.main(exceptionAsString,-1,logfile);
	            System.exit(-1);
			}
	}
	
	public static void NewWorld(List<Terrain> terrains){
		String worldName = "New World";
		while (Files.isDirectory(Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves\\"+worldName)) == true) {
			worldName = worldName + "-";
		}
		try {
			Files.createDirectory(Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves\\"+worldName));
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Logger.main(exceptionAsString,-1,logfile);
            System.exit(-1);
		}
		main(worldName,terrains);
	}
	
	public static void NewWorld(String worldName,List<Terrain> terrains) {
		while (Files.isDirectory(Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves\\"+worldName)) == true) {
			worldName = worldName + "-";
		}
		try {
			Files.createDirectory(Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves\\"+worldName));
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Logger.main(exceptionAsString,-1,logfile);
            System.exit(-1);
		}
		main(worldName,terrains);
	}
	
	public static void main(String worldName,List<Terrain> terrains) {
		try {
			Files.createDirectory(Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves\\"+worldName+"\\maps"));
			FileInputStream heightMap = new FileInputStream("res//textures//world//heightMap.png");
			FileInputStream worldMap = new FileInputStream("res//textures//world//worldMap.png");
			Files.copy(heightMap,Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves\\"+worldName+"\\maps\\heightMap.png"),StandardCopyOption.REPLACE_EXISTING);
			Files.copy(worldMap,Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves\\"+worldName+"\\maps\\worldMap.png"),StandardCopyOption.REPLACE_EXISTING);
			Files.createDirectory(Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves\\"+worldName+"\\Entities"));
			
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Logger.main(exceptionAsString,-1,logfile);
            System.exit(-1);
		}
	}

}
