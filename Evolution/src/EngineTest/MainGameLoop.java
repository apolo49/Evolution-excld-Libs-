package EngineTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.MasterRenderer;
import guis.GUIRenderer;
import guis.GUITexture;
import fileHandling.log.Logger;
import fileHandling.saves.Saves;
import python.PyExecuter;

//
//					  _oo0oo_
//					 o8888888o
//					 88" . "88
//					 (| -_- |)
//					 0\  =  /0
//				   ___/`---'\___
//          	  .'\\|     |// '.
//			     /\\|||  :  |||// \
//			    /_||||| -:- |||||- \
//			   |  | \\\  -  /// |   |
//			   |\_|  ''\---/''  |_/ |
//			   \ .-\__  '-'  ___/-. /
//			___'. .'  /--.--\  `. .'___
//		 ."" '<  `.___\_<|>_/___.' >' "".
//		| | :  `- \`.;`\ _ /`;.`/ - ` : | |
//		\  \ `_.   \_ __\ /__ _/   .-` /  /
//	=====`-.____`.___ \_____/___.-`___.-'=====
//					  `=---='
//
//
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//	BUDDHA BLESS YOUR CODE TO BE BUG FREE
//

public class MainGameLoop {

	public static void main(String[] args) {
		File file = null;
		Saves.CreateDirectory();
		file = Logger.create(0);
		Logger.main("[INIT] Game started", 0, file);
		PyExecuter.main(null, "hashtest.pyw");
		BufferedReader LoggedIn;
		try {
			LoggedIn = new BufferedReader(new FileReader("src//python//obj//LoggedIn.flg"));
			String Line;
			Line = LoggedIn.readLine();
			if (Line.contains("true")) {
				Logger.main("[HEALTHY] Logged in", -1, file);
			}
			else {
				Logger.main("[SEVERE] Couldn't log in", -1, file);
				System.exit(0);
			}
		} catch (IOException e1) {
			Logger.IOSevereErrorHandler(e1, file);
		}
		
		PyExecuter.main(null, "Menu.py");
		Logger.main("[HEALTHY] Menu Opened", 0, file);
		BufferedReader World;
		BufferedReader Continue;
		BufferedReader Seed;
		BufferedReader NewWorld;
		try {
			Continue = new BufferedReader(new FileReader(System.getenv("APPDATA")+"\\Evolution\\flags and misc\\QuitFlag.flg"));
			String End;
			End = Continue.readLine();
			if(!End.contains("0")) {
				Logger.main("[UNKNOWN] Error or Player chose to end the game", -1, file);
				System.exit(-1);
			}
			
			World = new BufferedReader(new FileReader(System.getenv("APPDATA")+"\\Evolution\\flags and misc\\chosenWorldFlag.flg"));
			String CurrentWorld;
			CurrentWorld = World.readLine();

			Seed = new BufferedReader(new FileReader(System.getenv("APPDATA")+"\\Evolution\\flags and misc\\SeedInfoFlag.flg"));
			String CurrentSeed;
			CurrentSeed = Seed.readLine();
			Seed.close();
			Random random = new Random();
			BigInteger seed = new BigInteger(Integer.MAX_VALUE , random); //return to fix
			if (CurrentSeed == "" | CurrentSeed.contains("null")) {
				seed = new BigInteger(seed.pow(16).bitLength(), random);
			}else {
				try {
					seed = new BigInteger(CurrentSeed);
				}catch (NumberFormatException e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					Logger.main(exceptionAsString,-1,file);
					System.exit(-1);
				}
			}
			
			NewWorld = new BufferedReader(new FileReader(System.getenv("APPDATA")+"\\Evolution\\flags and misc\\NewWorldFlag.flg"));
			String NewWorldFlag;
			NewWorldFlag = NewWorld.readLine();
			
			DisplayManager.createDisplay();
			Logger.main("[HEALTHY] Display created", -1, file);
			
			Loader loader = new Loader();
			
			MasterRenderer renderer = new MasterRenderer();
			List<GUITexture> guis = new ArrayList<GUITexture>();
			GUITexture gui = new GUITexture(loader.loadTexture("Evolution"),new Vector2f(0f,0.8f),new Vector2f(0.25f,0.5f));
			guis.add(gui);
			GUIRenderer guiRenderer = new GUIRenderer(loader);
			
			try {
				if(!NewWorldFlag.contains("1")) {
			 		CreateWorld.loadWorld(CurrentWorld,seed);
		 		}else if(NewWorldFlag.contains("1")) {
		 			CreateWorld.createNewWorld(CurrentWorld,seed);
		 		}else {
		 			Logger.main("[FATAL] Not 1 or 0 in NewWorldFlag \n[FATAL] Shutting down.", -1, file);
		 			System.exit(-1);
		 		}
			}catch (NullPointerException e) {
				Logger.NullPointerSevereErrorHandler(e, file);
			}
			
			guiRenderer.cleanUP();
			Logger.main("[HEALTHY] Cleaned up GUI", 0, file);
			renderer.cleanUp();
			Logger.main("[HEALTHY] Cleaned up Renderer", 0, file);
			loader.cleanUp();
			Logger.main("[HEALTHY] Cleaned up Loader", 0, file);
			DisplayManager.closeDisplay();
			Logger.main("[HEALTHY] Game ended correctly", -1, file);
			
		} catch (IOException e1) {
			Logger.IOSevereErrorHandler(e1, file);
		}
		
		
	}

}
/*
 * Okay with the Buddha.
 */