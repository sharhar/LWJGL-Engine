package game;

import static org.lwjgl.glfw.GLFW.*;

import java.io.File;

import engine.Game;
import engine.Loop;
import engine.Window;
import engine.graphics.Renderer;
import engine.graphics.text.FontType;
import engine.graphics.text.Text;
import engine.graphics.text.TextMaster;
import engine.input.Keyboard;
import engine.maths.CollisionMath;
import engine.maths.Sync;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.objects.Entity;
import engine.objects.Light;
import engine.shaders.ShaderProgram;
import engine.shaders.StaticShader;
import engine.utils.Loader;
import game.entities.Paddel;

public class Main implements Loop {

	Entity bg;
	Entity ball;
	Light light;
	Game game;
	Paddel p1;
	Paddel p2;
	FontType arial;
	
	float speed = 17f;
	Vector2f bSpeed = new Vector2f(1, 1);
	int p1Score = 0;
	int p2Score = 0;
	
	public void run() {
		if(Keyboard.isKeyDown(GLFW_KEY_W)) {
			p1.move(new Vector2f(0, speed));
		}
		
		if(Keyboard.isKeyDown(GLFW_KEY_S)) {
			p1.move(new Vector2f(0, -speed));
		}

		if(Keyboard.isKeyDown(GLFW_KEY_UP)) {
			p2.move(new Vector2f(0, speed));
		}
		
		if(Keyboard.isKeyDown(GLFW_KEY_DOWN)) {
			p2.move(new Vector2f(0, -speed));
		}
		
		if(ball.position.y > 720-25f/2 || ball.position.y < 25f/2) {
			bSpeed.y = -bSpeed.y;
		}
		
		if(CollisionMath.entityCol(p1, ball)) {
			bSpeed.x = Math.abs(bSpeed.x);
			bSpeed = bSpeed.add(calcEnglish(ball, p1));
		}
		if(CollisionMath.entityCol(p2, ball)) {
			bSpeed.x = -Math.abs(bSpeed.x);
			bSpeed = bSpeed.add(calcEnglish(ball, p2));
		}
		
		ball.move(bSpeed.multiply(7));
		light.pos = ball.position;
		
		if(p1.getPosition().y > 720-75) {
			p1.getPosition().y = 720-75;
			p1.shape.pos.y = 720-150;
		} else if(p1.getPosition().y < 75) {
			p1.getPosition().y = 75;
			p1.shape.pos.y = 0;
		}
		
		if(p2.getPosition().y > 720-75) {
			p2.getPosition().y = 720-75;
			p2.shape.pos.y = 720-150;
		} else if(p2.getPosition().y < 75) {
			p2.getPosition().y = 75;
			p2.shape.pos.y = 0;
		}
		
		if(ball.position.x < 0) {
			p2Score++;
			ball.move(new Vector2f(600, 0));
		}
		if(ball.position.x > 1280) {
			p1Score++;
			ball.move(new Vector2f(-600, 0));
		}
		
		if(Keyboard.isKeyPressed(GLFW_KEY_D)) {
			p1Score--;
			bSpeed.y = 0;
		}
		
		if(Keyboard.isKeyPressed(GLFW_KEY_RIGHT)) {
			p2Score--;
			bSpeed.y = 0;
		}
		
		StaticShader.basicShader.start();
		StaticShader.basicShader.loadLight(light);
		ShaderProgram.stopShaders();
		
		Renderer.addEntity(bg);
		Renderer.addEntity(ball);
		Renderer.addEntity(p1);
		Renderer.addEntity(p2);
		Renderer.renderScene();
		
		Text tempText = new Text(p1Score + " | " + p2Score, 3f, arial, new Vector2f(0, 0.02f), 1f, true);
		tempText.setColor(1, 1, 1);
		TextMaster.render();
		tempText.remove();
		
		Sync.sync(60);
	}
	
	public Vector2f calcEnglish(Entity ball, Entity paddel) {
		float result = 0;
		float dif = ball.position.y - paddel.position.y;
		result = dif/100f;
		return new Vector2f(0, result);
	}

	public void stop() {
		
	}

	public Main() {
		StaticShader.setLights(1);
		
		Window window = new Window("Game", 1280, 720, true, false);
		game = new Game(window, this);
		
		TextMaster.init();
		arial = new FontType(Loader.loadTexture("/niceArial.png"), new File("C:\\Users\\wiish\\Git\\LWJGL2DEngine\\enginewithadvancedopengl\\res\\niceArial.fnt"));
		
		bg = new Entity("/BlackBG.png", new Vector2f(1280/2, 720/2), 0, new Vector2f(1280, 720), null);
		ball = new Paddel(new Vector2f(300, 300), 0, new Vector2f(25, 25));
		p1 = new Paddel(new Vector2f(115, 100), 0, new Vector2f(30, 150));
		p2 = new Paddel(new Vector2f(1165, 100), 0, new Vector2f(30, 150));
		
		light = Light.createDefaultLight(new Vector2f(300, 300), 200, 600, new Vector3f(1, 1, 1));
		
		game.start();
	}

	public static void main(String[] args) {
		new Main();
	}
}
