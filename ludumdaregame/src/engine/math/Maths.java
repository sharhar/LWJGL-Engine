package engine.math;

import org.lwjgl.util.vector.Vector2f;

public class Maths {
	
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
