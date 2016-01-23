package game;

import engine.Game;
import engine.Loop;
import engine.Window;
import engine.graphics.Renderer;
import engine.graphics.models.RawModel;
import engine.shaders.ShaderProgram;
import engine.shaders.StaticShader;
import engine.utils.Loader;

public class Main implements Loop {

	RawModel model = null;

	public void run() {
		StaticShader.basicShader.start();
		Renderer.render(model);
		ShaderProgram.stopShaders();
	}

	public Main() {
		Window window = new Window("Game", 1280, 720);
		Game game = new Game(window, this);

		float[] vertices = { 
				-0.5f, 0.5f,
				-0.5f, -0.5f, 
				0.5f, -0.5f,
				0.5f, 0.5f
				};
		
		int[] index = {
				0 , 1, 3,
				3 , 1, 2
		};

		model = Loader.loadToVAO(vertices, index);

		game.start();
	}

	public static void main(String[] args) {
		new Main();
	}
}
