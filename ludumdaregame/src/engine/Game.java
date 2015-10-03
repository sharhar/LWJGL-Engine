package engine;

public class Game implements Runnable{
	
	private static Game g = null;
	
	public static String title;
	public static int width, height;
	public static Thread gameThread;
	public static Loop loop;
	
	private Game() {
		
	}
	
	public static void init(String a_title, int a_width, int a_height, Loop a_loop) {
		g = new Game();
		title = a_title;
		width = a_width;
		height = a_height;
		loop = a_loop;
		gameThread = new Thread(g);
	}
	
	public static void start() {
		gameThread.start();
	}
	
	private void init() {
		Window.create(title, width, height);
	}
	
	public void run() {
		init();
		while(!Window.isClosed()) {
			Window.clear();
			loop.run();
			Window.update();
		}
		destroy();
	}
	
	public static void destroy() {
		Window.destroy();
		
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
