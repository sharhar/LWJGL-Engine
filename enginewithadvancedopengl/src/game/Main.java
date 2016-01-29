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
import engine.maths.Sync;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.objects.Entity;
import engine.objects.Light;
import engine.shaders.ShaderProgram;
import engine.shaders.StaticShader;
import engine.utils.Loader;

public class Main implements Loop {

	Entity entity;
	Game game;
	Light light;
	
	public void run() {
		MasterRenderer.addEntity(entity);
		
		if(Keyboard.isKeyDown(GLFW.GLFW_KEY_E)) {
			entity.rot(-1f);
		}
		if(Keyboard.isKeyDown(GLFW.GLFW_KEY_Q)) {
			entity.rot(1f);
		}
		
		light.pos = new Vector2f((Mouse.pos.x - (game.getWindow().getWidth()/2))/(game.getWindow().getWidth()/2), (Mouse.pos.y - (game.getWindow().getHeight()/2))/(game.getWindow().getHeight()/2));
		StaticShader.basicShader.start();
		StaticShader.basicShader.loadLight(light, 0);
		ShaderProgram.stopShaders();
		
		Sync.sync(60);
	}

	public void stop() {
		
	}

	public Main() {
		StaticShader.setLights(4);
		
		Window window = new Window("Game", 1280, 720, true, false);
		game = new Game(window, this);
		
		ModelTexture texture = new ModelTexture(Loader.loadTexture("/test.png"));
		TexturedModel model = new TexturedModel(RectangleModel.rectangle, texture);
		
		entity = new Entity(model, new Vector2f(200, 200), 0, new Vector2f(200, 200));
		
		light = new Light(new Vector2f(0.5f, 0.5f), 1, new Vector3f(0, 0, 0));
		StaticShader.basicShader.start();
		StaticShader.basicShader.loadLight(light, 0);
		ShaderProgram.stopShaders();
		
		game.start();
	}

	public static void main(String[] args) {
		new Main();
	}
}
