
package engine.input;

import org.lwjgl.input.Keyboard;
/**
 * This class manages keyboard input
 * @author Sharhar
 */
public class KeyInput {
	
	public static final int KEY_NUM = 256;
	public static boolean[] keys = new boolean[KEY_NUM];
	public static boolean[] past_keys = new boolean[KEY_NUM];
	
	/**
	 * This function updates the boolean arrays used to determine if a key was pressed
	 */
	public static void update() {
		for(int i = 0;i < past_keys.length;i++) {
			past_keys[i] = keys[i];
		}
		
		for(int i = 0; i < keys.length;i ++) {
			keys[i] = Keyboard.isKeyDown(i);
		}
	}
	
	/**
	 * This function checks if a key on the keyboard was released
	 * @param key the key to check
	 * @return whether or not the key was released
	 */
	public static boolean isKeyReleased(int key) {
		return !keys[key] && past_keys[key];
	}
	
	/**
	  * This function checks if a key on the keyboard was pressed
	 * @param key the key to check
	 * @return whether or not the key was pressed
	 */
	public static boolean isKeyPressed(int key) {
		return keys[key] && !past_keys[key];
	}
	
	/**
	 * This function checks if a key is down
	 * @param key the key to check
	 * @return whether or not the key is pressed
	 */
	public static boolean isKeyDown(int key) {
		return keys[key];
	}
}
