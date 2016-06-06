package engine.window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import engine.input.KeyInput;
import engine.input.Mouse;
import engine.sound.SoundManager;

public class Window {
	
	private static GLFWErrorCallback errorCallback;
	private static GLFWKeyCallback keyCallback;
	private static GLFWWindowSizeCallback sizeCallback;
	private static GLFWCursorPosCallback cursorPosCallback;
	private static GLFWMouseButtonCallback mouseButtonCallback;
	private static GLFWScrollCallback scrollCallback;
	
	public static long window;

	private static int WIDTH;
	private static int HEIGHT;
	
	public static boolean resized = false;
	
	public static void create(String title, int width, int height) {
		create(title,width,height,false,null);
	}
	
	public static void create(String title, int width, int height, boolean resizeable, String iconPath) {
		WIDTH = width;
		HEIGHT = height;

		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

		if (glfwInit() != GLFW_TRUE) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		if (resizeable) {
			glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		} else {
			glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		}

		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			public void invoke(long window, int key, int scancode, int action, int mods) {
				KeyInput.callback(key, action);
			}
		});

		glfwSetWindowSizeCallback(window, sizeCallback = new GLFWWindowSizeCallback() {
			public void invoke(long window, int w, int h) {
				resized = true;
				WIDTH = w;
				HEIGHT = h;
			}
		});

		glfwSetCursorPosCallback(window, cursorPosCallback = new GLFWCursorPosCallback() {
			public void invoke(long window, double xpos, double ypos) {
				Mouse.posCallback((float) xpos, HEIGHT - ((float) ypos));
			}
		});

		glfwSetMouseButtonCallback(window, mouseButtonCallback = new GLFWMouseButtonCallback() {
			public void invoke(long window, int button, int action, int mods) {
				Mouse.buttonCallback(button, action);
			}
		});

		glfwSetScrollCallback(window, scrollCallback = new GLFWScrollCallback() {
			public void invoke(long window, double xoffset, double yoffset) {
				Mouse.scrollCallback((float) xoffset, (float) yoffset);
			}
		});

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

		glfwMakeContextCurrent(window);
		
		glfwSwapInterval(1);
			
		glfwShowWindow(window);

		GL.createCapabilities();
		
		glClearColor(0, 0, 0, 1);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width,  0,  height, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		SoundManager.init();
	}
	
	/**
	 * This function clears the window
	 */
	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	/**
	 * This function returns the window's width
	 * @return the window's width
	 */
	public static int getWidth() {
		return WIDTH;
	}
	
	/**
	 * This function returns the window's height
	 * @return the window's height
	 */
	public static int getHeight() {
		return HEIGHT;
	}
	
	/**
	 * This function checks if the window was closed
	 * @return whether or not the window was closed
	 */
	public static boolean isClosed() {
		return glfwWindowShouldClose(window) == GLFW_TRUE;
	}
	
	/**
	 * This function updates the display
	 */
	public static void update() {
		glfwPollEvents();
		glfwSwapBuffers(window);
	}
	
	/**
	 * This function destroys the window
	 */
	public static void destroy() {
		SoundManager.destroy();
		try {
			glfwDestroyWindow(window);
			keyCallback.release();
			sizeCallback.release();
			cursorPosCallback.release();
			mouseButtonCallback.release();
			scrollCallback.release();
		} finally {
			glfwTerminate();
			errorCallback.release();
		}
	}
}
