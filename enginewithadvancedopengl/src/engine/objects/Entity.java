package engine.objects;

import engine.graphics.models.RectangleModel;
import engine.graphics.models.TexturedModel;
import engine.graphics.textures.ModelTexture;
import engine.maths.Vector2f;
import engine.utils.Loader;

public class Entity {
	
	protected TexturedModel model;
	protected Vector2f position;
	protected float rotation;
	protected Vector2f scale;
	
	public Entity(TexturedModel model, Vector2f position, float rotation, Vector2f scale) {
		this.model = model;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public Entity(String texture, Vector2f position, float rotation, Vector2f scale) {
		if(texture != null && !texture.equals("")) {
			this.model = new TexturedModel(RectangleModel.rectangle, new ModelTexture(Loader.loadTexture(texture)));
		} else {
			this.model = new TexturedModel(RectangleModel.rectangle, new ModelTexture(0));
		}
		
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public void move(Vector2f move) {
		position.x += move.x;
		position.y += move.y;
	}
	
	public void rot(float rot) {
		rotation += rot;
	}

	public TexturedModel getModel() {
		return model;
	}

	public Vector2f getPosition() {
		return position;
	}

	public float getRotation() {
		return rotation;
	}

	public Vector2f getScale() {
		return scale;
	}
	
	public void destroy() {
		
	}
}
