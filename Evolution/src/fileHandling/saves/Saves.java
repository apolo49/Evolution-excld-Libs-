package fileHandling.saves;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
				if (Files.isDirectory(Paths.get(System.getenv("APPDATA")+"\\Evolution\\flags and misc")) == false) {
					Files.createDirectories(Paths.get(System.getenv("APPDATA")+"\\Evolution\\flags and misc"));
				}
			} catch (IOException e) {
				Logger.IOSevereErrorHandler(e, logfile);
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
			Logger.IOSevereErrorHandler(e, logfile);
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
			Logger.IOSevereErrorHandler(e, logfile);
		}
		main(worldName,terrains);
	}
	
	public static void main(String worldName,List<Terrain> terrains) {
		try {
			Files.createDirectory(Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves\\"+worldName+"\\maps"));
			FileInputStream heightMap = new FileInputStream("res//textures//world//heightMap.png");
			FileInputStream worldMap = new FileInputStream("res//textures//world//worldMap.png");
			FileInputStream seed = new FileInputStream(System.getenv("APPDATA")+"\\Evolution\\flags and misc\\SeedInfoFlag.flg");
			Files.copy(heightMap,Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves\\"+worldName+"\\maps\\heightMap.png"),StandardCopyOption.REPLACE_EXISTING);
			Files.copy(worldMap,Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves\\"+worldName+"\\maps\\worldMap.png"),StandardCopyOption.REPLACE_EXISTING);
			Files.copy(seed,Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves\\"+worldName+"\\maps\\seed.flg"),StandardCopyOption.REPLACE_EXISTING);
			Files.createDirectory(Paths.get(System.getenv("APPDATA")+"\\Evolution\\saves\\"+worldName+"\\Entities"));
			
		} catch (IOException e) {
			Logger.IOSevereErrorHandler(e, logfile);
		}
	}

}
