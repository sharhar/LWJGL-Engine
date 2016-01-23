package engine.shaders;

public class StaticShader extends ShaderProgram{
	
	public static StaticShader basicShader = null;
	
	public static void init() {
		basicShader = new StaticShader();
	}
	
	private static final String VERTEX_FILE = "src/engine/shaders/basicShader.vert";
	private static final String FRAGMENT_FILE = "src/engine/shaders/basicShader.frag";

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}
