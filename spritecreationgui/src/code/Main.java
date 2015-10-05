package code;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import gl.Renderer;
import gl.shapes.Shape;
import input.MouseInput;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static Shape temp = null;
	public static boolean drawingShape = false;
	
	public static List<Shape> shapes = new ArrayList<Shape>();
	
	public static Window window = null;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "GL View";
	
	public static void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle(TITLE);
			Display.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void initGL() {
		glClearColor(0, 0, 0, 1);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH,  0,  HEIGHT, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
	}
	
	public static void init() {
		initDisplay();
		initGL();
	}
	
	public static void destroy() {
		Display.destroy();
		System.exit(0);
	}
	
	public static void startShape() {
		temp = new Shape();
		drawingShape = true;
	}
	
	public static void endShape() {
		drawingShape = false;
		getShapes().add(temp);
	}
	
	public static synchronized List<Shape> getShapes() {
		return shapes;
	}
	
	public static void main(String[] args) {
		window = new Window();
		init();
		
		while(!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			MouseInput.update();
			glColor3f(1, 1, 1);
			if(drawingShape) {
				if(MouseInput.isMousePressed()) {
					temp.addVec(MouseInput.getMousePos());
				}
				Renderer.startEditMode();
				Renderer.editRenderShape(temp);
				Renderer.renderNextTri(temp);
				Renderer.stopEditMode();
			}
			
			for(Shape shape:getShapes()) {
				Renderer.renderShape(shape);
			}
			
			Display.update();
		}
		
		destroy();
	}
}
