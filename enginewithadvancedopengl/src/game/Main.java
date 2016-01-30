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
	Torch torch;
	Torch torch2;
	Torch torch3;
	Torch torch4;
	Torch torch5;
	Torch torch6;
	Game game;
	
	public void run() {
		
		if(Keyboard.isKeyDown(GLFW.GLFW_KEY_W)) {
			float off = 10;
			torch.light.range += off;
			torch2.light.range += off;
			torch3.light.range += off;
			torch4.light.range += off;
			torch5.light.range += off;
			torch6.light.range += off;
		}
		
		if(Keyboard.isKeyDown(GLFW.GLFW_KEY_S)) {
			float off = -10;
			torch.light.range += off;
			torch2.light.range += off;
			torch3.light.range += off;
			torch4.light.range += off;
			torch5.light.range += off;
			torch6.light.range += off;
		}
		
		Renderer.addEntity(bg);
		Renderer.addEntity(entity);
		Renderer.addEntity(torch);
		Renderer.addEntity(torch2);
		Renderer.addEntity(torch3);
		Renderer.addEntity(torch4);
		Renderer.addEntity(torch5);
		Renderer.addEntity(torch6);
		
		torch.renderLight();
		torch2.renderLight();
		torch3.renderLight();
		torch4.renderLight();
		torch5.renderLight();
		torch6.renderLight();

		Renderer.renderScene();
		
		Sync.sync(60);
	}

	public void stop() {
		
	}

	public Main() {
		StaticShader.setLights(6);
		
		Window window = new Window("Game", 1280, 720, true, false);
		game = new Game(window, this);
		
		entity = new Entity("/Thing.png", new Vector2f(600, 300), 0, new Vector2f(1400, 1200));
		bg = new Entity("/BG.png", new Vector2f(1280/2, 720/2), 0, new Vector2f(1280, 720));
		
		torch  = new Torch("/Torch.png", new Vector2f( 200, 650), 0, new Vector2f(50, 50));
		torch2 = new Torch("/Torch.png", new Vector2f( 400, 650), 0, new Vector2f(50, 50));
		torch3 = new Torch("/Torch.png", new Vector2f( 600, 650), 0, new Vector2f(50, 50));
		torch4 = new Torch("/Torch.png", new Vector2f( 800, 650), 0, new Vector2f(50, 50));
		torch5 = new Torch("/Torch.png", new Vector2f(1000, 650), 0, new Vector2f(50, 50));
		torch6 = new Torch("/Torch.png", new Vector2f(1200, 650), 0, new Vector2f(50, 50));
		
		torch.light.color  = new Vector3f(1, 0, 0);
		torch2.light.color = new Vector3f(0, 1, 0);
		torch3.light.color = new Vector3f(1, 0, 0);
		torch4.light.color = new Vector3f(0, 1, 0);
		torch5.light.color = new Vector3f(1, 0, 0);
		torch6.light.color = new Vector3f(0, 1, 0);
		
		game.start();
	}

	public static void main(String[] args) {
		new Main();
	}
}
