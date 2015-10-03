package game;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.vector.Vector3f;

import engine.Game;
import engine.graphics.BasicRenderer;
import engine.graphics.Texture;
import engine.objects.Sprite;
import engine.sound.Sound;
import engine.window.Loop;

public class Main implements Loop{
	
	Sprite test;
	Sound sound;
	float x = 0;
	
	public void run() {
		x++;
		BasicRenderer.drawString(100,  100, "This is some score text: " + x, 40);
	}
	
	public Main() {
		Game.init("Game Title", 800, 600, this);
		try {
			AL.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test = new Sprite(100, 100, 200, 200, 0, new Texture("/test.png"));
		sound = new Sound("BM.wav", new Vector3f());
		sound.play();
		Game.start();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
