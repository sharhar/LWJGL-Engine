package engine.graphics.font;

import java.awt.Font;
/**
 * This class is used to manage fonts
 * @author Sharhar
 */

public class FontManager {
	
	public static TrueTypeFont arial = new TrueTypeFont(new Font("Arial",Font.PLAIN, 46), true);
	public static TrueTypeFont arialBold = new TrueTypeFont(new Font("Arial",Font.BOLD, 46), true);
	
	public static TrueTypeFont current = arial;
	
	/**
	 * Sets the FontManager's font
	 * @param font the font to be used
	 */
	public static void setFont(TrueTypeFont font) {
		current = font;
	}
	
	/**
	 * This function draws strings
	 * @param x x position of string
	 * @param y y position of string
	 * @param text the text to be rendered
	 * @param scale scale of the text
	 */
	public static void drawString(float x, float y, String text, float scale) {
		current.drawString(x, y, text, scale, scale);
	}
}
