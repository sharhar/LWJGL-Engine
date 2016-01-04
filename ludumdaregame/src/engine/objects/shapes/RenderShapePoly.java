package engine.objects.shapes;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.BasicRenderer;
import engine.graphics.Color;

/**
 * This class creates a rectangle using the shape class
 * @author Sharhar
 *
 */
public class RenderShapePoly extends RenderShape{
	
	/**
	 * This constructor
	 * @param pos position
	 * @param size size
	 * @param color color
	 */
	public RenderShapePoly(Vector2f pos, Vector2f[] other, Color color) {
		super("Rect", pos, 0, color);
		other = new Vector2f[1];
		this.other = other;
		this.otherPreCalc = other;
	}
	
	public void setScaleX(float scale) {
		for(int i = 0;i < other.length;i++) {
			other[i].x = otherPreCalc[i].x*scale;
		}
	}
	
	public void setScaleY(float scale) {
		for(int i = 0;i < other.length;i++) {
			other[i].y = otherPreCalc[i].y*scale;
		}
	}
	
	/**
	 * This function renders the rectangle
	 */
	public void render() {
		BasicRenderer.renderPoly(pos.x, pos.y, r, other, color);
		System.out.println("polyRender");
	}

	/**
	 * This function renders the rectangle using the MasterRenderer
	 */
	public void masterRender() {
		
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
