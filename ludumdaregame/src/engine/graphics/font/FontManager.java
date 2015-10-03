package engine.graphics.font;

import java.awt.Font;

public class FontManager {
	
	public static TrueTypeFont arial = new TrueTypeFont(new Font("Arial",Font.PLAIN, 46), true);
	
	public static void drawString(float x, float y, String text, float scale) {
		arial.drawString(x, y, text, scale, scale);
	}
}
