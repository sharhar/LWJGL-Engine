package game;

import org.lwjgl.glfw.GLFW;

import engine.Game;
import engine.Loop;
import engine.Window;
import engine.graphics.Renderer;
import engine.input.Keyboard;
import engine.maths.Sync;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.objects.Entity;
import engine.shaders.StaticShader;
import game.entities.Torch;

public class Main implements Loop {

	Entity entity;
	Entity bg;
	Torch[] torch;
	Game game;
	
	public void run() {
		if(Keyboard.isKeyDown(GLFW.GLFW_KEY_W)) {
			float off = 10;
			torch[0].light.range += off;
			torch[1].light.range += off;
		}
		
		if(Keyboard.isKeyDown(GLFW.GLFW_KEY_S)) {
			float off = -10;
			torch[0].light.range += off;
			torch[1].light.range += off;
		}
		
		Renderer.addEntity(bg);
		Renderer.addEntity(entity);
		Renderer.addEntity(torch[0]);
		Renderer.addEntity(torch[1]);
		
		torch[0].renderLight();
		torch[1].renderLight();

		Renderer.renderScene();
		
		Sync.sync(60);
	}

	public void stop() {
		
	}

	public Main() {
		StaticShader.setLights(6);
		
		Window window = new Window("Game", 1280, 720, true, false);
		game = new Game(window, this);
		
		entity = new Entity("/Thing.png", new Vector2f(600, 300), 0, new Vector2f(300, 300));
		bg = new Entity("/BG.png", new Vector2f(1280/2, 720/2), 0, new Vector2f(1280, 720));
		
		torch = new Torch[2];
		
		torch[0]  = new Torch("/Torch.png", new Vector2f( 200, 650), 0, new Vector2f(50, 50));
		torch[1]  = new Torch("/Torch.png", new Vector2f(1000, 650), 0, new Vector2f(50, 50));
		
		torch[0].light.range = 1000;
		torch[0].light.color =  new Vector3f(1, 0.8f, 0.3f);
		torch[1].light.range = 1000;
		torch[1].light.color =  new Vector3f(1, 0.8f, 0.3f);
		
		game.start();
	}

	public static void main(String[] args) {
		new Main();
	}
}
