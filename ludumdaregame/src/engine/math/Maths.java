package engine.math;

import org.lwjgl.util.vector.Vector2f;

import engine.UI.Bounds;
import engine.objects.shapes.CollisionShape;

/**
 * A Math class to do Math
 * @author Sharhar
 */
public class Maths {
	
	public static float PI = getPi();
	public static float TAU = PI*2;
	public static float PHI = (float) ((Math.sqrt(5)/2)+0.5);
	public static float E = 2.718281828459045f;
	
	public static boolean inBounds(Bounds bounds, Vector2f vect) {
		Vector2f p1 = new Vector2f(bounds.x,bounds.y);
		Vector2f p2 = vect;
		Vector2f s1 = new Vector2f(bounds.w,bounds.h);
		
		if(p1.x + s1.x< p2.x || p2.x < p1.x) {
			return false;
		}
		
		if(p1.y + s1.y < p2.y || p2.y< p1.y) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * This function is used the rectangle circle collision algorithm
	 * @param s shape
	 * @param p point
	 * @return closest point in rectangle
	 */
	public static Vector2f getPointClosestToInRect(CollisionShape s, Vector2f p) {
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
	
	/**
	 * This function calculates the distance between two points
	 * @param p1 point 1
	 * @param p2 point 2
	 * @return the distance
	 */
	public static float distance(Vector2f p1, Vector2f p2) {
		return pythagorean(p1.x - p2.x, p1.y - p2.y);
	}
	
	/**
	 * This function calculates the hypotenuse of the right triangle with side lengths a and b
	 * @param a side a
	 * @param b side b
	 * @return length of side c
	 */
	public static float pythagorean(float a, float b) {
		return (float) Math.sqrt(a*a + b*b);
	}
	
	private static float getPi() {
		float acc = 3;
		
		boolean done = false;
		
		for(int i = 1;!done;i++) {
			float prev = acc;
			
			acc += (((4.0)/((i*2.0) * ((i*2.0) + 1) * (2.0 * (i + 1)))) * (((i%2) * 2) - 1));
			if(prev == acc) {
				done = true;
			}
		}
		
		return acc;
	}
	
	/**
	 * this function converts degrees to radians
	 * @param a degrees
	 * @return radians
	 */
	public static float toRadians(float a) {
		return (a%360)/180.0f * PI;
	}
	
	/**
	 * This function is a cosine approximation created to yield better trigonometry performance (STILL EXPIREMENTAL)
	 * @param a degrees
	 * @return cosine of degrees
	 */
	public static float cos(float a) {
		return sin(90 - a);
	}
	
	/**
	 * This function returns the sign of a number
	 * @param n number
	 * @return sign of number
	 */
	public static int sign(float n) {
		if(n > 0) {
			return 1;
		} else if (n < 0) {
			return -1;
		}
		
		return 0;
	}
	
	/**
	 * This function is a sine approximation created to yield better trigonometry performance (STILL EXPIREMENTAL)
	 * @param a degrees
	 * @return sine of degrees
	 */
	public static float sin(float a) {
		float x = toRadians(a);
		
		float sin = 0;
		if (x < 0)
		{
		    sin = 1.27323954f * x + 0.405284735f * x * x;
		    
		    if (sin < 0)
		        sin = 0.225f * (sin *-sin - sin) + sin;
		    else
		        sin = 0.225f * (sin * sin - sin) + sin;
		}
		else
		{
		    sin = 1.27323954f * x - 0.405284735f * x * x;
		    
		    if (sin < 0)
		        sin = 0.225f * (sin *-sin - sin) + sin;
		    else
		        sin = 0.225f * (sin * sin - sin) + sin;
		}
		
		return sin;
	}
	
	/**
	 * This function rotates a Vector
	 * @param vec vector to rotate
	 * @param origin origin of rotation
	 * @param theta angle to rotate by
	 * @return rotated vector
	 */
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
