package engine.objects.shapes;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.BasicRenderer;
import engine.graphics.Color;
import engine.math.Maths;

/**
 * This class creates a rectangle using the shape class
 * @author Sharhar
 *
 */
public class ShapeRect extends Shape{
	
	/**
	 * This constructor
	 * @param pos position
	 * @param size size
	 * @param color color
	 */
	public ShapeRect(Vector2f pos, Vector2f size, Color color) {
		super("Rect", pos, 0, color);
		other = new Vector2f[1];
		other[0] = size;
		
		colVert = new Vector2f[4];
		
		updateCol();
	}
	
	/**
	 * This function renders the rectangle
	 */
	public void render() {
		BasicRenderer.renderRect(pos.x, pos.y, other[0].x, other[0].y, r, 0, color);
	}

	/**
	 * This function renders the rectangle using the MasterRenderer
	 */
	public void masterRender() {
		
	}
	
	/**
	 * This function updates the collision information
	 */
	public void updateCol() {
		if(colCalcPos.x == pos.x && colCalcPos.y == pos.y && r == colCalcR) {
			return;
		}
		
		Vector2f center = new Vector2f(pos.x + other[0].x/2, pos.y + other[0].y/2);
		
		colVert[0] = Maths.rotVec(new Vector2f(pos.x, pos.y), center, r);
		colVert[1] = Maths.rotVec(new Vector2f(pos.x + other[0].x, pos.y), center, r);
		colVert[2] = Maths.rotVec(new Vector2f(pos.x + other[0].x, pos.y + other[0].y), center, r);
		colVert[3] = Maths.rotVec(new Vector2f(pos.x, pos.y + other[0].y), center, r);
		
		colCalcPos = new Vector2f(pos.x, pos.y);
		colCalcR = r;
	}

	/**
	 * This function returns the rotation of the rectangle
	 */
	public float getCalcR() {
		float R = r;
		
		if(R < 0)
			R = 360+R;
		
		return R%360;
	}
}
