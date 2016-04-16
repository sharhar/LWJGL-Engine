package engine.objects;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.Renderable;
import engine.math.physics.Collider;
import engine.objects.shapes.CollisionShape;

/**
 * This class is used to create sprites
 * @author Sharhar
 */
public class Sprite implements Renderable{
	
	public static List<Sprite> sprites = new ArrayList<Sprite>();
	protected static boolean[] IDs;
	
	public Vector2f pos;
	public SpriteData shapes;
	public int ID;
	public boolean dead = false;
	public boolean grounded = false;
	
	public static void array(int amount) {
		IDs = new boolean[amount];
	}
	
	/**
	 * This constructor creates the sprite from a shape
	 * @param shape shape to be used
	 */
	public Sprite(SpriteData shapes, Vector2f pos) {
		this.shapes = shapes;
		sprites.add(this);
		ID = getID();
		setPos(pos);
	}
	
	public void setPos(Vector2f pos) {
		shapes.render.pos.x = pos.x;
		shapes.render.pos.y = pos.y;
		
		for(int i = 0; i < shapes.col.length;i++) {
			shapes.col[i].pos.x = pos.x;
			shapes.col[i].pos.y = pos.y;
		}
		
		this.pos = pos;
	}
	
	public void setSpriteData(SpriteData newData) {
		newData.render.pos = pos;
		for(CollisionShape c:newData.col) {
			c.pos = pos;
		}
		newData.render.r = shapes.render.r;
		shapes = newData;
	}
	
	/**
	 * This function moves the sprite
	 * @param move amount to move
	 */
	public void move(Vector2f move) {
		pos.x += move.x;
		pos.y += move.y;
		
		shapes.render.pos.x += move.x;
		shapes.render.pos.y += move.y;
		
		for(int i = 0; i < shapes.col.length;i++) {
			shapes.col[i].pos.x += move.x;
			shapes.col[i].pos.y += move.y;
		}
	}
	
	/**
	 * This function is used to render the sprite
	 */
	public void render() {
		shapes.render.render();
	}

	/**
	 * This function is used to render the sprite with the MasterRenderer
	 */
	public void masterRender() {
		shapes.render.render();
	}
	
	/**
	 * This function moves the sprite, but it also takes into account the other sprites in the scene
	 */
	public void moveCol(Vector2f move) {
		
		pos.x += move.x;
		shapes.render.pos.x += move.x;
		for(int i = 0; i < shapes.col.length;i++) {
			shapes.col[i].pos.x += move.x;
		}
		if(anyCollision()) {
			pos.x -= move.x;
			shapes.render.pos.x -= move.x;
			for(int i = 0; i < shapes.col.length;i++) {
				shapes.col[i].pos.x -= move.x;
			}
		}
		
		pos.y += move.y;
		shapes.render.pos.y += move.y;
		for(int i = 0; i < shapes.col.length;i++) {
			shapes.col[i].pos.y += move.y;
		}
		
		if(anyCollision()) {
			pos.y -= move.y;
			shapes.render.pos.y -= move.y;
			for(int i = 0; i < shapes.col.length;i++) {
				shapes.col[i].pos.y -= move.y;
			}
		}
	}
	
	private boolean anyCollision() {
		for(Sprite s:sprites) {
			if(ID != s.ID) {
				if(Collider.spriteSpriteCol(this, s)) {
					return true;
				}
			}
		}
		
		return false;
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
