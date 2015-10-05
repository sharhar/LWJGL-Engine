package game;

import org.lwjgl.input.Keyboard;

import engine.Game;
import engine.graphics.BasicRenderer;
import engine.graphics.MasterRenderer;
import engine.graphics.Texture;
import engine.input.KeyInput;
import engine.objects.Sprite;
import engine.sound.Sound;
import engine.time.Clock;
import engine.window.Loop;

public class Main implements Loop{
	
	Sprite test;
	Sound sound;
	Clock clock;
	Clock clock2;
	float x = 0;
	
	public void run() {
		x++;
		BasicRenderer.drawString(100,  100, "Rotation of image(angles): " + x, 40);
		BasicRenderer.drawString(70,  60, "Amount of time passed since start of app: " + clock.getTimePassed().getAsSeconds(), 30);
		BasicRenderer.drawString(70,  20, "Have 5 seconds passed yet? " + clock2.isDone(), 30);
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
		Game.init("Game Title", 800, 600,true, "/Icon.png", this);
		
		test = new Sprite(100, 300, 200, 200, 0, new Texture("/test.png"));
		sound = new Sound("BM.wav");
		sound.play();
		clock = new Clock();
		clock2 = new Clock();
		clock.startCountUp();
		clock2.startCountDown(5);
		Game.start();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
