package game;

import engine.Game;
import engine.Loop;
import engine.Window;
import engine.graphics.MasterRenderer;
import engine.graphics.models.RectangleModel;
import engine.graphics.models.TexturedModel;
import engine.graphics.textures.ModelTexture;
import engine.maths.Vector2f;
import engine.objects.Entity;
import engine.shaders.ShaderProgram;
import engine.shaders.StaticShader;
import engine.utils.Loader;

public class Main implements Loop {

	Entity entity;
	
	public void run() {
		StaticShader.basicShader.start();
		MasterRenderer.addEntity(entity);
		ShaderProgram.stopShaders();
		entity.rot(1);
		
		MasterRenderer.renderScene();
	}

	public Main() {
		Window window = new Window("Game", 1280, 720);
		Game game = new Game(window, this);

		ModelTexture texture = new ModelTexture(Loader.loadTexture("/test.png"));
		TexturedModel model = new TexturedModel(RectangleModel.rectangle, texture);
		entity = new Entity(model, new Vector2f(200, 200), 0, new Vector2f(100, 100));
		
		game.start();
	}

	public static void main(String[] args) {
		new Main();
	}
}
