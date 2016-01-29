package engine.objects;

import engine.maths.Vector2f;
import engine.maths.Vector3f;

public class Light {
	
	public Vector2f pos;
	public float intensity;
	public Vector3f color;
	
	public Light(Vector2f pos, float intensity, Vector3f color) {
		this.pos = pos;
		this.intensity = intensity;
		this.color = color;
	}
}
