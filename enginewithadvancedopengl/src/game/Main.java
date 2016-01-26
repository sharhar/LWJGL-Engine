package game;

import org.lwjgl.glfw.GLFW;

import engine.Game;
import engine.Loop;
import engine.Window;
import engine.graphics.MasterRenderer;
import engine.graphics.models.RectangleModel;
import engine.graphics.models.TexturedModel;
import engine.graphics.textures.ModelTexture;
import engine.input.Keyboard;
import engine.input.Mouse;
import engine.maths.Vector2f;
import engine.objects.Entity;
import engine.utils.Loader;
import engine.maths.Sync;

public class Main implements Loop {

	Entity entity;
	Game game;
	
	public void run() {
		MasterRenderer.addEntity(entity);

		entity.getPosition().x = Mouse.pos.x;
		entity.getPosition().y = Mouse.pos.y;
		
		if(Keyboard.isKeyDown(GLFW.GLFW_KEY_E)) {
			entity.rot(-1f);
		}
		if(Keyboard.isKeyDown(GLFW.GLFW_KEY_Q)) {
			entity.rot(1f);
		}
		
		Sync.sync(60);
	}

	public void stop() {
		
	}

	public Main() {
		Window window = new Window("Game", 1280, 720, true, false);
		game = new Game(window, this);

		ModelTexture texture = new ModelTexture(Loader.loadTexture("/test.png"));
		TexturedModel model = new TexturedModel(RectangleModel.rectangle, texture);
		
		entity = new Entity(model, new Vector2f(400, 400), 0, new Vector2f(50, 50));
		
		game.start();
	}

	public static void main(String[] args) {
		new Main();
	}
}
