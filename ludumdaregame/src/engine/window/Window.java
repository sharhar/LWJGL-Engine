package engine.window;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Canvas;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
	public static boolean resized = false;
	
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
		    
		    frame.addComponentListener(new ComponentListener() {
		        @Override
		    	public void componentResized(ComponentEvent e) {
		            resized = true;       
		        }

				@Override
				public void componentHidden(ComponentEvent arg0) {
					
					
				}

				@Override
				public void componentMoved(ComponentEvent arg0) {
					
					
				}

				@Override
				public void componentShown(ComponentEvent arg0) {
					
					
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
		if(resized) {
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
			resized = false;
		}
		Display.update();
		Display.sync(120);
	}
	
	public static void destroy() {
		SoundManager.destroy();
		Display.destroy();
		frame.dispose();
	}
}
