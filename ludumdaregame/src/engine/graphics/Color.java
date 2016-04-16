
package engine.graphics;
/**
 * This class is used for colors
 * @author Sharhar
 */
public class Color {
	
	public static Color black = new Color(0,0,0);
	public static Color white = new Color(1,1,1);
	
	public float r, g, b;
	
	public Color(Color c) {
		this.r = c.r;
		this.g = c.g;
		this.b = c.b;
	}
	
	/**
	 * Initializes RGB values
	 * @param r red
	 * @param g green
	 * @param b blue
	 */
	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	/**
	 * Initializes RGB values and sets them to white
	 */
	public Color() {
		this(1, 1, 1);
	}
}
