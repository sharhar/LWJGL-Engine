package engine.objects.shapes;

import org.lwjgl.util.vector.Vector2f;

public abstract class CollisionShape {
	public String shapeType = "";
	public Vector2f pos;
	public float r;
	public Vector2f[] other;
	public Vector2f[] otherPreCalc;
	public Vector2f[] colVert;
	public Vector2f colCalcPos = new Vector2f(Float.MAX_VALUE,Float.MAX_VALUE);
	public float colCalcR = Float.MAX_VALUE;
	public float scale = 0;
	
	public Vector2f boundingBox = new Vector2f();
	
	public CollisionShape(String shapeType, Vector2f pos, float r) {
		this.shapeType = shapeType;
		this.pos = pos;
		this.r = r;
	}
	
	public CollisionShape(CollisionShape other) {
		this.shapeType = other.shapeType;
		this.pos = new Vector2f(other.pos);
		this.r = other.r;
		this.colCalcPos = new Vector2f(colCalcPos);
		this.colCalcR = other.colCalcR;
		this.scale = other.scale;
		
		this.other = new Vector2f[other.other.length];
		for(int i = 0;i < this.other.length;i++) {
			this.other[i] = new Vector2f(other.other[i]);
		}
		
		this.otherPreCalc = new Vector2f[other.otherPreCalc.length];
		for(int i = 0;i < this.otherPreCalc.length;i++) {
			this.otherPreCalc[i] = new Vector2f(other.otherPreCalc[i]);
		}
		
		this.colVert = new Vector2f[other.colVert.length];
		for(int i = 0;i < this.colVert.length;i++) {
			if(other.colVert[i] == null) {
				this.colVert[i] = null;
			} else {
				this.colVert[i] = new Vector2f(other.colVert[i]);
			}
		}
	}
	
	public void updateCollisionStuff() {
		updateCol();
		
		float xmax = Float.MIN_VALUE;
		float ymax = Float.MIN_VALUE;
		float xmin = Float.MAX_VALUE;
		float ymin = Float.MAX_VALUE;
		
		for(Vector2f p:colVert) {
			if(p.x > xmax) {
				xmax = p.x;
			}
			
			if(p.x < xmin) {
				xmin = p.x;
			}
			
			if(p.y > ymax) {
				ymax = p.y;
			}
			
			if(p.y < ymin) {
				ymin = p.y;
			}
		}
		
		boundingBox = new Vector2f(xmax - xmin, ymax - ymin);
	}
	
	public abstract void updateCol();
}
