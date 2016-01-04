package engine.objects;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.Renderable;
import engine.objects.shapes.CollisionShape;
import engine.objects.shapes.RenderShape;

/**
 * This class is used to create sprites
 * @author Sharhar
 */
public class Sprite implements Renderable{
	
	public static List<Sprite> sprites = new ArrayList<Sprite>();
	protected static boolean[] IDs;
	
	public RenderShape renderShape;
	public CollisionShape[] col;
	public int ID;
	public boolean dead = false;
	
	public static void array(int amount) {
		IDs = new boolean[amount];
	}
	
	/**
	 * This constructor creates the sprite from a shape
	 * @param shape shape to be used
	 */
	public Sprite(RenderShape shape, CollisionShape[] col) {
		this.renderShape = shape;
		this.col = col;
		sprites.add(this);
		ID = getID();
	}
	
	public void setPos(Vector2f pos) {
		renderShape.pos.x = pos.x;
		renderShape.pos.y = pos.y;
		
		for(int i = 0; i < col.length;i++) {
			col[i].pos.x = pos.x;
			col[i].pos.y = pos.y;
		}
	}
	
	/**
	 * This function moves the sprite
	 * @param move amount to move
	 */
	public void move(Vector2f move) {
		renderShape.pos.x += move.x;
		renderShape.pos.y += move.y;
		
		for(int i = 0; i < col.length;i++) {
			col[i].pos.x += move.x;
			col[i].pos.y += move.y;
		}
	}
	
	/**
	 * This function is used to render the sprite
	 */
	public void render() {
		renderShape.render();
	}

	/**
	 * This function is used to render the sprite with the MasterRenderer
	 */
	public void masterRender() {
		renderShape.render();
	}
	
	/**
	 * This function moves the sprite, but it also takes into account the other sprites in the scene
	 */
	public void moveCol() {
		
	}
	
	/**
	 * This function destroys the sprite 
	 */
	public void destroy() {
		sprites.remove(this);
		IDs[ID] = false;
		dead = true;
	}
	
	private static int getID() {
		int result = 0;
		
		for(int i = 0;i < IDs.length;i++) {
			if(!IDs[i]) {
				result = i;
				IDs[result] = true;
				break;
			}
		}
		
		return result;
	}
}
