package engine.graphics.models;

import engine.utils.Loader;

public class RectangleModel extends RawModel{
	
	public RectangleModel(RawModel model) {
		super(model.getVaoID(), model.getVertexCount());
	}

	static float[] vertices = { 
			-0.5f,  0.5f,
			-0.5f, -0.5f, 
			 0.5f, -0.5f,
			 0.5f,  0.5f
			};
	
	static int[] index = {
			0 , 1, 3,
			3 , 1, 2
	};
	
	static float[] texCoords = {
			0,0,
			0,1,
			1,1,
			1,0
	};
	
	public static RectangleModel rectangle =  new RectangleModel(Loader.loadToVAO(vertices, texCoords, index));
	
}
