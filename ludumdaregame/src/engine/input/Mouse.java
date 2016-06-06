package engine.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector2f;

public class Mouse {
	
	public static Vector2f pos = new Vector2f(0, 0);
	public static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST+1];
	
	public static void posCallback(float x, float y) {
		pos.x = x;
		pos.y = y;
	}
	
	public static void buttonCallback(int button, int action) {
		
	}
	
	public static void scrollCallback(float x, float y) {
		
	}
	
	public static boolean isButtonDown(int button) {
		return buttons[button];
	}
}
