package engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.opengl.GL11;

import engine.graphics.MasterRenderer;
import engine.graphics.Renderer;
import engine.shaders.StaticShader;
import engine.sound.SoundManager;
import engine.utils.Loader;

public class Game {
	
	private Loop loop;
	private Window window;
	private boolean running = false;
	private Timer fpsCounter;
	private int fps = 0;
	private boolean printFPS = false;
	
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
		StaticShader.init();
	}
	
	public void printFPS(boolean pf) {
		printFPS = pf;
	}
	
	public void start() {
		running = true;
		Renderer.init(window.getWidth(), window.getHeight());
		SoundManager.init();
		fpsCounter = new Timer();
		fpsCounter.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if(printFPS) {
					System.out.println(fps);
				}
				fps = 0;
			}
		}, 1000, 1000);
		run();
	}
	
	public void run() {
		while(running) {
			glClear(GL_COLOR_BUFFER_BIT);
			loop.run();
			MasterRenderer.renderScene();
			glfwSwapBuffers(window.getWindow());
			glfwPollEvents();
			if(window.resized) {
				GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
				window.resized = false;
			}
			if(glfwWindowShouldClose(window.getWindow()) == GLFW_TRUE) {
				close();
			}
			
			fps++;
		}
		
		destroy();
	}
	
	public void close() {
		running = false;
	}
	
	public void destroy() {
		loop.stop();
		SoundManager.destroy();
		fpsCounter.cancel();
		StaticShader.basicShader.cleanUp();
		Loader.cleanUp();
		window.close();
	}
}
