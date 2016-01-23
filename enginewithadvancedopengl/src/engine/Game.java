package engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.opengl.GL11;

import engine.utils.Loader;

public class Game {
	
	private Loop loop;
	private Window window;
	private boolean running = false;
	
	public void setLoop(Loop loop) {
		this.loop = loop;
	}
	
	public Game() {
		
	}
	
	public Game(Window window, Loop loop) {
		init(window, loop);
	}
	
	public void init(Window window, Loop loop) {
		this.loop = loop;
		this.window = window;
	}
	
	public void start() {
		running = true;
		run();
	}
	
	public void run() {
		while(running) {
			glClear(GL_COLOR_BUFFER_BIT);
			loop.run();
			

			glfwSwapBuffers(window.getWindow());
			glfwPollEvents();
			if(window.resized) {
				GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
				window.resized = false;
			}
			if(glfwWindowShouldClose(window.getWindow()) == GLFW_TRUE) {
				close();
			}
		}
		
		destroy();
	}
	
	public void close() {
		running = false;
	}
	
	public void destroy() {
		Loader.cleanUp();
		window.close();
	}
}
