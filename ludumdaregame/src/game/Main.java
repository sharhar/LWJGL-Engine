package game;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import engine.Game;
import engine.graphics.MasterRenderer;
import engine.input.KeyInput;
import engine.objects.Sprite;
import engine.objects.SpriteData;
import engine.time.Time;
import engine.utils.SpriteLoader;
import engine.window.Loop;

public class Main implements Loop {
	
	public static SpriteData circle;
	public static SpriteData square;
	public static SpriteData triangle;
	
	public static SpriteData shotCircle;
	public static SpriteData shotSquare;
	public static SpriteData shotTriangle;
	
	public static Sprite player;
	
	class Shot {
		public Sprite sprite;
		public Vector2f speed;
		
		public Shot(Sprite sprite, Vector2f speed) {
			this.sprite = sprite;
			this.speed = speed;
		}
		
		public void update() {
			sprite.move(speed);
		}
	}
	
	public static List<Shot> shots;
	
	public static final byte SHAPE_TRIANGLE = 0;
	public static final byte SHAPE_SQUARE = 1;
	public static final byte SHAPE_CIRCLE = 2;
	
	public static byte lastShape = 1;
	public static byte currentShape = 0;
	
	public static final float SHOT_SPEED = 15;
	public float shotTime = 0;
	
	public void shoot(Vector2f dir) {
		dir.normalise();
		dir.scale(SHOT_SPEED);
		if(currentShape == SHAPE_CIRCLE) {
			shots.add(new Shot(new Sprite(new SpriteData(shotCircle), new Vector2f(player.pos), player.shapes.render.r), new Vector2f(dir)));
		} else if(currentShape == SHAPE_SQUARE) {
			shots.add(new Shot(new Sprite(new SpriteData(shotSquare), new Vector2f(player.pos), player.shapes.render.r), new Vector2f(dir)));
		} else if(currentShape == SHAPE_TRIANGLE) {
			shots.add(new Shot(new Sprite(new SpriteData(shotTriangle), new Vector2f(player.pos), player.shapes.render.r), new Vector2f(dir)));
		}
	}
	
	public void playerControl() {
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
		float m = dif.y/dif.x;
		float  off = 0;
		if(dif.x < 0) {
			off = 180;
		}
		
		float angle = (float) (Math.toDegrees(Math.atan(m)) + off);
		
		if(angle < 0) {
			angle = 360 + angle;
		}
		
		angle -= 90;
		player.setR(angle);
		
		if(KeyInput.isKeyDown(Keyboard.KEY_W)) {
			float speed = 2;
			dif.normalise();
			dif.scale(speed);
			player.move(dif);
		}
		
		if(KeyInput.isKeyDown(Keyboard.KEY_SPACE)) {
			if(shotTime <= 0) {
				shoot(dif);
				shotTime = 0.1f;
			} else {
				shotTime -= Time.deltaTime;
			}
		} else {
			shotTime = 0;
		}
	}
	
	public void run() {
		playerControl();
		
		List<Integer> removes = new ArrayList<Integer>();
		
		for(int i = 0;i < shots.size();i++) {
			Shot s = shots.get(i);
			s.update();
			if(s.sprite.pos.x < 0 || s.sprite.pos.y < 0 || s.sprite.pos.x > 1280 || s.sprite.pos.y > 720) {
				removes.add(i);
			}
		}
		
		for(int r:removes) {
			shots.remove(r);
		}
		
		for(Shot s:shots) {
			MasterRenderer.addSprite(s.sprite);
		}
		
		MasterRenderer.addSprite(player);
	}
	
	public Main() {
		Game.init("Game", 1280, 720, false, "/Icon.png", this);
		
		Vector2f scale = new Vector2f(64, 64);
		circle = SpriteLoader.loadSpriteData("res/sprites/Player_Circle", scale);
		square = SpriteLoader.loadSpriteData("res/sprites/Player_Square", scale);
		triangle = SpriteLoader.loadSpriteData("res/sprites/Player_Triangle", scale);
		
		Vector2f shotScale = new Vector2f(32, 32);
		shotCircle = SpriteLoader.loadSpriteData("res/sprites/Shot_Circle", shotScale);
		shotSquare = SpriteLoader.loadSpriteData("res/sprites/Shot_Square", shotScale);
		shotTriangle = SpriteLoader.loadSpriteData("res/sprites/Shot_Triangle", shotScale);
		
		shots = new ArrayList<Shot>();
		
		player = new Sprite(triangle, new Vector2f(640, 360));
		
		Game.start();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
