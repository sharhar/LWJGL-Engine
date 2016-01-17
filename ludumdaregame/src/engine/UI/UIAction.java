package engine.UI;

public interface UIAction {
	public void actionPreformed();
	public void mouseActionPreformed(UIMouseEvent m);
	public void keyActionPreformed(UIKeyEvent k);
}
