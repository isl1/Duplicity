package models;

public class Model1 {

	public float[] Model1Vertices() {
		float[] vertices = { -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,

				-0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,

				0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,

				-0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,

				-0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,

				-0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f
		};
		return vertices;
	}

	public float[] Model1TextureCoords() {
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1,
				1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0 };
		return textureCoords;
	}

	public int[] Model1Indices() {
		int[] indices = { 0, 1, 3, 3, 1, 2, 4, 5, 7, 7, 5, 6, 8, 9, 11, 11, 9, 10, 12, 13, 15, 15, 13, 14, 16, 17, 19,
				19, 17, 18, 20, 21, 23, 23, 21, 22 };
		return indices;
	}

	/*
	 * public float[] Model1Vertices() { float[] vertices = { -0.5f, 0.5f, 0,
	 * //v0 -0.5f, -0.5f, 0, //v1 0.5f, -0.5f, 0, //v2 0.5f, 0.5f, 0, //v3 };
	 * return vertices; }
	 * 
	 * public int[] Model1Indices(){ int[] indices = { 0,1,3, 3,1,2}; return
	 * indices; }
	 * 
	 * public float[] Model1TextureCoords(){ float[] textureCoords = { 1, 0,
	 * //v1 1, 1, //v2 0, 1, //v3 0, 0, //v0
	 * 
	 * }; return textureCoords; }
	 */

	public String Model1Name() {
		String modelName = "Cube";
		return modelName;
	}

	public int Model1PolyCount() {
		int getPolyCount = 2;
		return getPolyCount;
	}
}
