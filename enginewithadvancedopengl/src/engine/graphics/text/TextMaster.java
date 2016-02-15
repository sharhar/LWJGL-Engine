package engine.graphics.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.utils.Loader;

public class TextMaster {
	
	private static Map<FontType, List<Text>> texts = new HashMap<FontType, List<Text>>();
	
	public static void init(){
		FontShader.init();
	}
	
	public static void render(){
		FontRenderer.render(texts);
	}
	
	public static void loadText(Text text){
		FontType font = text.getFont();
		TextMeshData data = font.loadText(text);
		int vao = Loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<Text> textBatch = texts.get(font);
		if(textBatch == null){
			textBatch = new ArrayList<Text>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}
	
	public static void removeText(Text text){
		List<Text> textBatch = texts.get(text.getFont());
		textBatch.remove(text);
		if(textBatch.isEmpty()){
			texts.remove(texts.get(text.getFont()));
		}
	}
	
	public static void cleanUp(){
		FontShader.current.cleanUp();
	}

}
