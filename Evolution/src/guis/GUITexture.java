package guis;

import org.lwjgl.util.vector.Vector2f;

public class GUITexture {

		private int texture;
		private Vector2f position;
		private Vector2f size;
		
		public GUITexture(int texture, Vector2f position, Vector2f size) {
			this.texture = texture;
			this.position = position;
			this.size = size;
		}

		public int getTexture() {
			return texture;
		}

		public Vector2f getPosition() {
			return position;
		}

		public Vector2f getSize() {
			return size;
		}
	
}
