package input;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

public class MouseInput {
	
	public static boolean pressed = false;
	public static boolean past_pressed = false;
	
	public static void update() {
		past_pressed = pressed;
		pressed = Mouse.isButtonDown(0);
	}
	
	public static boolean isMousePressed() {
		return pressed && !past_pressed;
	}
	
	public static Vector2f getMousePos() {
		int x = Mouse.getX();
		int y = Mouse.getY();
		
		return new Vector2f(x, y);
	}
	
}
