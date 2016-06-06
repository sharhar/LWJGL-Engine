
package engine;

import engine.graphics.MasterRenderer;
import engine.input.KeyInput;
import engine.objects.Sprite;
import engine.time.Time;
import engine.window.Loop;
import engine.window.Window;

import static org.lwjgl.glfw.GLFW.*;

/**
 * This class is used to manage the game
 * @author Sharhar
 */
public class Game{
	
	public static String title;
	public static int width, height;
	public static Loop loop;
	
	/**
	 * This function initializes the game
	 * @param a_title title of window
	 * @param a_width width of window
	 * @param a_height height of window
	 * @param a_loop game loop instance
	 */
	public static void init(String a_title, int a_width, int a_height, Loop a_loop) {
		init(a_title, a_width, a_height, false , null, a_loop);
	}
	
	/**
	 * This function initializes the game
	 * @param a_title title of window
	 * @param a_width width of window
	 * @param a_height height of window
	 * @param resizeable whether the window is allowed to resize
	 * @param path path of window icon
	 * @param a_loop game loop instance
	 */
	public static void init(String a_title, int a_width, int a_height, boolean resizeable, String path, Loop a_loop) {
		title = a_title;
		width = a_width;
		height = a_height;
		loop = a_loop;
		Window.create(title, width, height, resizeable, path);
		Sprite.array(500000);
	}
	
	
	/**
	 * This function starts the game
	 */
	public static void start() {
		run();
	}
	
	/**
	 * This function closes the game
	 */
	public static void close() {
		glfwSetWindowShouldClose(Window.window, GLFW_TRUE);
	}
	
	/**
	 * This is the main loop of the game
	 */
	public static void run() {
		Time.init();
		while(!Window.isClosed()) {
			Window.clear();
			Time.tick();
			loop.run();
			MasterRenderer.renderScene();
			Window.update();
		}
		destroy();
	}
	
	/**
	 * This function is called to stop the game and delete any memory objects
	 */
	public static void destroy() {
		Window.destroy();
	}
}
