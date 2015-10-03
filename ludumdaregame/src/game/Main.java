package game;

import engine.Game;
import engine.graphics.BasicRenderer;
import engine.graphics.Texture;
import engine.objects.Sprite;
import engine.window.Loop;

public class Main implements Loop{
	
	Sprite test;
	
	float x = 0;
	
	public void run() {
		x++;
		BasicRenderer.drawString(100,  100, "This is some score text: " + x, 40);
	}
	
	public Main() {
		Game.init("Game Title", 800, 600, this);
		
		test = new Sprite(100, 100, 200, 200, 0, new Texture("/test.png"));
		
		Game.start();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
