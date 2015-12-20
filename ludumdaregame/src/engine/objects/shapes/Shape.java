package engine.objects.shapes;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.Color;
import engine.graphics.Renderable;

/**
 * This class is used to implement shapes
 * @author Sharhar
 *
 */
abstract public class Shape implements Renderable{
	public String shapeType = "";
	public Vector2f pos;
	public float r;
	public Color color;
	public int ID;
	public Vector2f[] other;
	public Vector2f[] colVert;
	public Vector2f colCalcPos = new Vector2f(Float.MAX_VALUE,Float.MAX_VALUE);
	public float colCalcR = Float.MAX_VALUE;
	
	/**
	 * This constructor initializes the basic shape
	 * @param shapeType the type of shape
	 * @param pos position of shape
	 * @param r rotation (or radius if circle)
	 * @param color color of shape
	 */
	public Shape(String shapeType, Vector2f pos, float r, Color color) {
		this.shapeType = shapeType;
		this.pos = pos;
		this.r = r;
		this.color = color;
		ID = 0;
	}
	
	/**
	 * This function returns the R value for this shape type
	 * @return r value
	 */
	abstract public float getCalcR();
	
	/**
	 * This function updates the collision vertices
	 */
	abstract public void updateCol();
}
