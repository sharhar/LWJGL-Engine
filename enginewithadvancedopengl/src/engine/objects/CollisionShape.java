package engine.objects;

import engine.maths.Vector2f;

public abstract class CollisionShape {
	
	public String type;
	public Vector2f pos;
	public float r;
	public Vector2f[] other;
	public Vector2f[] otherPreCalc;
	public Vector2f[] colVert;
	public Vector2f colCalcPos = new Vector2f(Float.MAX_VALUE,Float.MAX_VALUE);
	public float colCalcR = Float.MAX_VALUE;
	public float scale = 0;
	
	public CollisionShape(String type, Vector2f pos, float r) {
		this.type = type;
		this.pos = pos;
		this.r = r;
	}
	
	public abstract void updateCol();
}
