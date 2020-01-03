package EngineTest;

import java.io.File;
import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import RenderEngine.DisplayManager;
import RenderEngine.MasterRenderer;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GUIRenderer;
import guis.GUITexture;
import fileHandling.log.Logger;
import terrains.Terrain;

public class Game {
	public static void main(MasterRenderer renderer, Camera camera, List<Terrain> terrains,Player player,List<Entity> allEntities,Light light,GUIRenderer guiRenderer,List<GUITexture> guis ) {
		boolean gamePaused = false;
		File file = new File(System.getenv("APPDATA")+"\\Evolution\\logs\\Latest.txt");
		
		while(!Display.isCloseRequested()) {
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !gamePaused)
	            gamePaused = !gamePaused;
	        if(gamePaused) {
	        	Logger.main("[HEALTHY] Game Paused", 0, file);
	        	String Quit = Pause.main();
	            if (Quit.contains("0")) {
	            	gamePaused = false;
	            }else if (Quit.contains("1")) {
	            	break;
	            }else{
	            	Logger.main("[SEVERE] Has not landed from pause menu correctly!", -1, file);
	            	Logger.main("[SEVERE] Expected 1 or 0 got "+Quit+" Instead", -1, file);
	            	Logger.main("[SEVERE] System Closing", -1, file);
	            	System.exit(-1);
	            }
	        }
			camera.move();
			for(Terrain terrain1 : terrains) {
				if(terrain1.getX() <= player.getPosition().x) { 
					if(terrain1.getX() + Terrain.getSize() > player.getPosition().x) {
						if(terrain1.getZ() <= player.getPosition().z) {
							if(terrain1.getZ() + Terrain.getSize() > player.getPosition().z) {
								player.move(terrain1);
							}
						}
					}
				}
			}
			//game logic
			for(Terrain terrain1 : terrains) {
				renderer.processTerrain(terrain1);
			}
			for (Entity entity : allEntities) {
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			guiRenderer.render(guis);
			DisplayManager.updateDisplay();
			
		}
	}
}
