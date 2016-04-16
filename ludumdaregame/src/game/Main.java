package game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import engine.Game;
import engine.graphics.MasterRenderer;
import engine.input.KeyInput;
import engine.objects.Sprite;
import engine.objects.SpriteData;
import engine.sound.SoundManager;
import engine.utils.SpriteLoader;
import engine.window.Loop;

public class Main implements Loop {
	
	public static SpriteData circle;
	public static SpriteData square;
	public static SpriteData triangle;
	public static Sprite player;
	
	public static final byte SHAPE_TRIANGLE = 0;
	public static final byte SHAPE_SQUARE = 1;
	public static final byte SHAPE_CIRCLE = 2;
	
	public static byte lastShape = 1;
	public static byte currentShape = 0;
	
	public static float lookAt(Vector2f dif) {
		float m = dif.y/dif.x;
		float  off = 0;
		if(dif.x < 0) {
			off = 180;
		}
		
		float angle = (float) (Math.toDegrees(Math.atan(m)) + off);
		
		if(angle < 0) {
			angle = 360 + angle;
		}
		
		return angle - 90;
	}
	
	public void run() {
		/*
		if(screen == 0) {
			menu.tick();
			menu.render();
			BasicRenderer.drawString(500, 500, "Jungle Battle", 80, Color.white);
		} else if (screen == 1) {
			settingsMenu.tick();
			settingsMenu.render();
			BasicRenderer.drawString(500, 500, "Settings", 80, Color.white);
		} else if (screen == 2) {
			
			player.moveCol(new Vector2f(xVel,yVel));
			
			if(!player.grounded) {
				yVel += gForce*Time.deltaTime;
			} else {
				yVel = 0;
			}
			
			if(KeyInput.isKeyDown(Keyboard.KEY_W) && player.grounded) {
				yVel = 1.7f;
			}
			
			if(KeyInput.isKeyDown(Keyboard.KEY_A)) {
				xVel = -speed*Time.deltaTime;
			} else if(KeyInput.isKeyDown(Keyboard.KEY_D)) {
				xVel = speed*Time.deltaTime;
			} else {
				xVel = 0;
			}
			
			MasterRenderer.addSprite(stage);
			MasterRenderer.addSprite(player);
		}
		*/
		
		if(KeyInput.isKeyPressed(Keyboard.KEY_A)) {
			currentShape -= 1;
		}
		
		if(KeyInput.isKeyPressed(Keyboard.KEY_D)) {
			currentShape += 1;
		}
		
		if(currentShape < 0) {
			currentShape += 3;
		}
		
		if(currentShape >= 3) {
			currentShape -= 3;
		}
		
		if(currentShape != lastShape) {
			if(currentShape == SHAPE_CIRCLE) {
				player.setSpriteData(circle);
			} else if(currentShape == SHAPE_SQUARE) {
				player.setSpriteData(square);
			} else if(currentShape == SHAPE_TRIANGLE) {
				player.setSpriteData(triangle);
			}
			
			lastShape = currentShape;
		}
		
		Vector2f dif = new Vector2f(Mouse.getX() - player.pos.x, Mouse.getY() - player.pos.y);
		float r = lookAt(dif);
		player.shapes.render.r = r;
		
		if(KeyInput.isKeyDown(Keyboard.KEY_W)) {
			float speed = 2;
			dif.normalise();
			dif.scale(speed);
			player.move(dif);
		}
		
		MasterRenderer.addSprite(player);
	}
	
	public Main() {
		Game.init("Game", 1280, 720, false, "/Icon.png", this);
		
		Vector2f scale = new Vector2f(64, 64);
		circle = SpriteLoader.loadSpriteData("res/sprites/Player_Circle", scale);
		square = SpriteLoader.loadSpriteData("res/sprites/Player_Square", scale);
		triangle = SpriteLoader.loadSpriteData("res/sprites/Player_Triangle", scale);
		
		player = new Sprite(triangle, new Vector2f(640, 360));
		
		Game.start();
	}
	
	/*
	public void createUI() {
		menu = new WindowUI();
		settingsMenu = new WindowUI();
		Texture tex = new Texture("/images/MenuButton.png");
		DirectLayout layout = new DirectLayout(new Vector2f(1280, 720));
		
		UIImage bg = new UIImage("/images/BG.png", layout.getBounds(0, 0, 1280, 720));
		
		Button play = new Button(layout.getBounds(50,400,200,60), "Play", 35, tex.ID);
		play.addAction(new UIAction() {
			
			public void mouseActionPreformed(UIMouseEvent m) {
				screen = 2;
			}
			
			public void keyActionPreformed(UIKeyEvent k) {}
			public void actionPreformed() {}
		});
		
		Button settings = new Button(layout.getBounds(50,300,200,60), "Settings", 35, tex.ID);
		settings.addAction(new UIAction() {
			
			public void mouseActionPreformed(UIMouseEvent m) {
				screen = 1;
			}
			
			public void keyActionPreformed(UIKeyEvent k) {}
			public void actionPreformed() {}
		});
		
		Button quit = new Button(layout.getBounds(50,200,200,60), "Quit", 35, tex.ID);
		quit.addAction(new UIAction() {
			
			public void mouseActionPreformed(UIMouseEvent m) {
				Game.close();
			}
			
			public void keyActionPreformed(UIKeyEvent k) {}
			public void actionPreformed() {}
		});
		
		menu.addObject(bg);
		menu.addObject(play);
		menu.addObject(settings);
		menu.addObject(quit);
		
		Button back = new Button(layout.getBounds(50,100,200,60), "Back", 35, tex.ID);
		back.addAction(new UIAction() {
			
			public void mouseActionPreformed(UIMouseEvent m) {
				screen = 0;
			}
			
			public void keyActionPreformed(UIKeyEvent k) {}
			public void actionPreformed() {}
		});
		
		settingsMenu.addObject(bg);
		settingsMenu.addObject(back);
	}
	*/
	
	public static void main(String[] args) {
		new Main();
	}
}
