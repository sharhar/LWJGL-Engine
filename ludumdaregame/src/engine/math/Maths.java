package engine.math;

import org.lwjgl.util.vector.Vector2f;

import engine.objects.shapes.Shape;

public class Maths {
	
	public static Vector2f getPointClosestToInRect(Shape s, Vector2f p) {
		if(!s.shapeType.equals("Rect")) {
			System.err.println("Shape " + s + " is not a rectangle!");
			return null;
		}
		
		Vector2f result = new Vector2f(p.x, p.y);
		
		Vector2f pos = s.pos;
		Vector2f size = s.other[0];
		
		if(result.x < pos.x) {
			result.x = pos.x;
		} else if(result.x > size.x + pos.x) {
			result.x = size.x + pos.x;
		}
		
		if(result.y < pos.y) {
			result.y = pos.y;
		} else if(result.y > size.y + pos.y) {
			result.y = size.y + pos.y;
		}
		
		return result;
	}
	
	public static float distance(Vector2f p1, Vector2f p2) {
		return pythagorean(p1.x - p2.x, p1.y - p2.y);
	}
	
	public static float pythagorean(float a, float b) {
		return (float) Math.sqrt(a*a + b*b);
	}
	
	public static Vector2f rotVec(Vector2f vec, Vector2f origin, float theta) {
		if(theta == 0) {
			return vec;
		}
		
		Vector2f result = new Vector2f();
		
		float thetaRad = (float) Math.toRadians(theta);
		
		result.x = (float) (Math.cos(thetaRad) * (vec.x - origin.x) - Math.sin(thetaRad) * (vec.y - origin.y) + origin.x);
		result.y = (float) (Math.sin(thetaRad) * (vec.x - origin.x) + Math.cos(thetaRad) * (vec.y - origin.y) + origin.y);
		
		return result;
	}
	
}
