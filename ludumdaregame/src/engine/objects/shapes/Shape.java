package engine.objects.shapes;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.Color;
import engine.graphics.Renderable;

abstract public class Shape implements Renderable{
	public String type = "";
	public Vector2f pos;
	public float r;
	public Color color;
	public int ID;
	public Vector2f[] other;
	public Vector2f[] colVert;
	public Vector2f colCalcPos = new Vector2f(-968347.663542f,6583567.523540978f);
	
	public Shape(String type, Vector2f pos, float r, Color color) {
		this.type = type;
		this.pos = pos;
		this.r = r;
		this.color = color;
		ID = 0;
	}
	
	abstract public void updateCol();
}
