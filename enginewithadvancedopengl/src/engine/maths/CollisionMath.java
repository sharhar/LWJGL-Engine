package engine.maths;

import engine.objects.CollisionShape;
import engine.objects.Entity;

public class CollisionMath {
	
	public static boolean entityCol(Entity e1, Entity e2) {
		return shapeShapeCol(e1.shape, e2.shape);
	}
	
	public static boolean shapeShapeCol(CollisionShape s1, CollisionShape s2) {
		s1.updateCol();
		s2.updateCol();
		if(s1.type.equals("Rect") && s2.type.equals("Rect") && s1.r == 0 && s2.r == 0) {
			return rectRectCol(s1, s2);
		} else if(s1.type.equals("Cir") && s2.type.equals("Cir")) {
			return cirCirCol(s1, s2);
		} else if(s1.type.equals("Cir") && s2.type.equals("Rec") && s2.r == 0) {
			return rectCirCol(s2, s1);
		} else if(s1.type.equals("Rec") && s2.type.equals("Cir") && s1.r == 0) {
			return rectCirCol(s1, s2);
		}
		
		return polygonCol(s1, s2);
	}
	
	public static boolean rectCirCol(CollisionShape r, CollisionShape c) {
		Vector2f close = Maths.getPointClosestToInRect(r, c.pos);
		
		float d = Maths.distance(close, c.pos);
		
		return d <= c.r;
	}
	
	public static boolean cirCirCol(CollisionShape s1, CollisionShape s2) {
		float d = Maths.distance(s1.pos, s2.pos);
		float r = s1.r + s2.r;
		
		return d <= r;
	}
	
	public static boolean rectRectCol(CollisionShape sh1, CollisionShape sh2) {
		Vector2f p1 = sh1.pos;
		Vector2f p2 = sh2.pos;
		Vector2f s1 = sh1.other[0];
		Vector2f s2 = sh2.other[0];
		
		if(p1.x + s1.x< p2.x || p2.x + s2.x < p1.x) {
			return false;
		}
		
		if(p1.y + s1.y < p2.y || p2.y + s2.y < p1.y) {
			return false;
		}
		
		return true;
	}
	
	public static boolean polygonCol(CollisionShape s1, CollisionShape s2) {
		s1.updateCol();
		s2.updateCol();

		CollisionShape[] shapes = new CollisionShape[2];
		shapes[0] = s1;
		shapes[1] = s2;

		for (CollisionShape s : shapes) {
			for (int i1 = 0; i1 < s.colVert.length; i1++) {
				int i2 = (i1 + 1) % s.colVert.length;
				Vector2f p1 = s.colVert[i1];
				Vector2f p2 = s.colVert[i2];

				Vector2f normal = new Vector2f(p2.y - p1.y, p1.x - p2.x);

				float minA = Float.MAX_VALUE;
				float maxA = Float.MIN_VALUE;

				for (Vector2f v : s1.colVert) {
					float projected = normal.x * v.x + normal.y * v.y;
					if (projected < minA)
						minA = projected;
					if (projected > maxA)
						maxA = projected;
				}

				float minB = Float.MAX_VALUE;
				float maxB = Float.MIN_VALUE;

				for (Vector2f v : s2.colVert) {
					float projected = normal.x * v.x + normal.y * v.y;
					if (projected < minB)
						minB = projected;
					if (projected > maxB)
						maxB = projected;
				}

				if (maxA < minB || maxB < minA) {
					return false;
				}
			}
		}

		return true;
	}
}
