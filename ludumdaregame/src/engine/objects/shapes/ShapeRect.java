package engine.objects.shapes;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.BasicRenderer;
import engine.graphics.Color;
import engine.math.Maths;

public class ShapeRect extends Shape{
	
	public ShapeRect(Vector2f pos, Vector2f size, Color color) {
		super("Rect", pos, 0, color);
		other = new Vector2f[1];
		other[0] = size;
		
		colVert = new Vector2f[4];
		
		updateCol();
	}
	
	public void render() {
		BasicRenderer.renderRect(pos.x, pos.y, other[0].x, other[0].y, r, 0, color);
	}

	public void masterRender() {
		
	}
	
	public void updateCol() {
		if(colCalcPos.x == pos.x && colCalcPos.y == pos.y) {
			return;
		}
		
		Vector2f center = new Vector2f(pos.x + other[0].x/2, pos.y + other[0].y/2);
		
		colVert[0] = Maths.rotVec(new Vector2f(pos.x, pos.y), center, r);
		colVert[1] = Maths.rotVec(new Vector2f(pos.x + other[0].x, pos.y), center, r);
		colVert[2] = Maths.rotVec(new Vector2f(pos.x + other[0].x, pos.y + other[0].y), center, r);
		colVert[3] = Maths.rotVec(new Vector2f(pos.x, pos.y + other[0].y), center, r);
		
		colCalcPos = new Vector2f(pos.x, pos.y);
	}
}
