package engine.objects;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.Renderable;
import engine.objects.shapes.Shape;

/**
 * This class is used to create sprites
 * @author Sharhar
 */
public class Sprite implements Renderable{
	
	public Shape shape;
	
	/**
	 * This constructor creates the sprite from a shape
	 * @param shape shape to be used
	 */
	public Sprite(Shape shape) {
		this.shape = shape;
	}
	
	/**
	 * This function moves the sprite
	 * @param move amount to move
	 */
	public void move(Vector2f move) {
		shape.pos.x += move.x;
		shape.pos.y += move.y;
	}
	
	/**
	 * This function is used to render the sprite
	 */
	public void render() {
		shape.render();
	}

	/**
	 * This function is used to render the sprite with the MasterRenderer
	 */
	public void masterRender() {
		shape.render();
	}
}
