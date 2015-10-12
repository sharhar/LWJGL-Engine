package engine.input;

import org.lwjgl.input.Keyboard;

public class KeyInput {
	
	public static final int KEY_NUM = 256;
	
	
	
	public static boolean[] keys = new boolean[KEY_NUM];
	public static boolean[] past_keys = new boolean[KEY_NUM];
	
	public static void update() {
		for(int i = 0;i < past_keys.length;i++) {
			past_keys[i] = keys[i];
		}
		
		for(int i = 0; i < keys.length;i ++) {
			keys[i] = Keyboard.isKeyDown(i);
		}
	}
	
	public static boolean isKeyRealeased(int key) {
		return !keys[key] && past_keys[key];
	}
	
	public static boolean isKeyPressed(int key) {
		return keys[key] && !past_keys[key];
	}
	
	public static boolean isKeyDown(int key) {
		return keys[key];
	}
}
