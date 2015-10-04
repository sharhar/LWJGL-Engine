package game;

import engine.Game;
import engine.graphics.BasicRenderer;
import engine.graphics.Texture;
import engine.objects.Sprite;
import engine.window.Loop;
import engine.window.Window;

public class Main implements Loop{
	
	Sprite test;
	float x = 0;
	
	public void run() {
		x++;
		BasicRenderer.drawString(100,  100, "Rotation of image(angles): " + x, 40);
		test.rot = x;
		test.render();
	}
	
	public Main() {
		Game.init("Game Title", 800, 600, this);
		Window.setIcon("/Icon.png");
		
		test = new Sprite(100, 300, 200, 200, 0, new Texture("/test.png"));
		Game.start();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
