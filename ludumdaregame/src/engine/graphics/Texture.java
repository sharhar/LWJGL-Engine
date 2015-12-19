/**
 * A class used to manage openGL textures
 * @author Sharhar
 */
package engine.graphics;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
	
	public int ID;
	public BufferedImage img = null;
	
	/**
	 * A basic constructor that creates the instance and nothing more
	 */
	public Texture() {
		ID = 0;
	}
	
	/**
	 * This constructor uses a BufferedImage to create an OpenGL texture
	 * @param image
	 */
	public Texture(BufferedImage image) {
		ID = loadFromImage(image);
	}
	
	/**
	 * This constructor uses a path to an image to create the OpenGL texture
	 * @param path
	 */
	public Texture(String path) {
		ID = load(path);
	}
	
	/**
	 * This function creates an OpenGL texture from a BufferedImage
	 * @param image the image to use
	 * @return the ID of the OpenGl texture
	 */
	public int loadFromImage(BufferedImage image) {
		int width = 0;
		int height = 0;
		int[] pixels = null;
		
		width = image.getWidth();
		height = image.getHeight();
		pixels = new int[width*height];
		image.getRGB(0,0,width,height,pixels,0,width);
		img = image;
		
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
	
	/**
	 * This function loads an OpenGL texture from the image's path
	 * @param path path to the image
	 * @return the ID of the OpenGL image
	 */
	public int load(String path) {
		int width = 0;
		int height = 0;
		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read(Texture.class.getResourceAsStream(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width*height];
			image.getRGB(0,0,width,height,pixels,0,width);
			img = image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	
	/**
	 * This function binds the texture from OpenGL to use
	 */
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, ID);
	}
	
	/**
	 * This function unbinds any texture from being used
	 */
	public static void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
}
