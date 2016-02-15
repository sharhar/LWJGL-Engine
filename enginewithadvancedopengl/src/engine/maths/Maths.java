package engine.maths;

import engine.objects.CollisionShape;

public class Maths {
	
	public static float[] createTransformationMatrix(Vector2f translation, float rotation, Vector2f scale) {
		float[] result = getIdentityMatrix();
		result = matrixTranslate(result, translation);
		result = matrixRotate(result, Math.toRadians(rotation));
		result = matrixScale(result, scale);
		return result;
	}
	
	public static String matrixToString(float[] data) {
		String result = "";
		result += data[getPos(0, 0)] + " " + data[getPos(1, 0)] + " " + data[getPos(2, 0)] + " " + data[getPos(3, 0)] + "\n";
		result += data[getPos(0, 1)] + " " + data[getPos(1, 1)] + " " + data[getPos(2, 1)] + " " + data[getPos(3, 1)] + "\n";
		result += data[getPos(0, 2)] + " " + data[getPos(1, 2)] + " " + data[getPos(2, 2)] + " " + data[getPos(3, 2)] + "\n";
		result += data[getPos(0, 3)] + " " + data[getPos(1, 3)] + " " + data[getPos(2, 3)] + " " + data[getPos(3, 3)] + "\n";
		return result;
	}
	
	public static float[] matrixScale(float[] matrix, Vector2f scale) {
		float[] result = new float[16];
		System.arraycopy(matrix, 0, result, 0, 16);
		
		result[getPos(0,0)] *= scale.x;
		result[getPos(0,1)] *= scale.x;
		result[getPos(0,2)] *= scale.x;
		result[getPos(0,3)] *= scale.x;
		result[getPos(1,0)] *= scale.y;
		result[getPos(1,1)] *= scale.y;
		result[getPos(1,2)] *= scale.y;
		result[getPos(1,3)] *= scale.y;
		
		return result;
	}
	
	public static float[] matrixTranslate(float[] matrix, Vector2f vec) {
		float[] result = new float[16];
		System.arraycopy(matrix, 0, result, 0, 16);
		
		result[getPos(3,0)] += matrix[getPos(0,0)] * vec.x + matrix[getPos(1,0)] * vec.y;
		result[getPos(3,1)] += matrix[getPos(0,1)] * vec.x + matrix[getPos(1,1)] * vec.y;
		result[getPos(3,2)] += matrix[getPos(0,2)] * vec.x + matrix[getPos(1,2)] * vec.y;
		result[getPos(3,3)] += matrix[getPos(0,3)] * vec.x + matrix[getPos(1,3)] * vec.y;
		
		return result;
	}
	
	public static float[] matrixRotate(float[] matrix, double angle) {
		float[] result = new float[16];
		System.arraycopy(matrix, 0, result, 0, 16);
		
		float c = (float) Math.cos(angle);
		float s = (float) Math.sin(angle);
		
		result[0*4 + 0] = matrix[0*4 + 0] *  c + matrix[1*4 + 0] * s;
		result[0*4 + 1] = matrix[0*4 + 1] *  c + matrix[1*4 + 1] * s;
		result[0*4 + 2] = matrix[0*4 + 2] *  c + matrix[1*4 + 2] * s;
		result[0*4 + 3] = matrix[0*4 + 3] *  c + matrix[1*4 + 3] * s;
		result[1*4 + 0] = matrix[0*4 + 0] * -s + matrix[1*4 + 0] * c;
		result[1*4 + 1] = matrix[0*4 + 1] * -s + matrix[1*4 + 1] * c;
		result[1*4 + 2] = matrix[0*4 + 2] * -s + matrix[1*4 + 2] * c;
		result[1*4 + 3] = matrix[0*4 + 3] * -s + matrix[1*4 + 3] * c;
		
		return result;
	}
	
	public static float[] matrixRotate(float[] matrix, double angle, Vector3f axis) {
		float[] result = new float[16];
		System.arraycopy(matrix, 0, result, 0, 16);
		
		float c = (float) Math.cos(angle);
		float s = (float) Math.sin(angle);
		float omc = 1.0f - c;

		float r00 = axis.x*axis.x*omc+c;
		float r01 = axis.x*axis.y*omc+axis.z*s;
		float r02 = axis.x*axis.z*omc-axis.y*s;
		
		float r10 = axis.x*axis.y*omc-axis.z*s;
		float r11 = axis.y*axis.y*omc+c;
		float r12 = axis.y*axis.z*omc+axis.x*s;
		
		float r20 = axis.x*axis.z*omc+axis.y*s;
		float r21 = axis.y*axis.z*omc-axis.x*s;
		float r22 = axis.z*axis.z*omc+c;

		result[getPos(0,0)] = matrix[getPos(0,0)] * r00 + matrix[getPos(1,0)] * r01 + matrix[getPos(2,0)] * r02;
		result[getPos(0,1)] = matrix[getPos(0,1)] * r00 + matrix[getPos(1,1)] * r01 + matrix[getPos(2,1)] * r02;
		result[getPos(0,2)] = matrix[getPos(0,2)] * r00 + matrix[getPos(1,2)] * r01 + matrix[getPos(2,2)] * r02;
		result[getPos(0,3)] = matrix[getPos(0,3)] * r00 + matrix[getPos(1,3)] * r01 + matrix[getPos(2,3)] * r02;
		result[getPos(1,0)] = matrix[getPos(0,0)] * r10 + matrix[getPos(1,0)] * r11 + matrix[getPos(2,0)] * r12;
		result[getPos(1,1)] = matrix[getPos(0,1)] * r10 + matrix[getPos(1,1)] * r11 + matrix[getPos(2,1)] * r12;
		result[getPos(1,2)] = matrix[getPos(0,2)] * r10 + matrix[getPos(1,2)] * r11 + matrix[getPos(2,2)] * r12;
		result[getPos(1,3)] = matrix[getPos(0,3)] * r10 + matrix[getPos(1,3)] * r11 + matrix[getPos(2,3)] * r12;
		result[getPos(2,0)] = matrix[getPos(0,0)] * r20 + matrix[getPos(1,0)] * r21 + matrix[getPos(2,0)] * r22;
		result[getPos(2,1)] = matrix[getPos(0,1)] * r20 + matrix[getPos(1,1)] * r21 + matrix[getPos(2,1)] * r22;
		result[getPos(2,2)] = matrix[getPos(0,2)] * r20 + matrix[getPos(1,2)] * r21 + matrix[getPos(2,2)] * r22;
		result[getPos(2,3)] = matrix[getPos(0,3)] * r20 + matrix[getPos(1,3)] * r21 + matrix[getPos(2,3)] * r22;
		
		return result;
	}
	
	public static float[] getIdentityMatrix() {
		float[] matrix = new float[16];
		for(int i = 0; i < 16;i++) {
			matrix[i] = 0;
		}
		
		matrix[getPos(0,0)] = 1;
		matrix[getPos(1,1)] = 1;
		matrix[getPos(2,2)] = 1;
		matrix[getPos(3,3)] = 1;
		return matrix;
	}
	
	public static int getPos(int x, int y) {
		return y + x * 4;
	}
	
	public static Vector2f rotVec(Vector2f vec, Vector2f origin, float theta) {
		if(theta == 0) {
			return vec;
		}
		
		Vector2f result = new Vector2f(0, 0);
		
		float thetaRad = (float) Math.toRadians(theta);
		
		result.x = (float) (Math.cos(thetaRad) * (vec.x - origin.x) - Math.sin(thetaRad) * (vec.y - origin.y) + origin.x);
		result.y = (float) (Math.sin(thetaRad) * (vec.x - origin.x) + Math.cos(thetaRad) * (vec.y - origin.y) + origin.y);
		
		return result;
	}
	
	public static Vector2f getPointClosestToInRect(CollisionShape s, Vector2f p) {
		if(!s.type.equals("Rect")) {
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
}
