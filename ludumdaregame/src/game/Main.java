package game;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import engine.Game;
import engine.graphics.Color;
import engine.graphics.MasterRenderer;
import engine.math.physics.Collider;
import engine.objects.Sprite;
import engine.objects.shapes.CollisionShape;
import engine.objects.shapes.CollisionShapeRect;
import engine.objects.shapes.RenderShapeRect;
import engine.utils.SpriteLoader;
import engine.window.Loop;

public class Main implements Loop {
	
	Sprite test;
	Sprite test2;
	
	public void run() {
		MasterRenderer.addSprite(test);
		MasterRenderer.addSprite(test2);
		
		test.setPos(new Vector2f(Mouse.getX(), Mouse.getY()));
		
		if(Collider.spriteSpriteCol(test, test2)) {
			test.renderShape.color = new Color(1,0,0);
			test2.renderShape.color = new Color(1,0,0);
		} else {
			test.renderShape.color = new Color(1,1,1);
			test2.renderShape.color = new Color(1,1,1);
		}
		
		MasterRenderer.renderScene();
	}

	public Main() {
		Game.init("Game", 800, 600, this);
		
		CollisionShape[] cols = {new CollisionShapeRect(new Vector2f(200,200), new Vector2f(5,5))};
		test = new Sprite(new RenderShapeRect(new Vector2f(200,200), new Vector2f(5,5), new Color(1,1,1)), cols);
		
		test2 = SpriteLoader.loadSprite("C:\\Users\\wiish\\Desktop\\SpriteCreation test\\Test", new Vector2f(500,300),new Vector2f(100, 100));
		
		Game.start();
	}

	public static void main(String[] args) {
		new Main();
	}
}
