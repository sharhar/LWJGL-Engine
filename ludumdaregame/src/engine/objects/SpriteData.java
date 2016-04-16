package engine.objects;

import engine.objects.shapes.CollisionShape;
import engine.objects.shapes.RenderShape;

public class SpriteData {
	
	public RenderShape render;
	public CollisionShape[] col;
	
	public SpriteData(RenderShape render, CollisionShape[] col) {
		this.render = render;
		this.col = col;
	}
	
}
