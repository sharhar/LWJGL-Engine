package engine.objects;

import engine.utils.maths.Maths;
import engine.utils.maths.Vector2f;

public class CollisionShapeRect extends CollisionShape{

	public CollisionShapeRect(Vector2f pos, Vector2f size) {
		super("Rect", new Vector2f(pos.x - size.x/2, pos.y - size.y/2), 0);
		other = new Vector2f[1];
		other[0] = size;
		colVert = new Vector2f[4];
		
		updateCol();
	}

	public void updateCol() {
		if(colCalcPos.x == pos.x && colCalcPos.y == pos.y && r == colCalcR) {
			return;
		}
		
		Vector2f center = new Vector2f(pos.x + other[0].x/2, pos.y + other[0].y/2);
		
		colVert[0] = Maths.rotVec(new Vector2f(pos.x, pos.y), center, r);
		colVert[1] = Maths.rotVec(new Vector2f(pos.x + other[0].x, pos.y), center, r);
		colVert[2] = Maths.rotVec(new Vector2f(pos.x + other[0].x, pos.y + other[0].y), center, r);
		colVert[3] = Maths.rotVec(new Vector2f(pos.x, pos.y + other[0].y), center, r);
		
		colCalcPos = new Vector2f(pos.x, pos.y);
		colCalcR = r;
	}

}
