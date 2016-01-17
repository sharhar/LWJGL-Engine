package engine.UI;

import engine.graphics.BasicRenderer;
import engine.graphics.Color;
import engine.graphics.Texture;

public class UIImage extends UIObject{

	int tex = 0;
	public Bounds bounds;
	
	public UIImage(String path, Bounds bounds) {
		this(new Texture(path),bounds);
	}
	
	public UIImage(Texture texture, Bounds bounds) {
		this(texture.ID,bounds);
	}
	
	public UIImage(int tex, Bounds bounds) {
		this.tex = tex;
		this.bounds = bounds;
	}
	
	public void render() {
		BasicRenderer.renderRect(bounds.x, bounds.y, bounds.w, bounds.h, 0, tex, Color.white);
	}

	public void masterRender() {
		
	}

	public void tick() {
		
	}

}
