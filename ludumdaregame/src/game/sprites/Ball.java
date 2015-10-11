package game.sprites;

import org.lwjgl.util.vector.Vector2f;

import engine.graphics.Color;
import engine.objects.Sprite;
import engine.objects.shapes.ShapeCircle;

public class Ball extends Sprite{

	public Ball(float x, float y, float r) {
		super(new ShapeCircle(new Vector2f(x,y), r, (int) r * 2, new Color()));
	}

}
