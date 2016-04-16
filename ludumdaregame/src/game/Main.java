package game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.naming.TimeLimitExceededException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import engine.Game;
import engine.UI.DirectLayout;
import engine.UI.UIImage;
import engine.UI.WindowUI;
import engine.graphics.BasicRenderer;
import engine.graphics.MasterRenderer;
import engine.graphics.Texture;
import engine.input.KeyInput;
import engine.math.physics.Collider;
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
	
	public static Texture explosionGreen;
	public static Texture explosionBlue;
	public static Texture explosionRed;
	
	public static DirectLayout layout;
	public static WindowUI gameUI;
	
	public static Sprite player;
	
	public static Random rand = new Random();
	
	class Shot {
		public Sprite sprite;
		public Vector2f speed;
		public byte type;
		public boolean dead = false;
		
		public Shot(Sprite sprite, byte type, Vector2f speed) {
			this.sprite = sprite;
			this.speed = speed;
			this.type = type;
		}
		
		public void update() {
			sprite.move(speed);
		}
	}
	
	class Enemy {
		public Sprite sprite;
		public Vector2f dif;
		public static final float speed = 2;
		public boolean dead = false;
		
		public byte type;
		
		public Enemy(Sprite sprite, byte type, Vector2f playerPos) {
			this.sprite = sprite;
			this.type = type;
			
			dif = new Vector2f(this.sprite.pos.x - playerPos.x, this.sprite.pos.y - playerPos.y);
			try {
				dif.normalise();
				dif.scale(-speed);
			} catch (java.lang.IllegalStateException e) {
				
			}
		}
		
		public void setDif(Vector2f playerPos, int currentShape) {
			if(currentShape == type) {
				return;
			}
			dif = new Vector2f(this.sprite.pos.x - playerPos.x, this.sprite.pos.y - playerPos.y);
			try {
				dif.normalise();
				dif.scale(-speed);
			} catch (java.lang.IllegalStateException e) {
				
			}
		}
		
		public void move(List<Shot> shots) {
			
			sprite.move(dif);
			
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
			sprite.setR(angle);
			
			if(sprite.pos.x < -50 || sprite.pos.y < -50 || sprite.pos.x > 1330 || sprite.pos.y > 770) {
				dead = true;
			}
			
			if(!dead) {
				for(Shot s:shots) {
					if(!s.dead) {
						if(Collider.spriteSpriteCol(s.sprite, sprite)) {
							if(s.type != type) {
								dead = true;
							}
							
							s.dead = true;
						}
					}
				}
			}
		}
		
		public boolean isDead() {
			return dead;
		}
	}
	
	public static List<Shot> shots;
	public static List<Enemy> enemies;
	public static List<Integer> removes = new ArrayList<Integer>();
	
	public static final byte SHAPE_TRIANGLE = 0;
	public static final byte SHAPE_SQUARE = 1;
	public static final byte SHAPE_CIRCLE = 2;
	
	public static byte lastShape = 1;
	public static byte currentShape = 0;
	
	public static final float SHOT_SPEED = 15;
	public float shotTime = 0;
	public float enemyTime = 3;
	public float enemyWait = 0.5f;
	
	public static boolean exploding = false;
	public static Texture explosion = null;
	public static SpriteData setSprite = null;
	
	public static float expLen = 0.1f;
	public static float expTime = 0;
	public static float expSize = 0.2f;
	
	public void startExplosion(Texture exp, SpriteData dat) {
		exploding = true;
		explosion = exp;
		expTime = 0;
		setSprite = dat;
	}
	
	public void explosion() {
		if(exploding) {
			float a = 1;
			float s = 1;
			
			float time = expTime;
			
			if(expTime > expLen/2) {
				if(setSprite != null) {
					player.setSpriteData(setSprite);
					setSprite = null;
				}
				
				time = expLen - time;
			}
			
			float percent = time/(expLen/2);
			
			s += expSize * percent;
			
			BasicRenderer.renderRect(player.pos.x, player.pos.y, 128 * s, 128 * s, 0, explosion.ID, a);
			
			expTime += Time.deltaTime;
			if(expTime > expLen) {
				exploding = false;
			}
		}
	}
	
	public void shoot(Vector2f dir) {
		dir.normalise();
		dir.scale(SHOT_SPEED);
		if(currentShape == SHAPE_CIRCLE) {
			shots.add(new Shot(new Sprite(new SpriteData(shotCircle), new Vector2f(player.pos), player.shapes.render.r), SHAPE_CIRCLE, new Vector2f(dir)));
		} else if(currentShape == SHAPE_SQUARE) {
			shots.add(new Shot(new Sprite(new SpriteData(shotSquare), new Vector2f(player.pos), player.shapes.render.r), SHAPE_SQUARE, new Vector2f(dir)));
		} else if(currentShape == SHAPE_TRIANGLE) {
			shots.add(new Shot(new Sprite(new SpriteData(shotTriangle), new Vector2f(player.pos), player.shapes.render.r), SHAPE_TRIANGLE, new Vector2f(dir)));
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
				gameUI.objects.get(0).bounds = layout.getBounds(1280 - 180, 50, 30, 30);
				gameUI.objects.get(1).bounds = layout.getBounds(1280 - 130, 50, 30, 30);
				gameUI.objects.get(2).bounds = layout.getBounds(1280 - 80, 50, 50, 50);
				startExplosion(explosionRed, circle);
			} else if(currentShape == SHAPE_SQUARE) {
				gameUI.objects.get(0).bounds = layout.getBounds(1280 - 180, 50, 30, 30);
				gameUI.objects.get(1).bounds = layout.getBounds(1280 - 130, 50, 50, 50);
				gameUI.objects.get(2).bounds = layout.getBounds(1280 - 80, 50, 30, 30);
				startExplosion(explosionBlue, square);
			} else if(currentShape == SHAPE_TRIANGLE) {
				gameUI.objects.get(0).bounds = layout.getBounds(1280 - 180, 50, 50, 50);
				gameUI.objects.get(1).bounds = layout.getBounds(1280 - 130, 50, 30, 30);
				gameUI.objects.get(2).bounds = layout.getBounds(1280 - 80, 50, 30, 30);
				startExplosion(explosionGreen, triangle);
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
		
		if(Mouse.isButtonDown(0)) {
			if(shotTime <= 0) {
				shoot(dif);
				shotTime = 0.1f;
			} else {
				shotTime -= Time.deltaTime;
			}
		} else {
			shotTime = 0;
		}
		
		for(Enemy e:enemies) {
			if(e.type != currentShape) {
				
			}
		}
	}
	
	public void spawnEnemy() {
		byte type = (byte) rand.nextInt(3);
		
		SpriteData data = null;
		if(type == SHAPE_CIRCLE) {
			data = new SpriteData(circle);
		} else if(type == SHAPE_SQUARE) {
			data = new SpriteData(square);
		} else if(type == SHAPE_TRIANGLE) {
			data = new SpriteData(triangle);
		}
		
		int side = rand.nextInt(4);
		
		int x = rand.nextInt(1280);
		int y = rand.nextInt(720);
		
		int xf = 0;
		int yf = 0;
		
		if(side == 0) {
			xf = 0;
			yf = y;
		} else if(side == 1) {
			xf = 1280;
			yf = y;
		} else if(side == 2) {
			xf = x;
			yf = 0;
		} else if(side == 3) {
			xf = x;
			yf = 720;
		}
		
		Sprite s = new Sprite(data, new Vector2f(xf, yf));
		Enemy e = new Enemy(s, type, player.pos);
		enemies.add(e);
	}
	
	public void update() {
		enemyTime -= Time.deltaTime;
		if(enemyTime <= 0) {
			spawnEnemy();
			
			enemyTime = enemyWait;
		}
		
		enemyWait -= Time.deltaTime/1000;
		
		removes.clear();
		
		for(int i = 0;i < shots.size();i++) {
			Shot s = shots.get(i);
			s.update();
			if(s.dead || s.sprite.pos.x < 0 || s.sprite.pos.y < 0 || s.sprite.pos.x > 1280 || s.sprite.pos.y > 720) {
				removes.add(i);
			}
		}
		
		int off = 0;
		for(int r:removes) {
			shots.remove(r + off);
			off -= 1;
		}
		
		removes.clear();
		for(int i = 0;i < enemies.size();i++) {
			Enemy e = enemies.get(i);
			e.setDif(player.pos, currentShape);
			e.move(shots);
			if(e.isDead()) {
				removes.add(i);
			}
		}
		
		off = 0;
		for(int r:removes) {
			enemies.remove(r + off);
			off -= 1;
		}
	}
	
	public void render() {
		for(Shot s:shots) {
			MasterRenderer.addSprite(s.sprite);
		}
		MasterRenderer.renderScene();
		
		for(Enemy e:enemies) {
			MasterRenderer.addSprite(e.sprite);
		}
		
		MasterRenderer.addSprite(player);
		
		MasterRenderer.renderScene();
	}
	
	public void run() {
		playerControl();
		update();
		render();
		
		explosion();
		
		gameUI.render();
		
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
		enemies = new ArrayList<Enemy>();
		
		gameUI = new WindowUI();
		layout = new DirectLayout(new Vector2f(1280, 720));
		UIImage uits = new UIImage(triangle.render.ID, 	layout.getBounds(1280 - 180, 50, 30, 30));
		UIImage uiss = new UIImage(square.render.ID, 	layout.getBounds(1280 - 130, 50, 30, 30));
		UIImage uics = new UIImage(circle.render.ID, 	layout.getBounds(1280 - 80, 50, 30, 30));
		
		gameUI.addObject(uits);
		gameUI.addObject(uiss);
		gameUI.addObject(uics);
		
		player = new Sprite(triangle, new Vector2f(640, 360));
		
		loadExplosions();
		
		Game.start();
	}
	
	public void loadExplosions() {
		BufferedImage image = null;
		
		int amp = 3;
		
		try {
			image = ImageIO.read(Main.class.getResourceAsStream("/images/Explosion.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BufferedImage blueIMG = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for(int i = 0;i < blueIMG.getWidth();i++) {
			for(int j = 0;j < blueIMG.getHeight();j++) {
				int color = image.getRGB(i, j);
				int a = color & 0xff000000;
				int r = (((color & 0xff0000) >> 16)/amp) << 16;
				int g = (((color & 0xff00) >> 8)/amp) << 8;
				int b = (((color & 0xff))/1);
				blueIMG.setRGB(i, j, a | r | g | b);
			}
		}
		
		BufferedImage greenIMG = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for(int i = 0;i < greenIMG.getWidth();i++) {
			for(int j = 0;j < greenIMG.getHeight();j++) {
				int color = image.getRGB(i, j);
				int a = color & 0xff000000;
				int r = (((color & 0xff0000) >> 16)/amp) << 16;
				int g = (((color & 0xff00) >> 8)/1) << 8;
				int b = (((color & 0xff))/amp);
				greenIMG.setRGB(i, j, a | r | g | b);
			}
		}
		
		BufferedImage redIMG = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for(int i = 0;i < redIMG.getWidth();i++) {
			for(int j = 0;j < redIMG.getHeight();j++) {
				int color = image.getRGB(i, j);
				int a = color & 0xff000000;
				int r = (((color & 0xff0000) >> 16)/1) << 16;
				int g = (((color & 0xff00) >> 8)/amp) << 8;
				int b = (((color & 0xff))/amp);
				redIMG.setRGB(i, j, a | r | g | b);
			}
		}
		
		explosionBlue = new Texture(blueIMG);
		explosionGreen = new Texture(greenIMG);
		explosionRed = new Texture(redIMG);
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
