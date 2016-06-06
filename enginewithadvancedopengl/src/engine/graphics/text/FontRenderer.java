package engine.graphics.text;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.graphics.ShaderProgram;

public class FontRenderer {
	
	public static void render(Map<FontType, List<Text>> texts){
		prepare();
		for(FontType font : texts.keySet()){
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
			for(Text text : texts.get(font)){
				renderText(text);
			}
		}
		endRendering();
	}

	public static void cleanUp(){
		FontShader.current.cleanUp();
	}
	
	private static void prepare(){
		FontShader.current.start();
	}
	
	private static void renderText(Text text){
		GL30.glBindVertexArray(text.getMesh());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		FontShader.current.loadColor(text.getColor());
		FontShader.current.loadTranslation(text.getPosition());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	private static void endRendering(){
		ShaderProgram.stopShaders();
	}

}
