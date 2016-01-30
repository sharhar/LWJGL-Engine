package engine.objects;

import engine.maths.Vector2f;
import engine.maths.Vector3f;

public class Light {
	
	protected static boolean[] lightIDs;
	public static void initLightArray(int amount) {
		lightIDs = new boolean[amount];
	}
	
	protected static int getID() {
		for(int i = 0; i < lightIDs.length;i++) {
			if(!lightIDs[i]) {
				lightIDs[i] = true;
				return i;
			}
		}
		
		throw new ArrayIndexOutOfBoundsException("Too many lights in scene!");
	}
	
	public Vector2f pos;
	public float intensity;
	public Vector3f color;
	public Vector3f attenuation;
	public float z;
	public int index;
	public int ID;
	public float range;
	
	public Light(Vector2f pos, float intensity, float range, Vector3f color, Vector3f attenuation, float z) {
		this.pos = pos;
		this.range = range;
		this.intensity = intensity;
		this.color = color;
		this.attenuation = attenuation;
		this.z = z;
		this.ID = getID();
	}
	
	public static Light createDefaultLight(Vector2f pos, float intensity, float range) {
		return new Light(pos, intensity, range, new Vector3f(1, 1, 1), new Vector3f(0.5f,  01f,  0), 100.0f);
	}
	
	public static Light createDefaultLight(Vector2f pos, float intensity, float range, Vector3f color) {
		return new Light(pos, intensity, range, color, new Vector3f(0.5f,  01f,  0), 100.0f);
	}
	
	public void destroy() {
		lightIDs[ID] = false;
	}
}
