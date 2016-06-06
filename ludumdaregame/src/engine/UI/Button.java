package engine.UI;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.BasicRenderer;
import engine.graphics.Color;
import engine.graphics.font.TrueTypeFont;
import engine.input.Mouse;
import engine.math.Maths;

public class Button extends UIObject {

	public Bounds bounds;
	public int tex;
	public UIMouseEvent mouseEvent = new UIMouseEvent(new Vector2f(), 0);
	public UIAction action;
	public String text;
	public float scale = 20;
	private boolean mouseDown = false;
	private Color shade = Color.white;

	public Button(Bounds bounds, String text, int tex) {
		this(bounds, text, 20, tex);
	}

	public Button(Bounds bounds, String text, float textScale, int tex) {
		this.bounds = bounds;
		this.text = text;
		this.tex = tex;
		this.scale = textScale;
	}

	public void render() {
		BasicRenderer.renderRect(bounds.x + bounds.w/2, bounds.y + bounds.h/2, bounds.w, bounds.h, 0, tex, shade);
		BasicRenderer.drawString(bounds.x + bounds.w / 2, bounds.y + (bounds.h - scale) / 2, text, scale,
				TrueTypeFont.ALIGN_CENTER, new Color(0, 0, 0));
	}

	public void tick() {
		Vector2f vect = new Vector2f(Mouse.pos.x, Mouse.pos.y);
		if (Maths.inBounds(bounds, vect)) {
			if (!mouseDown && Mouse.isButtonDown(0)) {
				mouseEvent.state = 1;
				mouseEvent.pos = vect;
				try {
					action.mouseActionPreformed(mouseEvent);
				} catch (NullPointerException e) {
					System.out.println("Action interface enevr specified!");
				}
			}
			
			shade = new Color(0.85f,0.85f,0.85f);
		} else {
			shade = Color.white;
		}
		
		mouseDown = Mouse.isButtonDown(0);
	}

	public void addAction(UIAction action) {
		this.action = action;
	}

	public void masterRender() {

	}
}
