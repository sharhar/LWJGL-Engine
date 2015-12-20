
package engine.graphics;
/**
 * This class is used for colors
 * @author Sharhar
 */
public class Color {
	
	public float r, g, b;
	
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
