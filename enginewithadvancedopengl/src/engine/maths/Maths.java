package engine.maths;

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
		
		result[getPos(0,0)] = matrix[getPos(0,0)] * c + matrix[getPos(1,0)] * s;
		result[getPos(0,1)] = matrix[getPos(0,1)] * c + matrix[getPos(1,1)] * s;
		result[getPos(0,2)] = matrix[getPos(0,2)] * c + matrix[getPos(1,2)] * s;
		result[getPos(0,3)] = matrix[getPos(0,3)] * c + matrix[getPos(1,3)] * s;
		result[getPos(1,0)] = matrix[getPos(0,0)] * -s + matrix[getPos(1,0)] * c;
		result[getPos(1,1)] = matrix[getPos(0,1)] * -s + matrix[getPos(1,1)] * c;
		result[getPos(1,2)] = matrix[getPos(0,2)] * -s + matrix[getPos(1,2)] * c;
		result[getPos(1,3)] = matrix[getPos(0,3)] * -s + matrix[getPos(1,3)] * c;
		
		return result;
	}
	
	public static float[] matrixRotate(float[] matrix, double angle, Vector3f axis) {
		float[] result = new float[16];
		System.arraycopy(matrix, 0, result, 0, 16);
		
		float c = (float) Math.cos(angle);
		float s = (float) Math.sin(angle);
		float oneminusc = 1.0f - c;
		float xy = axis.x*axis.y;
		float yz = axis.y*axis.z;
		float xz = axis.x*axis.z;
		float xs = axis.x*s;
		float ys = axis.y*s;
		float zs = axis.z*s;

		float f00 = axis.x*axis.x*oneminusc+c;
		float f01 = xy*oneminusc+zs;
		float f02 = xz*oneminusc-ys;
		
		float f10 = xy*oneminusc-zs;
		float f11 = axis.y*axis.y*oneminusc+c;
		float f12 = yz*oneminusc+xs;
		
		float f20 = xz*oneminusc+ys;
		float f21 = yz*oneminusc-xs;
		float f22 = axis.z*axis.z*oneminusc+c;


		float t00 = matrix[getPos(0,0)] * f00 + matrix[getPos(1,0)] * f01 + matrix[getPos(2,0)] * f02;
		float t01 = matrix[getPos(0,1)] * f00 + matrix[getPos(1,1)] * f01 + matrix[getPos(2,1)] * f02;
		float t02 = matrix[getPos(0,2)] * f00 + matrix[getPos(1,2)] * f01 + matrix[getPos(2,2)] * f02;
		float t03 = matrix[getPos(0,3)] * f00 + matrix[getPos(1,3)] * f01 + matrix[getPos(2,3)] * f02;
		float t10 = matrix[getPos(0,0)] * f10 + matrix[getPos(1,0)] * f11 + matrix[getPos(2,0)] * f12;
		float t11 = matrix[getPos(0,1)] * f10 + matrix[getPos(1,1)] * f11 + matrix[getPos(2,1)] * f12;
		float t12 = matrix[getPos(0,2)] * f10 + matrix[getPos(1,2)] * f11 + matrix[getPos(2,2)] * f12;
		float t13 = matrix[getPos(0,3)] * f10 + matrix[getPos(1,3)] * f11 + matrix[getPos(2,3)] * f12;
		result[getPos(0,0)] = t00;
		result[getPos(0,1)] = t01;
		result[getPos(0,2)] = t02;
		result[getPos(0,3)] = t03;
		result[getPos(1,0)] = t10;
		result[getPos(1,1)] = t11;
		result[getPos(1,2)] = t12;
		result[getPos(1,3)] = t13;
		result[getPos(2,0)] = matrix[getPos(0,0)] * f20 + matrix[getPos(1,0)] * f21 + matrix[getPos(2,0)] * f22;
		result[getPos(2,1)] = matrix[getPos(0,1)] * f20 + matrix[getPos(1,1)] * f21 + matrix[getPos(2,1)] * f22;
		result[getPos(2,2)] = matrix[getPos(0,2)] * f20 + matrix[getPos(1,2)] * f21 + matrix[getPos(2,2)] * f22;
		result[getPos(2,3)] = matrix[getPos(0,3)] * f20 + matrix[getPos(1,3)] * f21 + matrix[getPos(2,3)] * f22;
		
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
}
