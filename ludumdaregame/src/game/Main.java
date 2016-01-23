package game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import engine.Game;
import engine.UI.Button;
import engine.UI.DirectLayout;
import engine.UI.UIAction;
import engine.UI.UIImage;
import engine.UI.UIKeyEvent;
import engine.UI.UIMouseEvent;
import engine.UI.WindowUI;
import engine.graphics.BasicRenderer;
import engine.graphics.Color;
import engine.graphics.MasterRenderer;
import engine.graphics.Texture;
import engine.input.KeyInput;
import engine.objects.Sprite;
import engine.sound.Sound;
import engine.sound.SoundManager;
import engine.time.Time;
import engine.utils.SpriteLoader;
import engine.window.Loop;

public class Main implements Loop {
	
	int screen = 0;
	WindowUI menu;
	WindowUI settingsMenu;
	Sound menuMusic;
	
	Sprite stage;
	Sprite player;
	
	float yVel = 0;
	float xVel = 0;
	float speed = 75;
	float gForce = -4f;
	
	public void run() {
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
	}

	public Main() {
		Game.init("Game", 1280, 720, false, "/Icon.png", this);
		
		createUI();
		menuMusic = new Sound("music/DK Theme.wav");
		menuMusic.setLooping(true);
		menuMusic.play();
		
		SoundManager.setUniversalSoundVolume(0);
		
		stage = SpriteLoader.loadSprite("C:\\Users\\wiish\\Git\\LWJGL2DEngine\\ludumdaregame\\res\\sprites\\Stage1", new Vector2f(0, 0), new Vector2f(1280, 720));
		player = SpriteLoader.loadSprite("C:\\Users\\wiish\\Git\\LWJGL2DEngine\\ludumdaregame\\res\\sprites\\Player", new Vector2f(600, 300), new Vector2f(100, 100));
		
		Game.start();
	}
	
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
	
	public static void main(String[] args) {
		new Main();
	}
}
