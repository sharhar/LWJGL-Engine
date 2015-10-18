package game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import engine.Game;
import engine.graphics.BasicRenderer;
import engine.graphics.MasterRenderer;
import engine.input.KeyInput;
import engine.math.Maths;
import engine.math.physics.Colider;
import engine.objects.shapes.Shape;
import engine.sound.Sound;
import engine.window.Loop;
import engine.window.Window;
import game.sprites.Ball;
import game.sprites.Paddel;

public class Main implements Loop {

	Paddel paddel1;
	Paddel paddel2;
	Ball ball;
	Sound sound;

	int p1Score = 0;
	int p2Score = 0;

	float paddelSpeed = 5;

	float ballSpeed = 3;

	Vector2f currentBallSpeed = new Vector2f(ballSpeed, ballSpeed);

	public void run() {
		 input();
		 tick();

		render();
	}

	public void render() {
		BasicRenderer.drawString(Window.getWidth() / 2 - 35, Window.getHeight() - 50, p1Score + " | " + p2Score, 40);
		MasterRenderer.addSprite(paddel1);
		MasterRenderer.addSprite(paddel2);
		MasterRenderer.addSprite(ball);
	}

	public void tick() {
		ball.move(currentBallSpeed);

		if (ball.shape.pos.y - ball.shape.r < 0) {
			currentBallSpeed.y = Math.abs(currentBallSpeed.y);
		} else if (ball.shape.pos.y + ball.shape.r > Window.getHeight()) {
			currentBallSpeed.y = -Math.abs(currentBallSpeed.y);
		}

		if (Colider.shapeShapeCol(ball.shape, paddel1.shape)) {
			currentBallSpeed = getSpeed(currentBallSpeed, paddel1.shape, 1);
			currentBallSpeed.x = Math.abs(currentBallSpeed.x);
		} else if (Colider.shapeShapeCol(ball.shape, paddel2.shape)) {
			currentBallSpeed = getSpeed(currentBallSpeed, paddel1.shape, -1);
			currentBallSpeed.x = -Math.abs(currentBallSpeed.x);
			currentBallSpeed.y = -(currentBallSpeed.y);
		}

		if (ball.shape.pos.x < -20) {
			p2Score++;
			ball.shape.pos = new Vector2f(400, 400);
		} else if (ball.shape.pos.x > Window.getWidth() + 20) {
			p1Score++;
			ball.shape.pos = new Vector2f(400, 400);
		}
	}

	public void input() {
		if (KeyInput.isKeyDown(Keyboard.KEY_W)) {
			paddel1.move(new Vector2f(0, paddelSpeed));
		}

		if (KeyInput.isKeyDown(Keyboard.KEY_S)) {
			paddel1.move(new Vector2f(0, -paddelSpeed));
		}

		if (KeyInput.isKeyDown(Keyboard.KEY_UP)) {
			paddel2.move(new Vector2f(0, paddelSpeed));
		}

		if (KeyInput.isKeyDown(Keyboard.KEY_DOWN)) {
			paddel2.move(new Vector2f(0, -paddelSpeed));
		}

		if (KeyInput.isKeyDown(Keyboard.KEY_A)) {
			paddel1.shape.r += paddelSpeed / 2;
		}

		if (KeyInput.isKeyDown(Keyboard.KEY_D)) {
			paddel1.shape.r -= paddelSpeed / 2;
		}

		if (KeyInput.isKeyDown(Keyboard.KEY_RIGHT)) {
			paddel2.shape.r -= paddelSpeed / 2;
		}

		if (KeyInput.isKeyDown(Keyboard.KEY_LEFT)) {
			paddel2.shape.r += paddelSpeed / 2;
		}

		if (KeyInput.isKeyPressed(Keyboard.KEY_R)) {
			ball.shape.pos = ball.shape.pos = new Vector2f(400, 400);
			currentBallSpeed = new Vector2f(ballSpeed, ballSpeed);
		}

		if (KeyInput.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Game.close();
		}
	}

	public Main() {
		Game.init("Game Title", 800, 600, true, "/Icon.png", this);

		paddel1 = new Paddel(30, Window.getHeight() / 2, 20, 150);
		paddel2 = new Paddel(Window.getWidth() - 30 - 20, Window.getHeight() / 2, 20, 150);

		ball = new Ball(400, 400, 15);
		sound = new Sound("BM.wav");
		sound.play();
		sound.setVolume(0);
		Game.start();
	}

	public static void main(String[] args) {
		new Main();
		
		
	}

	public Vector2f getSpeed(Vector2f speed, Shape s, int x) {
		Vector2f sn = new Vector2f(speed.x, speed.y);
		sn.normalise();
		float off = 0;
		if (x == 1) {
			off = 180;
		}
		Vector2f rn = Maths.rotVec(new Vector2f(x, 0), new Vector2f(), (s.getCalcR() % 180) + off);

		float dot = Vector2f.dot(sn, rn);
		Vector2f dr = new Vector2f(2 * dot * rn.x, 2 * dot * rn.y);

		Vector2f result = new Vector2f(sn.x - dr.x, sn.y - dr.y);
		result.x = result.x * ballSpeed;
		result.y = result.y * ballSpeed;
		return result;
	}
}
