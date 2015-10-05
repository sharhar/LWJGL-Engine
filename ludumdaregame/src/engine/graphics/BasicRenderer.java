package engine.graphics;

import static org.lwjgl.opengl.GL11.*;

import engine.graphics.font.FontManager;

public class BasicRenderer {
	
	public static void drawString(float x, float y, String text, float scale) {
		FontManager.drawString(x, y, text, scale/46.0f);
	}
	
	public static void renderRect(float x, float y, float w, float h, float r, Texture tex) {
		renderRect(x, y, w, h, r, tex.ID);
	}
	
	public static void masterRender(float x, float y, float w, float h, float r) {
		glPushMatrix();

		float W = w / 2;
		float H = h / 2;

		glTranslatef(x + W, y + H, 0);
		glRotatef(r, 0, 0, 1);
		
		glBegin(GL_QUADS);
		{
			glTexCoord2f(1, 1);
			glVertex2f(W, -H);
			glTexCoord2f(1, 0);
			glVertex2f(W, H);
			glTexCoord2f(0, 0);
			glVertex2f(-W, H);
			glTexCoord2f(0, 1);
			glVertex2f(-W, -H);
		}
		glEnd();

		glPopMatrix();
	}

	public static void renderRect(float x, float y, float w, float h, float r, int texID) {
		glPushMatrix();

		float W = w / 2;
		float H = h / 2;

		glTranslatef(x + W, y + H, 0);
		glRotatef(r, 0, 0, 1);
		
		if(texID != 0) {
			glBindTexture(GL_TEXTURE_2D, texID);
		}
		glBegin(GL_QUADS);
		{
			if (texID != 0) {
				glTexCoord2f(1, 1);
				glVertex2f(W, -H);
				glTexCoord2f(1, 0);
				glVertex2f(W, H);
				glTexCoord2f(0, 0);
				glVertex2f(-W, H);
				glTexCoord2f(0, 1);
				glVertex2f(-W, -H);
			} else {
				glVertex2f(W, -H);
				glVertex2f(W, H);
				glVertex2f(-W, H);
				glVertex2f(-W, -H);
				
				
			}
		}
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);

		glPopMatrix();
	}

}
