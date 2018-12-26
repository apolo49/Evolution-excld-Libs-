package models;

import textures.ModelTexture;

public class TexturedModel {
	
	private rawModel RawModel;
	private ModelTexture texture;
	
	public TexturedModel(rawModel model, ModelTexture texture) {
		this.RawModel = model;
		this.texture = texture;
		
	}

	public rawModel getRawModel() {
		return RawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}

}
