package engine.objects.shapes;

import org.lwjgl.util.vector.Vector2f;

import engine.math.Maths;

public class CollisionShapePoly extends CollisionShape{

	public CollisionShapePoly(Vector2f pos, float r, Vector2f[] other) {
		super("Poly", pos, r);
		this.other = other;
		this.otherPreCalc = other;
		
		this.colVert = new Vector2f[other.length];
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
	
	public void updateCol() {
		if(colCalcPos.x != pos.x || colCalcPos.y != pos.y) {
			for(int i = 0;i < colVert.length;i++) {
				colVert[i] = new Vector2f(other[i].x + pos.x, other[i].y + pos.y);
			}
		}
		
		if(colCalcR == r) {
			for(int i = 0;i < colVert.length;i++) {
				colVert[i] = Maths.rotVec(colVert[i], pos, r);
			}
		}
		
		
		colCalcR = r;
		colCalcPos.x = pos.x;
		colCalcPos.y = pos.y;
	}
}
