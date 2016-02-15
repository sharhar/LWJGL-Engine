package game.entities;

import engine.graphics.models.RectangleModel;
import engine.graphics.models.TexturedModel;
import engine.graphics.textures.ModelTexture;
import engine.maths.Vector2f;
import engine.objects.CollisionShapeRect;
import engine.objects.Entity;

public class Paddel extends Entity{

	public Paddel(Vector2f position, float rotation, Vector2f scale) {
		super(new TexturedModel(RectangleModel.rectangle, new ModelTexture(0)), position, rotation, scale, new CollisionShapeRect(position, scale));
	}

}
