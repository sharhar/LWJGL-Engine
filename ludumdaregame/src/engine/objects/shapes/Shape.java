package engine.objects.shapes;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.Color;
import engine.graphics.Renderable;

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
	
	public Shape(String shapeType, Vector2f pos, float r, Color color) {
		this.shapeType = shapeType;
		this.pos = pos;
		this.r = r;
		this.color = color;
		ID = 0;
	}
	abstract public float getCalcR();
	
	abstract public void updateCol();
}
