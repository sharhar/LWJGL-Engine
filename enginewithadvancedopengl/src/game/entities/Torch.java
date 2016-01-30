package game.entities;

import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.objects.Entity;
import engine.objects.Light;
import engine.shaders.ShaderProgram;
import engine.shaders.StaticShader;

public class Torch extends Entity{

	public Light light;
	
	public Torch(String path, Vector2f position, float rotation, Vector2f scale) {
		super(path, position, rotation, scale);
		light = Light.createDefaultLight(position, 1, 100, new Vector3f(1, 1, 1));
	}
	
	public void renderLight() {
		StaticShader.basicShader.start();
		StaticShader.basicShader.loadLight(light);
		ShaderProgram.stopShaders();
	}
	
	@Override
	public void move(Vector2f move) {
		super.move(move);
		light.pos.x += move.x;
		light.pos.y += move.y;
	}
	
	@Override
	public void destroy() {
		light.destroy();
	}
}
