package engine;

import engine.window.Loop;
import engine.window.Window;

public class Game{
	
	public static String title;
	public static int width, height;
	public static Loop loop;
	
	public static void init(String a_title, int a_width, int a_height, Loop a_loop) {
		title = a_title;
		width = a_width;
		height = a_height;
		loop = a_loop;
		Window.create(title, width, height);
	}
	
	public static void start() {
		run();
	}
	
	public static void run() {
		while(!Window.isClosed()) {
			Window.clear();
			loop.run();
			Window.update();
		}
		destroy();
	}
	
	public static void destroy() {
		Window.destroy();
	}
}
