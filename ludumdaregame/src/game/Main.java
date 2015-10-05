package game;

import org.lwjgl.input.Keyboard;

import engine.Game;
import engine.graphics.BasicRenderer;
import engine.graphics.MasterRenderer;
import engine.graphics.Texture;
import engine.input.KeyInput;
import engine.objects.Sprite;
import engine.sound.Sound;
import engine.window.Loop;
import engine.window.Window;

public class Main implements Loop{
	
	Sprite test;
	Sound sound;
	float x = 0;
	
	public void run() {
		x++;
		BasicRenderer.drawString(100,  100, "Rotation of image(angles): " + x, 40);
		if(KeyInput.isKeyPressed(Keyboard.KEY_SPACE)) {
			sound.toggle();
		}
		if(KeyInput.isKeyPressed(Keyboard.KEY_UP)) {
			sound.volUp(0.1f);
		}
		if(KeyInput.isKeyPressed(Keyboard.KEY_DOWN)) {
			sound.volDown(0.1f);
		}
		test.rot = x;
		MasterRenderer.addSprite(test);
	}
	
	public Main() {
		Game.init("Game Title", 800, 600, this);
		Window.setIcon("/Icon.png");
		
		test = new Sprite(100, 300, 200, 200, 0, new Texture("/test.png"));
		sound = new Sound("BM.wav");
		sound.play();
		Game.start();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
