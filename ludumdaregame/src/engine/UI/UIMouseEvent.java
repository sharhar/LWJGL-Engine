package engine.UI;

import org.lwjgl.util.vector.Vector2f;

public class UIMouseEvent {
	
	public static final int PRESSED = 1;
	public static final int RELEASED = 2;
	
	public Vector2f pos;
	public int state = -1;
	
	public UIMouseEvent(Vector2f pos, int state) {
		this.pos = pos;
		this.state = state;
	}
}
