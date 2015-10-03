package engine.window;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class Window {
	
	public static void create(String title, int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		glClearColor(0, 0, 0, 1);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width,  0,  height, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_TEXTURE_2D);
	}
	
	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public static int getWidth() {
		return Display.getWidth();
	}
	
	public static String getTitle() {
		return Display.getTitle();
	}
	
	public static int getHeight() {
		return Display.getHeight();
	}
	
	public static boolean isClosed() {
		return Display.isCloseRequested();
	}
	
	public static void update() {
		Display.update();
		Display.sync(60);
	}
	
	public static void destroy() {
		Display.destroy();
	}
}
