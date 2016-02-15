package engine.graphics.text;

import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.shaders.ShaderProgram;

public class FontShader extends ShaderProgram{

	private static final String VERTEX_FILE = "shaders/fontVertex.txt";
	private static final String FRAGMENT_FILE = "shaders/fontFragment.txt";
	
	public static FontShader current;
	
	private int location_colour;
	private int location_translation;
	
	
	public static void init() {
		current = new FontShader();
		current.compile(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public FontShader() {
		super();
	}

	@Override
	protected void getAllUniformLocations() {
		location_colour = super.getUniformLocation("color");
		location_translation = super.getUniformLocation("translation");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	protected void loadColor(Vector3f colour){
		super.loadVec3(location_colour, colour.x, colour.y, colour.z);
	}
	
	protected void loadTranslation(Vector2f translation){
		super.loadVec2(location_translation, translation.x, translation.y);
	}


}
