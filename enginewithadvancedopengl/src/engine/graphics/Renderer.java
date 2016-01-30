package engine.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.graphics.models.RawModel;
import engine.graphics.models.TexturedModel;
import engine.maths.Maths;
import engine.objects.Entity;
import engine.shaders.ShaderProgram;
import engine.shaders.StaticShader;

public class Renderer {
	
	public static float[] projectionMatrix = null;
	
	public static List<Entity> entities = new ArrayList<Entity>();
	
	public static void init(int width, int height) {
		float right = (float) width;
		float left = 0;
		float top = (float) height;
		float bottom = 0;
		float near = -Float.MAX_VALUE/3;
		float far = Float.MAX_VALUE/3;
		
		projectionMatrix = new float[16];
		
		for(int i = 0;i < 16;i++) {
			projectionMatrix[i] = 0;
		}
		
		projectionMatrix[0 + 0*4] = 2/(right - left);
		projectionMatrix[1 + 1*4] = 2/(top - bottom);
		projectionMatrix[2 + 2*4] = 2/(far - near);
		projectionMatrix[0 + 3*4] = -(right + left)/(right - left);
		projectionMatrix[1 + 3*4] = -(top + bottom)/(top - bottom);
		projectionMatrix[2 + 3*4] = -(far + near)/(far - near);
		projectionMatrix[3 + 3*4] = 1;
		
		StaticShader.basicShader.start();
		StaticShader.basicShader.loadProjectionMatrix(projectionMatrix);
		ShaderProgram.stopShaders();
	}
	
	protected static void render(Map<RawModel, Map<Integer, List<Entity>>> entities) {
		for(RawModel raw:entities.keySet()) {
			GL30.glBindVertexArray(raw.getVaoID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			Map<Integer, List<Entity>> texList = entities.get(raw);
			//System.out.println(texList);
			for(Integer tex:texList.keySet()) {
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
				List<Entity> entity = texList.get(tex);
				for(Entity ent:entity) {
					float[] transformationMatrix = Maths.createTransformationMatrix(ent.getPosition(), ent.getRotation(), ent.getScale());
					StaticShader.basicShader.loadTransformationMatrix(transformationMatrix);
					GL11.glDrawElements(GL11.GL_TRIANGLES, raw.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
				}
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			}
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
	}
	
	public static void renderScene() {
		StaticShader.basicShader.start();
		for(Entity ent:entities) {
			render(ent);
		}
		ShaderProgram.stopShaders();
		entities.clear();
	}
	
	public static void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public static void render(Entity entity) {
		TexturedModel model = entity.getModel();
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		float[] transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
		StaticShader.basicShader.loadTransformationMatrix(transformationMatrix);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
}
