package code;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import gl.Renderer;
import gl.shapes.Shape;
import input.MouseInput;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Main {
	
	public static Shape temp = null;
	public static boolean drawingShape = false;
	
	public static List<Shape> shapes = new ArrayList<Shape>();
	
	public static Window window = null;
	
	public static BufferedImage imageBuffer = null;
	public static int texture  = 0;
	public static boolean readImage = false;
	
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
		//glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
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
		//getShapes().add(temp);
	}
	
	public static synchronized List<Shape> getShapes() {
		return shapes;
	}
	
	public static void main(String[] args) {
		window = new Window();
		init();
		
		try {
			imageBuffer = ImageIO.read(Main.class.getResourceAsStream("/pic.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		texture = loadFromImage(imageBuffer);
		
		while(!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			MouseInput.update();
			glColor3f(1, 1, 1);
			
			if(readImage) {
				texture = loadFromImage(imageBuffer);
				readImage = false;
			}
			/*
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
			*/
			
			if(drawingShape) {
				if(MouseInput.isMousePressed()) {
					temp.addVec(MouseInput.getMousePos());
				}
				glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
				Renderer.renderShape(temp);
				glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );
			}
			
			glBindTexture(GL_TEXTURE_2D, texture);
			
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 1);
				glVertex2f(100, 100);
				glTexCoord2f(0, 0);
				glVertex2f(100, 500);
				glTexCoord2f(1, 0);
				glVertex2f(500, 500);
				glTexCoord2f(1, 1);
				glVertex2f(500, 100);
			}
			glEnd();
			
			glBindTexture(GL_TEXTURE_2D, 0);
			
			Display.update();
		}
		
		destroy();
	}
	
	public static int loadFromImage(BufferedImage image) {
		int width = 0;
		int height = 0;
		int[] pixels = null;
		
		width = image.getWidth();
		height = image.getHeight();
		pixels = new int[width*height];
		image.getRGB(0,0,width,height,pixels,0,width);
		
		int[] data = new int[width*height];
		for(int i = 0;i < width*height;i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		
		int tex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tex);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		IntBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		buffer.put(data).flip();
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width,height,0,GL_RGBA,GL_UNSIGNED_BYTE, buffer);
		glBindTexture(GL_TEXTURE_2D, 0);
		return tex;
	}
}
