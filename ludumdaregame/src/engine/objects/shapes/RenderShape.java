package engine.objects.shapes;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.Color;
import engine.graphics.Renderable;

/**
 * This class is used to implement shapes
 * @author Sharhar
 *
 */
abstract public class RenderShape implements Renderable{
	public String shapeType = "";
	public Vector2f pos;
	public float r;
	public Color color;
	public int ID;
	public Vector2f[] other;
	public Vector2f[] otherPreCalc;
	
	/**
	 * This constructor initializes the basic shape
	 * @param shapeType the type of shape
	 * @param pos position of shape
	 * @param r rotation (or radius if circle)
	 * @param color color of shape
	 */
	
	public RenderShape(RenderShape other) {
		this.shapeType = other.shapeType;
		this.pos = new Vector2f(other.pos);
		this.r = other.r;
		this.color = new Color(other.color);
		this.ID = other.ID;
		
		this.other = new Vector2f[other.other.length];
		for(int i = 0;i < this.other.length;i++) {
			this.other[i] = new Vector2f(other.other[i]);
		}
		if(other.otherPreCalc == null) {
			this.otherPreCalc = null;
		} else {
			this.otherPreCalc = new Vector2f[other.otherPreCalc.length];
			for(int i = 0;i < this.otherPreCalc.length;i++) {
				this.otherPreCalc[i] = new Vector2f(other.otherPreCalc[i]);
			}
		}
	}
	
	public RenderShape(String shapeType, Vector2f pos, float r, Color color) {
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
}
