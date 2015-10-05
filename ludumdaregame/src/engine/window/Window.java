package engine.window;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.awt.Canvas;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import engine.sound.SoundManager;

public class Window {
	
	public static JFrame frame;
	public static Canvas canvas;
	
	public static boolean closed = false;
	
	public static void create(String title, int width, int height) {
		create(title,width,height,false,null);
	}
	
	public static void create(String title, int width, int height, boolean resizeable, String iconPath) {
		try {
			frame = new JFrame();
			frame.setVisible(false);
			
			canvas = new Canvas();
			canvas.setSize(width,height);
			
			Display.setParent(canvas);
			
			frame.add(canvas);
			
			frame.pack();
			frame.setVisible(true);
			canvas.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			
			if(iconPath != null) {
				frame.setIconImage(ImageIO.read(Window.class.getResourceAsStream(iconPath)));
			}
			
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		    frame.addWindowListener(new WindowAdapter() {
		        @Override
		        public void windowClosing(WindowEvent event) {
		            closed = true;
		        }
		    });
			
			frame.setTitle(title);
			Display.create();
			frame.setResizable(resizeable);
			
			Mouse.create();
			Keyboard.create();
			//fix white border
			//Display.setDisplayMode(new DisplayMode(width, height));
		} catch (Exception e) {
			System.out.println("NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
			e.printStackTrace();
		}
		
		//Display.setResizable(true);
		//Display.setVSyncEnabled(false);
		
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
		return closed;
	}
	
	public static void update() {
		Display.update();
		Display.sync(120);
	}
	
	public static void destroy() {
		SoundManager.destroy();
		Display.destroy();
		frame.dispose();
	}
}
