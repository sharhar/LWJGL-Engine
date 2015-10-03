package engine.graphics;

import static org.lwjgl.opengl.GL11.*;

public class BasicRenderer {
	
	public static void renderRect(float x , float y, float w , float h, float r) {
		glPushMatrix();
		
		float W = w/2;
		float H = h/2;
		
		glTranslatef(x + W, y + H, 0);
		glRotatef(r, 0, 0, 1);
		
		glBegin(GL_QUADS);
		{
			glVertex2f(-W, -H);
			glVertex2f(-W, H);
			glVertex2f(W, H);
			glVertex2f(W, -H);
		}
		glEnd();
		
		glPopMatrix();
	}
	
}
