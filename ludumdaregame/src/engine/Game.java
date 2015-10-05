package engine;

import engine.graphics.MasterRenderer;
import engine.input.KeyInput;
import engine.window.Loop;
import engine.window.Window;

public class Game{
	
	public static String title;
	public static int width, height;
	public static Loop loop;
	
	public static void init(String a_title, int a_width, int a_height, Loop a_loop) {
		init(a_title, a_width, a_height, false , null, a_loop);
	}
	
	public static void init(String a_title, int a_width, int a_height, boolean resizeable, String path, Loop a_loop) {
		title = a_title;
		width = a_width;
		height = a_height;
		loop = a_loop;
		Window.create(title, width, height, resizeable, path);
	}
	
	public static void start() {
		run();
	}
	
	public static void run() {
		while(!Window.isClosed()) {
			Window.clear();
			KeyInput.update();
			loop.run();
			MasterRenderer.renderScene();
			Window.update();
		}
		destroy();
	}
	
	public static void destroy() {
		Window.destroy();
	}
}
