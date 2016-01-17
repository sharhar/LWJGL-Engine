package engine.window;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Canvas;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import engine.sound.SoundManager;

/**
 * This class is used to manage the window
 * @author Sharhar
 */
public class Window {
	
	public static JFrame frame;
	public static Canvas canvas;
	
	public static boolean closed = false;
	public static boolean resized = false;
	
	/**
	 * This function is used to create a window
	 * @param title title of the window
	 * @param width width of the window
	 * @param height height of the window
	 */
	public static void create(String title, int width, int height) {
		create(title,width,height,false,null);
	}
	
	/**
	 * This function is used to create a window
	 * @param title title of the window
	 * @param width width of the window
	 * @param height height of the window
	 * @param resizeable whether or not the window is resizable
	 * @param iconPath the path to the icon
	 */
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
			
			try {
				frame.setIconImage(ImageIO.read(Window.class.getResourceAsStream(iconPath)));
			} catch (Exception e) {
				System.out.println("Could not load Icon!");
			}
			
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		    frame.addWindowListener(new WindowAdapter() {
		        public void windowClosing(WindowEvent event) {
		            closed = true;
		        }
		    });
		    
		    frame.addComponentListener(new ComponentListener() {
		    	public void componentResized(ComponentEvent e) {
		            resized = true;       
		        }
				public void componentHidden(ComponentEvent arg0) {}
				public void componentMoved(ComponentEvent arg0) {}
				public void componentShown(ComponentEvent arg0) {}
		    });
			
			frame.setTitle(title);
			Display.create();
			frame.setResizable(resizeable);
			
			Mouse.create();
			Keyboard.create();
		} catch (LWJGLException e) {
			System.err.println("Could not create window!");
			e.printStackTrace();
		}
		
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
	
	/**
	 * This function clears the window
	 */
	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	/**
	 * This function returns the window's width
	 * @return the window's width
	 */
	public static int getWidth() {
		return Display.getWidth();
	}
	
	/**
	 * This function returns the window's title
	 * @return the window's title
	 */
	public static String getTitle() {
		return Display.getTitle();
	}
	
	/**
	 * This function returns the window's height
	 * @return the window's height
	 */
	public static int getHeight() {
		return Display.getHeight();
	}
	
	/**
	 * This function checks if the window was closed
	 * @return whether or not the window was closed
	 */
	public static boolean isClosed() {
		return closed;
	}
	
	/**
	 * This function updates the display
	 */
	public static void update() {
		if(resized) {
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
			resized = false;
		}
		Display.update();
		Display.sync(120);
	}
	
	/**
	 * This function destroys the window
	 */
	public static void destroy() {
		SoundManager.destroy();
		Display.destroy();
		frame.dispose();
		System.exit(0);
	}
}
