package engine.objects.shapes;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.BasicRenderer;
import engine.graphics.Color;

public class ShapeCircle extends Shape{

	public ShapeCircle(Vector2f pos, float r ,int vertCount, Color color) {
		super("Cir", pos, r, color);
		
		other = new Vector2f[vertCount];
		
		for(int i = 0;i < vertCount;i++) {
			float theta = (float) Math.toRadians((360.0f/vertCount) * i);
			float X = (float) (Math.cos(theta));
			float Y = (float) (Math.sin(theta));
			other[i] = new Vector2f(X,Y);
		}
		
		colVert = new Vector2f[vertCount];
		
		updateCol();
	}
	
	
	
	public void render() {
		BasicRenderer.renderCircle(pos.x, pos.y, r, other, color);
	}

	public void masterRender() {
		
	}
	
	public void updateCol() {
		if(colCalcPos.x == pos.x && colCalcPos.y == pos.y) {
			return;
		}
		
		for(int i = 0;i < other.length;i++) {
			colVert[i] = new Vector2f(other[i].x*r + pos.x, other[i].y*r + pos.y);
		}
		
		colCalcPos = new Vector2f(pos.x, pos.y);
	}

	public float getCalcR() {
		return r;
	}
}
