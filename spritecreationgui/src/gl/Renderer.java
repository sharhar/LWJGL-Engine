package gl;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import gl.shapes.Shape;
import input.MouseInput;

public class Renderer {
	
	public static void startEditMode(){
		glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
		glPushMatrix();
	}
	
	public static void stopEditMode() {
		glPopMatrix();
		glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );
	}
	
	public static void renderNextTri(Shape shape) {
		List<Vector2f> v = shape.vecs;
		if(v.size() == 0) {
			return;
		} else if (v.size() == 1){
			glLineWidth(2.5f);
			glBegin(GL_LINES);
			{
				Vector2f pos = MouseInput.getMousePos();
				glVertex3f(v.get(0).x, v.get(0).y,0);
				glVertex3f(pos.x, pos.y,0);
			}
			glEnd();
		} else {
			Vector2f first = v.get(v.size()-2);
			Vector2f second = v.get(v.size()-1);
			Vector2f third = MouseInput.getMousePos();
			
			glBegin(GL_TRIANGLES);
			{
				glVertex2f(first.x, first.y);
				glVertex2f(second.x, second.y);
				glVertex2f(third.x, third.y);
			}
			glEnd();
		}
	}
	
	public static void editRenderShape(Shape shape) {
		
		if(shape == null || !shape.render) {
			return;
		}
		
		
		
		glTranslatef(shape.pos.x, shape.pos.y, 0);
		glRotatef(shape.rot, 0, 0, 1);
		
		Vector2f first = null;
		Vector2f second = null;
		
		glBegin(GL_TRIANGLES);
		{
			for(int i = 0;i < shape.vecs.size();i++) {
				if(i == 0) {
					first = shape.vecs.get(i);
				} else if(i == 1) {
					second = shape.vecs.get(i);
				} else {
					Vector2f v = shape.vecs.get(i);
					
					glVertex2f(first.x, first.y);
					glVertex2f(second.x, second.y);
					glVertex2f(v.x, v.y);
					
					first = second;
					second = v;
				}
			}
		}
		glEnd();
		
		
	}
	
	public static void renderShape(Shape shape) {
		if(shape == null || !shape.render) {
			return;
		}
		
		glPushMatrix();
		
		glTranslatef(shape.pos.x, shape.pos.y, 0);
		glRotatef(shape.rot, 0, 0, 1);
		
		Vector2f first = null;
		Vector2f second = null;
		
		glBegin(GL_TRIANGLES);
		{
			for(int i = 0;i < shape.vecs.size();i++) {
				if(i == 0) {
					first = shape.vecs.get(i);
				} else if(i == 1) {
					second = shape.vecs.get(i);
				} else {
					Vector2f v = shape.vecs.get(i);
					
					glVertex2f(first.x, first.y);
					glVertex2f(second.x, second.y);
					glVertex2f(v.x, v.y);
					
					first = second;
					second = v;
				}
			}
		}
		glEnd();
		
		glPopMatrix();
	}
}
