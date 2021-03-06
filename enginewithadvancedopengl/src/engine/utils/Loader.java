package engine.utils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.graphics.models.RawModel;

public class Loader {
	
	private static List<Integer> vaos = new ArrayList<Integer>();
	private static List<Integer> vbos = new ArrayList<Integer>();

	public static int loadToVAO(float[] positions, float[] texCoords) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, positions);
		storeDataInAttributeList(1, texCoords);
		unbindVAO();
		return vaoID;
	}
	
	public static RawModel loadToVAORaw(float[] positions, float[] texCoords) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, positions);
		storeDataInAttributeList(1, texCoords);
		unbindVAO();
		return new RawModel(vaoID, positions.length/2);
	}
	
	public static RawModel loadToVAO(float[] positions, float[] texCoords, int[] indices) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, positions);
		storeDataInAttributeList(1, texCoords);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}
	
	private static int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private static void storeDataInAttributeList(int attributeNumber, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, 2, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	private static void bindIndicesBuffer(int[] indices) {
		int vboID =  GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
	}
	
	private static IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private static FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public static int loadTexture(BufferedImage image) {
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
		
		int tex = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		IntBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		buffer.put(data).flip();
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D,0,GL11.GL_RGBA,width,height,0,GL11.GL_RGBA,GL11.GL_UNSIGNED_BYTE, buffer);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return tex;
	}
	
	public static int loadTexture(String path) {
		int width = 0;
		int height = 0;
		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read(Loader.class.getResourceAsStream(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width*height];
			image.getRGB(0,0,width,height,pixels,0,width);
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
		
		int tex = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		IntBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		buffer.put(data).flip();
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D,0,GL11.GL_RGBA,width,height,0,GL11.GL_RGBA,GL11.GL_UNSIGNED_BYTE, buffer);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return tex;
	}
	
	public static void cleanUp() {
		for(int vao:vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo:vbos) {
			GL15.glDeleteBuffers(vbo);
		}
	}
}
