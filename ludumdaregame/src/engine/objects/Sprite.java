package engine.objects;

import engine.graphics.BasicRenderer;
import engine.graphics.Renderable;

public class Sprite implements Renderable{
	
	public float x, y, width, height, rot;
	
	public Sprite() {
		
	}
	
	public void render() {
		BasicRenderer.renderRect(x, y, width, height, rot);
	}
}
