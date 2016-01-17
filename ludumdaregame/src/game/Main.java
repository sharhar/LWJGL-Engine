package game;

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
import engine.graphics.Texture;
import engine.sound.Sound;
import engine.sound.SoundManager;
import engine.window.Loop;

public class Main implements Loop {
	
	int screen = 0;
	WindowUI menu;
	WindowUI settingsMenu;
	Sound menuMusic;
	
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
			
		}
	}

	public Main() {
		Game.init("Game", 1280, 720, false, "/Icon.png", this);
		
		createUI();
		menuMusic = new Sound("music/DK Theme.wav");
		menuMusic.setLooping(true);
		menuMusic.play();
		
		SoundManager.setUniversalSoundVolume(0);
		
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
