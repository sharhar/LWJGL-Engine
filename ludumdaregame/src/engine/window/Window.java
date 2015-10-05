package engine.window;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import engine.sound.SoundManager;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class Window {
	
	public static void create(String title, int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width-10, height-10));
			Display.setTitle(title);
			Display.create();
			Mouse.create();
			Keyboard.create();
			//fix white border
			Display.setDisplayMode(new DisplayMode(width, height));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		Display.setResizable(true);
		Display.setVSyncEnabled(false);
		
		glClearColor(0, 0, 0, 1);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width,  0,  height, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		SoundManager.init();
	}
	
	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public static int getWidth() {
		return Display.getWidth();
	}
	
	public static void setIcon(String path) {
		BufferedImage icon = null;
		try {
			icon = ImageIO.read(Window.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		setIcon(icon);
	}
	
	public static void setIcon(BufferedImage icon) {
		ByteBuffer buffer = convertImageData(icon);
		ByteBuffer[] buffers = new ByteBuffer[1];
		buffers[0] = buffer;
		Display.setIcon(buffers);
	}
	
	private static ByteBuffer convertImageData(BufferedImage bi) {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    try {
	        ImageIO.write(bi, "png", out);
	        return ByteBuffer.wrap(out.toByteArray());
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	    return null;
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
		Display.sync(120);
	}
	
	public static void destroy() {
		SoundManager.destroy();
		Display.destroy();
	}
}
