/**
 * A class that renders using a batch rendering technique
 * @author Sharhar
 */
package engine.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import engine.objects.Sprite;

public class MasterRenderer {
	
	public static Map<Integer, List<Renderable>> renderables = new HashMap<Integer, List<Renderable>>();
	
	/**
	 * This function renders the scene
	 */
	public static void renderScene() {
		for(int ID:renderables.keySet()) {
			List<Renderable> batch = renderables.get(ID);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, ID);
			for(Renderable renderable:batch) {
				renderable.masterRender();
			}
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}
		
		renderables.clear();
	}
	
	/**
	 * This function adds a sprite object to the sprite HashMap
	 * @param sprite the object to be added
	 */
	public static void addSprite(Sprite sprite) {
		addRenderable(sprite.shape.ID, sprite);
	}
	
	/**
	 * This function adds a Renderable to the sprite HashMap 
	 * @param ID texture ID
	 * @param renderable Renderable object
	 */
	public static void addRenderable(int ID, Renderable renderable) {
		List<Renderable> batch = renderables.get(ID);
		if(batch != null) {
			batch.add(renderable);
		} else {
			List<Renderable> newBatch = new ArrayList<Renderable>();
			newBatch.add(renderable);
			renderables.put(ID, newBatch);
		}
	}
}
