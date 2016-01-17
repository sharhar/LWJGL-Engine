package engine.UI;

import org.lwjgl.util.vector.Vector2f;

import engine.window.Window;

public class DirectLayout extends Layout{
	
	public Vector2f screen;
	public DirectLayout(Vector2f screen) {
		this.screen = screen;
	}

	public Bounds getBounds(Bounds bounds) {
		float x = (bounds.x/screen.x)*Window.getWidth();
		float y = (bounds.y/screen.y)*Window.getHeight();
		float w = (bounds.w/screen.x)*Window.getWidth();
		float h = (bounds.h/screen.y)*Window.getHeight();
		
		return new Bounds(x,y,w,h);
	}

	public Bounds getBounds(float x, float y, float w, float h) {
		float X = (x/screen.x)*Window.getWidth();
		float Y = (y/screen.y)*Window.getHeight();
		float W = (w/screen.x)*Window.getWidth();
		float H = (h/screen.y)*Window.getHeight();
	
		return new Bounds(X,Y,W,H);
	}
}
