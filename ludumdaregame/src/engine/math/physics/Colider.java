package engine.math.physics;

import org.lwjgl.util.vector.Vector2f;

import engine.objects.shapes.Shape;

public class Colider {

	public static boolean isShapeHitting(Shape s1, Shape s2) {
		return false;
	}

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
