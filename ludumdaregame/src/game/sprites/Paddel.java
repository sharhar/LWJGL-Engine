package game.sprites;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.Color;
import engine.objects.Sprite;
import engine.objects.shapes.ShapeRect;

public class Paddel extends Sprite{

	public Paddel(float x, float y, float w, float h) {
		super(new ShapeRect(new Vector2f(x,y), new Vector2f(w,h), new Color()));
	}
}
