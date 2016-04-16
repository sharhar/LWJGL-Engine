package engine.objects;

import engine.objects.shapes.CollisionShape;
import engine.objects.shapes.CollisionShapePoly;
import engine.objects.shapes.RenderShape;
import engine.objects.shapes.RenderShapeRect;

public class SpriteData {
	
	public RenderShape render;
	public CollisionShape[] col;
	
	public SpriteData(RenderShape render, CollisionShape[] col) {
		this.render = render;
		this.col = col;
	}

	public SpriteData(SpriteData other) {
		this.render = new RenderShapeRect(other.render);
		
		this.col = new CollisionShape[other.col.length];
		for(int i = 0;i < this.col.length;i++) {
			this.col[i] = new CollisionShapePoly(other.col[i]);
		}
	}
	
}
