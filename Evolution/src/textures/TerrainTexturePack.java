package textures;

public class TerrainTexturePack {
	
	private TerrainTexture BackgroundTexture;
	private TerrainTexture RTexture;
	private TerrainTexture GTexture;
	private TerrainTexture BTexture;
	
	public TerrainTexturePack(TerrainTexture backgroundTexture, TerrainTexture rTexture, TerrainTexture gTexture, TerrainTexture bTexture) {
		BackgroundTexture = backgroundTexture;
		RTexture = rTexture;
		GTexture = gTexture;
		BTexture = bTexture;
	}

	public TerrainTexture getBackgroundTexture() {
		return BackgroundTexture;
	}

	public TerrainTexture getRTexture() {
		return RTexture;
	}

	public TerrainTexture getGTexture() {
		return GTexture;
	}

	public TerrainTexture getBTexture() {
		return BTexture;
	}
	
	

}
