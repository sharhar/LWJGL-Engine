package engine.objects;

import engine.graphics.BasicRenderer;
import engine.graphics.Renderable;
import engine.graphics.Texture;

public class Sprite implements Renderable{
	
	public float x, y, width, height, rot;
	public Texture tex;
	
	public Sprite(float x, float y , float w, float h, float r, Texture tex) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.rot = r;
		this.tex = tex;
	}
	
	public void render() {
		BasicRenderer.renderRect(x, y, width, height, rot, tex);
	}
}
