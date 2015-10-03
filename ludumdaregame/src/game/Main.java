package game;

import engine.Game;
import engine.graphics.BasicRenderer;
import engine.window.Loop;

public class Main implements Loop{
	
	int x = 0;
	
	public void run() {
		BasicRenderer.renderRect(100,  100,  200,  200,  x);
		x++;
	}
	
	public Main() {
		Game.init("Game Title", 800, 600, this);
		Game.start();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
