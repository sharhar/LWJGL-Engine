package engine.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.graphics.models.RawModel;
import engine.objects.Entity;
import engine.shaders.ShaderProgram;
import engine.shaders.StaticShader;

public class MasterRenderer {
	
	public static Map<String, Map<RawModel, Map<Integer, List<Entity>>>> scenes = new HashMap<String, Map<RawModel, Map<Integer, List<Entity>>>>();
	
	public static void renderScene(String name) {
		StaticShader.basicShader.start();
		Renderer.render(scenes.get(name));
		ShaderProgram.stopShaders();
		scenes.get(name).clear();
	}
	
	public static void renderSceneNoClear(String name) {
		StaticShader.basicShader.start();
		Renderer.render(scenes.get(name));
		ShaderProgram.stopShaders();
	}
	
	public static void addEntity(Entity entity, String scene) {
		if(scenes.get(scene) == null) {
			Map<RawModel, Map<Integer, List<Entity>>> temp = new HashMap<RawModel, Map<Integer, List<Entity>>>();
			scenes.put(scene, temp);
		}
		RawModel raw = entity.getModel().getRawModel();
		Map<Integer, List<Entity>> texMap = scenes.get(scene).get(raw);
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
			scenes.get(scene).put(raw, temp);
		}
	}
}
