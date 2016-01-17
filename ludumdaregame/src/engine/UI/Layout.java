package engine.UI;

public abstract class Layout {
	public abstract Bounds getBounds(float x, float y, float w, float h);
	public abstract Bounds getBounds(Bounds bounds);
}
