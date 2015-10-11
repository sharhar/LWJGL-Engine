package game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import engine.Game;
import engine.graphics.BasicRenderer;
import engine.graphics.Color;
import engine.graphics.MasterRenderer;
import engine.input.KeyInput;
import engine.math.physics.Colider;
import engine.sound.Sound;
import engine.window.Loop;
import engine.window.Window;
import game.sprites.Ball;
import game.sprites.Paddel;

public class Main implements Loop{
	
	Paddel paddel1;
	Paddel paddel2;
	Ball ball;
	Sound sound;
	
	int p1Score = 0;
	int p2Score = 0;
	
	float paddelSpeed = 1;
	
	public void run() {
		BasicRenderer.drawString(Window.getWidth()/2-35,  Window.getHeight() - 50, p1Score + " | " + p2Score, 40);
		
		/*
		if(KeyInput.isKeyDown(Keyboard.KEY_W)) {
			paddel1.move(new Vector2f(0, paddelSpeed));
		}
		
		if(KeyInput.isKeyDown(Keyboard.KEY_S)) {
			paddel1.move(new Vector2f(0, -paddelSpeed));
		}
		
		*/
		
		if(KeyInput.isKeyDown(Keyboard.KEY_W)) {
			ball.move(new Vector2f(0, paddelSpeed));
		}
		
		if(KeyInput.isKeyDown(Keyboard.KEY_S)) {
			ball.move(new Vector2f(0, -paddelSpeed));
		}
		
		if(KeyInput.isKeyDown(Keyboard.KEY_UP)) {
			paddel2.move(new Vector2f(0, paddelSpeed));
		}
		
		if(KeyInput.isKeyDown(Keyboard.KEY_DOWN)) {
			paddel2.move(new Vector2f(0, -paddelSpeed));
		}
		
		/*
		
		if(KeyInput.isKeyDown(Keyboard.KEY_A)) {
			paddel1.move(new Vector2f(-paddelSpeed, 0));
		}
		
		if(KeyInput.isKeyDown(Keyboard.KEY_D)) {
			paddel1.move(new Vector2f(paddelSpeed, 0));
		}
		
		*/
		
		if(KeyInput.isKeyDown(Keyboard.KEY_A)) {
			ball.move(new Vector2f(-paddelSpeed, 0));
		}
		
		if(KeyInput.isKeyDown(Keyboard.KEY_D)) {
			ball.move(new Vector2f(paddelSpeed, 0));
		}
		
		if(KeyInput.isKeyDown(Keyboard.KEY_RIGHT)) {
			paddel2.move(new Vector2f(paddelSpeed, 0));
		}
		
		if(KeyInput.isKeyDown(Keyboard.KEY_LEFT)) {
			paddel2.move(new Vector2f(-paddelSpeed, 0));
		}
		
		if(KeyInput.isKeyDown(Keyboard.KEY_1)) {
			paddel2.shape.r += paddelSpeed;
		}
		
		if(KeyInput.isKeyDown(Keyboard.KEY_2)) {
			paddel2.shape.r -= paddelSpeed;
		}
		
		if(Colider.polygonCol(ball.shape, paddel2.shape)) {
			ball.shape.color = new Color(1,0,0);
			paddel2.shape.color = new Color(1,0,0);
		} else {
			ball.shape.color = new Color(1,1,1);
			paddel2.shape.color = new Color(1,1,1);
		}
		
		MasterRenderer.addSprite(paddel1);
		MasterRenderer.addSprite(paddel2);
		MasterRenderer.addSprite(ball);
	}
	
	public Main() {
		Game.init("Game Title", 800, 600,true, "/Icon.png", this);
		
		paddel1 = new Paddel(30, 30, 20, 150);
		paddel2 = new Paddel(Window.getWidth() - 30 - 20, 30, 20, 150);
		ball = new Ball(400, 400, 40);
		sound = new Sound("BM.wav");
		sound.play();
		sound.setVolume(0);
		Game.start();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
