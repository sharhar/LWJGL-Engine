
package engine.graphics;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.font.FontManager;
/**
 * This class is used to render basic shapes and objects
 * @author Sharhar
 */
public class BasicRenderer {

	/**
	 * This function renders a string
	 * @param x x position of string
	 * @param y y position of string
	 * @param text text to render
	 * @param scale scale of the text
	 */
	public static void drawString(float x, float y, String text, float scale) {
		FontManager.drawString(x, y, text, scale / 46.0f);
	}

	/**
	 * This function renders a rectangle
	 * @param x x position of rectangle
	 * @param y y position of rectangle
	 * @param w width of rectangle
	 * @param h height of rectangle
	 * @param r rotation of rectangle
	 * @param tex texture of rectangle
	 * @param c color of rectangle
	 */
	public static void renderRect(float x, float y, float w, float h, float r, Texture tex, Color c) {
		renderRect(x, y, w, h, r, tex.ID,c);
	}

	/**
	 * This function is used in MasterRenderer to render rectangles DO NOT USE!
	 * @param x x position of rectangle
	 * @param y y position of rectangle
	 * @param w width of rectangle
	 * @param h height of rectangle
	 * @param r rotation of rectangle
	 */
	public static void masterRenderRect(float x, float y, float w, float h, float r) {
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

	/**
	 * This function renders a rectangle
	 * @param x x position of rectangle
	 * @param y y position of rectangle
	 * @param w width of rectangle
	 * @param h height of rectangle
	 * @param r rotation of rectangle
	 * @param texID texture ID of rectangle
	 * @param c color of rectangle
	 */
	public static void renderRect(float x, float y, float w, float h, float r, int texID, Color c) {
		glPushMatrix();
		
		float W = w / 2;
		float H = h / 2;

		glTranslatef(x + W, y + H, 0);
		glRotatef(r, 0, 0, 1);
		
		glColor3f(c.r, c.g, c.b);

		if (texID != 0) {
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

		if (texID != 0) {
			glBindTexture(GL_TEXTURE_2D, 0);
		}
		
		glColor3f(1, 1, 1);

		glPopMatrix();
	}

	/**
	 * This function renders a circle
	 * @param x x position of circle
	 * @param y y position of circle
	 * @param r radius of circle
	 * @param verts vertices of circle
	 * @param c color of circle
	 */
	public static void renderCircle(float x, float y, float r, Vector2f[] verts, Color c) {
		glPushMatrix();

		glTranslatef(x, y, 0);
		
		glColor3f(c.r, c.g, c.b);

		glBegin(GL_POLYGON);
		{
			for (Vector2f v:verts) {
				glVertex2f(v.x * r, v.y * r);
			}
		}
		glEnd();
		
		glColor3f(1, 1, 1);

		glPopMatrix();
	}
	
	public static void renderPoly(float x, float y, float r, Vector2f[] verts, Color c) {
		glPushMatrix();

		glTranslatef(x, y, 0);
		glRotatef(r, 0, 0, 1);
		
		glColor3f(c.r, c.g, c.b);

		glBegin(GL_POLYGON);
		{
			for (Vector2f v:verts) {
				glVertex2f(v.x, v.y);
			}
		}
		glEnd();
		
		glColor3f(1, 1, 1);

		glPopMatrix();
	}
}
