package engine.objects;

import engine.graphics.models.TexturedModel;
import engine.maths.Vector2f;

public class Entity {
	
	private TexturedModel model;
	private Vector2f position;
	private float rotation;
	private Vector2f scale;
	
	public Entity(TexturedModel model, Vector2f position, float rotation, Vector2f scale) {
		this.model = model;
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
}
