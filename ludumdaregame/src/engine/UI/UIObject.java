package engine.UI;

import engine.graphics.Renderable;

public abstract class UIObject implements Renderable{
	public Bounds bounds;
	
	public abstract void tick();
}
