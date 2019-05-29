package EngineTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.MasterRenderer;
import RenderEngine.OBJLoader;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GUIRenderer;
import guis.GUITexture;
import log.Logger;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import models.TexturedModel;
import models.rawModel;
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
		file = Logger.create(0);
		Logger.main("[INIT] Game started", 0, file);
		PyExecuter.main(null, "hashtest.pyw");
		BufferedReader LoggedIn;
		try {
			LoggedIn = new BufferedReader(new FileReader("src//python//obj//LoggedIn.flg"));
			String Line;
			Line = LoggedIn.readLine();
			if (Line.contains("true")) {
				Logger.main("[HEALTHY] Logged in", 0, file);
			}
			else {
				Logger.main("[SEVERE] Couldn't log in", -1, file);
				System.exit(0);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			StringWriter sw = new StringWriter();
			e1.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Logger.main(exceptionAsString,-1,file);
			System.exit(-1);
		}

		DisplayManager.createDisplay();
		Logger.main("[HEALTHY] Display created", 0, file);
		
		Loader loader = new Loader();
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("water"));
		
		TerrainTexturePack terrainTexturePack = new TerrainTexturePack(backgroundTexture,rTexture,gTexture,bTexture);
		Logger.main("[HEALTHY] Created textures", 0, file);
		
		System.out.println("Generating world map");
		PyExecuter.main(null, "Mapper.py");
		PyExecuter.main(null, "heightMap.py");
		Logger.main("[HEALTHY] Map Generation Done!", 0, file);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("world\\worldMap"));

		rawModel tree = OBJLoader.loadObjModel("Tree", loader);
		ModelTexture treeTexture = new ModelTexture(loader.loadTexture("plain"));
		treeTexture.setShineDamper(15);
		treeTexture.setReflectivity(10);
		TexturedModel texturedTree = new TexturedModel(tree,treeTexture);
		Entity TreeEntity = new Entity(texturedTree,new Vector3f(0,0,-30),0,0,0,1);
		
		rawModel grass = OBJLoader.loadObjModel("Grass_01", loader);
		ModelTexture grassTexture = new ModelTexture(loader.loadTexture("plain"));
		grassTexture.setShineDamper(15);
		grassTexture.setReflectivity(10);
		TexturedModel texturedGrass = new TexturedModel(grass,grassTexture);
		texturedGrass.getTexture().setHasTransparency(true);
		
		rawModel grass1 = OBJLoader.loadObjModel("grassModel", loader);
		ModelTexture grassTexture1 = new ModelTexture(loader.loadTexture("grassTexture"));
		grassTexture1.setShineDamper(15);
		grassTexture1.setReflectivity(10);
		TexturedModel texturedGrass1 = new TexturedModel(grass1,grassTexture1);
		texturedGrass1.getTexture().setHasTransparency(true);
		
		rawModel fern = OBJLoader.loadObjModel("fern", loader);
		ModelTexture fernTexture = new ModelTexture(loader.loadTexture("fern"));
		fernTexture.setNumberOfRows(4);
		fernTexture.setShineDamper(15);
		fernTexture.setReflectivity(10);
		TexturedModel texturedFern = new TexturedModel(fern,fernTexture);
		texturedFern.getTexture().setHasTransparency(true);
		
		Light light = new Light(new Vector3f(20000,20000,20000),new Vector3f(1,1,1));
		Logger.main("[HEALTHY] Created entities", 0, file);
		
		Terrain terrain = new Terrain(0,-1,loader,terrainTexturePack,blendMap,"heightMap.png");
		Terrain terrain2 = new Terrain(1,-1,loader,terrainTexturePack,blendMap,"heightMap.png");
		Terrain terrain3 = new Terrain(1,0,loader,terrainTexturePack,blendMap,"heightMap.png");
		Terrain terrain4 = new Terrain(0,0,loader,terrainTexturePack,blendMap,"heightMap.png");
		Terrain terrain5 = new Terrain(-1,0,loader,terrainTexturePack,blendMap,"heightMap.png");
		Terrain terrain6 = new Terrain(-1,-1,loader,terrainTexturePack,blendMap,"heightMap.png");
		List<Terrain> terrains = new ArrayList<Terrain>();
		terrains.add(terrain);
		terrains.add(terrain2);
		terrains.add(terrain3);
		terrains.add(terrain4);
		terrains.add(terrain5);
		terrains.add(terrain6);
		Logger.main("[HEALTHY] Created terrains", 0, file);
		
		List<Entity> allTrees = new ArrayList<Entity>();
		List<Entity> allGrasses = new ArrayList<Entity>();
		Random random = new Random();
		
		for(Terrain terrain1 : terrains) {
			for (int i = 0; i < 100; i++) {
				float x = random.nextInt((int) Terrain.getSize() ) + terrain1.getX();
				float z = random.nextInt((int) Terrain.getSize() ) + terrain1.getZ();
				float y = terrain1.getHeightOfTerrain(x, z);
				allTrees.add(new Entity(texturedTree, new Vector3f(x, y, z),0, random.nextFloat() * 360, 0, 1));
			}
		}
		for(Terrain terrain1 :terrains){
			for (int j =0; j < 200; j++) {
				float x = random.nextInt((int) Terrain.getSize() ) + terrain1.getX();
				float z = random.nextInt((int) Terrain.getSize() ) + terrain1.getZ();
				float y = terrain1.getHeightOfTerrain(x, z);
				allGrasses.add(new Entity(texturedGrass,new Vector3f(x,y,z),0,0,0,3));
			}
		}
		for (Terrain terrain1 :terrains) {
			for (int k=0; k<200; k++) {
			float x = random.nextInt((int) Terrain.getSize() ) + terrain1.getX();
			float z = random.nextInt((int) Terrain.getSize() ) + terrain1.getZ();
			float y = terrain1.getHeightOfTerrain(x, z);
				allGrasses.add(new Entity(texturedGrass1,new Vector3f(x,y,z),0,0,0,3));
			}
		}
		
		for (Terrain terrain1 :terrains) {
			for (int k=0; k<200; k++) {
			float x = random.nextInt((int) Terrain.getSize() ) + terrain1.getX();
			float z = random.nextInt((int) Terrain.getSize() ) + terrain1.getZ();
			float y = terrain1.getHeightOfTerrain(x, z);
				allGrasses.add(new Entity(texturedFern,random.nextInt(16),new Vector3f(x,y,z),0,0,0,3));
			}
		}
		
		
		List<Entity> allEntities = new ArrayList<Entity>();
		for (Entity entity : allTrees) {
			allEntities.add(entity);
		}
		for (Entity entity : allGrasses) {
			allEntities.add(entity);
		}
		Logger.main("[HEALTHY] Populated world", 0, file);
		
		MasterRenderer renderer = new MasterRenderer();
		
		
		System.out.println("Generating DNA...");
		PyExecuter.main(null, "test.py");
		System.out.println("Done!");
		Logger.main("[HEALTHY] DNA Generation done!", 0, file);
		
		rawModel HumanModel = OBJLoader.loadObjModel("Human1", loader);
		ModelTexture HumanTexture = new ModelTexture(loader.loadTexture("plain"));
		HumanTexture.setShineDamper(15);
		HumanTexture.setReflectivity(10);
		TexturedModel Human = new TexturedModel(HumanModel,HumanTexture);
		
		Player player = new Player(Human, new Vector3f(100,0,-50), 0, 0, 0, 1);
		allEntities.add(player);
		Camera camera = new Camera(player);
		Logger.main("[HEALTHY] Created player, loading world", 0, file);
		
		List<GUITexture> guis = new ArrayList<GUITexture>();
		GUITexture gui = new GUITexture(loader.loadTexture("Evolution"),new Vector2f(0f,0.8f),new Vector2f(0.25f,0.5f));
		guis.add(gui);
		GUIRenderer guiRenderer = new GUIRenderer(loader);
		
		Logger.main("[HEALTHY] World loaded!", -1, file);
		while(!Display.isCloseRequested()) {
			TreeEntity.increaseRotation(0, 1, 0);
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
		
		guiRenderer.cleanUP();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		Logger.main("[HEALTHY] Game ended correctly", -1, file);
		
	}

}
/*
 * Okay with the Buddha.
 */