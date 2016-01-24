package engine.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.graphics.models.RawModel;
import engine.objects.Entity;
import engine.shaders.ShaderProgram;
import engine.shaders.StaticShader;

public class MasterRenderer extends Renderer {
	
	public static Map<RawModel, Map<Integer, List<Entity>>> entities = new HashMap<RawModel, Map<Integer, List<Entity>>>();;
	
	public static void renderScene() {
		StaticShader.basicShader.start();
		render(entities);
		ShaderProgram.stopShaders();
		entities.clear();
	}
	
	public static void addEntity(Entity entity) {
		RawModel raw = entity.getModel().getRawModel();
		Map<Integer, List<Entity>> texMap = entities.get(raw);
		if(texMap != null) {
			List<Entity> ent = texMap.get(entity.getModel().getTexture().getID());
			if(ent != null) {
				ent.add(entity);
			} else {
				List<Entity> ltemp = new ArrayList<Entity>();
				ltemp.add(entity);
				texMap.put(entity.getModel().getTexture().getID(), ltemp);
			}
		} else {
			Map<Integer, List<Entity>> temp = new HashMap<Integer, List<Entity>>();
			List<Entity> ltemp = new ArrayList<Entity>();
			ltemp.add(entity);
			temp.put(entity.getModel().getTexture().getID(), ltemp);
			entities.put(raw, temp);
		}
	}
}
