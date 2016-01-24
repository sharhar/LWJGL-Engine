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
import engine.sound.SoundManager;
import engine.utils.Loader;
import engine.maths.Sync;

public class Main implements Loop {

	Entity[] ents;
	
	public void run() {
		for(Entity ent:ents) {
			ent.rot(5f);
			MasterRenderer.addEntity(ent);
		}
		
		Sync.sync(60);
	}

	public void stop() {
		SoundManager.destroy();
	}

	public Main() {
		Window window = new Window("Game", 1280, 720);
		Game game = new Game(window, this);

		ModelTexture texture = new ModelTexture(Loader.loadTexture("/test.png"));
		TexturedModel model = new TexturedModel(RectangleModel.rectangle, texture);
		
		ents = new Entity[10];
		for(int i = 0; i < ents.length;i++) {
			ents[i] = new Entity(model, new Vector2f(200 + (i%5)*200, (i/5)*200 + 100), 0, new Vector2f(50, 50));
		}
		
		SoundManager.init();
		
		game.start();
	}

	public static void main(String[] args) {
		new Main();
	}
}
