package engine.math.physics;

import org.lwjgl.util.vector.Vector2f;

import engine.math.Maths;
import engine.objects.shapes.Shape;

/**
 * This class handles collision calculations
 * @author Sharhar
 *
 */
public class Colider {

	/**
	 * This is a general function to check if any to shapes are colliding
	 * @param s1 shape 1
	 * @param s2 shape 2
	 * @return whether the shapes are colliding
	 */
	public static boolean shapeShapeCol(Shape s1, Shape s2) {
		if(s1.shapeType.equals("Rect") && s2.shapeType.equals("Rect") && s1.r == 0 && s2.r == 0) {
			return rectRectCol(s1, s2);
		} else if(s1.shapeType.equals("Cir") && s2.shapeType.equals("Cir")) {
			return cirCirCol(s1, s2);
		} else if(s1.shapeType.equals("Cir") && s2.shapeType.equals("Rec") && s2.r == 0) {
			return rectCirCol(s2, s1);
		} else if(s1.shapeType.equals("Rec") && s2.shapeType.equals("Cir") && s1.r == 0) {
			return rectCirCol(s1, s2);
		}
		
		return polygonCol(s1, s2);
	}
	
	/**
	 * This function checks to see if a rectangle shape and a circle shape are colliding
	 * @param r rectangle shape
	 * @param c circle shape
	 * @return whether they are colliding
	 */
	public static boolean rectCirCol(Shape r, Shape c) {
		Vector2f close = Maths.getPointClosestToInRect(r, c.pos);
		
		float d = Maths.distance(close, c.pos);
		
		return d <= c.r;
	}
	
	/**
	 * This function checks if two circle shapes are colliding
	 * @param s1 shape 1
	 * @param s2 shape 2
	 * @return whether they are colliding
	 */
	public static boolean cirCirCol(Shape s1, Shape s2) {
		float d = Maths.distance(s1.pos, s2.pos);
		float r = s1.r + s2.r;
		
		return d <= r;
	}
	
	/**
	 * This function checks if two rectangle shapes are colliding
	 * @param sh1 shape 1
	 * @param sh2 shape 2
	 * @return whether they are colliding
	 */
	public static boolean rectRectCol(Shape sh1, Shape sh2) {
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
	
	/**
	 * This function checks if two polygons are colliding
	 * @param s1 shape 1
	 * @param s2 shape 2
	 * @return whether they are colliding
	 */
	public static boolean polygonCol(Shape s1, Shape s2) {
		s1.updateCol();
		s2.updateCol();

		Shape[] shapes = new Shape[2];
		shapes[0] = s1;
		shapes[1] = s2;

		for (Shape s : shapes) {
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
