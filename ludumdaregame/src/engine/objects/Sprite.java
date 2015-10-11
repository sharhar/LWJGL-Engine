package engine.objects;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.Renderable;
import engine.objects.shapes.Shape;

public class Sprite implements Renderable{
	
	public Shape shape;
	
	public Sprite(Shape shape) {
		this.shape = shape;
	}
	
	public void move(Vector2f move) {
		shape.pos.x += move.x;
		shape.pos.y += move.y;
	}
	
	public void render() {
		shape.render();
	}

	public void masterRender() {
		shape.render();
	}
}
