package engine.graphics.font;

import java.awt.Font;

public class FontManager {
	
	public static TrueTypeFont arial = new TrueTypeFont(new Font("Arial",Font.PLAIN, 46), true);
	public static TrueTypeFont arialBold = new TrueTypeFont(new Font("Arial",Font.BOLD, 46), true);
	
	public static TrueTypeFont current = arial;
	public static void setFont(TrueTypeFont font) {
		current = font;
	}
	
	public static void drawString(float x, float y, String text, float scale) {
		current.drawString(x, y, text, scale, scale);
	}
}
