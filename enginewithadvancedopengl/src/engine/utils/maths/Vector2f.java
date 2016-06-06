package engine.utils.maths;

public class Vector2f {
	
	public float x, y;
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f multiply(float f) {
		return new Vector2f(x * f, y * f);
	}
	
	public Vector2f multiply(Vector2f vec) {
		return new Vector2f(x * vec.x, y * vec.y);
	}
	
	public Vector2f add(Vector2f vec) {
		return new Vector2f(x + vec.x, y + vec.y);
	}
}
