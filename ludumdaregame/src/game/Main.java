package game;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class Main {

	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(800,  600));
			Display.setTitle("My Window");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		glClearColor(0,0,0,1);
		
		while(!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			
			Display.update();
		}
		
		Display.destroy();
	}

}
