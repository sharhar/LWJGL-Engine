package game;

import engine.Game;
import engine.Loop;

public class Main implements Loop{
	
	public void run() {
		
	}
	
	public Main() {
		Game.init("Game Title", 800, 600, this);
		Game.start();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
