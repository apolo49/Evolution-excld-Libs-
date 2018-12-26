package models;

public class rawModel {

	private int vaoID;
	private int vertexCount;
	
	public rawModel(int VaoID, int vertexCount) {
		this.vaoID = VaoID;
		this.vertexCount = vertexCount;			
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
	
	
}
