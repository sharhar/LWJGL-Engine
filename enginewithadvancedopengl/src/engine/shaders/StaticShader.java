package engine.shaders;

import java.util.HashMap;

import engine.objects.Light;

public class StaticShader extends ShaderProgram{
	
	public static StaticShader basicShader = null;
	public static int[] light_positions;
	private static HashMap<String, String> consts = new HashMap<String, String>();
	
	public static void init() {
		basicShader = new StaticShader();
		pushAllConstants();
		basicShader.compile(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	private static final String VERTEX_FILE = "src/engine/shaders/basicShader.vert";
	private static final String FRAGMENT_FILE = "src/engine/shaders/basicShader.frag";

	public StaticShader() {
		super();
	}
	
	public static void pushAllConstants() {
		for(String name:consts.keySet()) {
			basicShader.constants.put(name, consts.get(name));
		}
	}
	
	public static void addConstant(String name, String value) {
		consts.put(name, value);
	}
	
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	protected void getAllUniformLocations() {
		super.getUniformLocation("transformationMatrix");
		super.getUniformLocation("projectionMatrix");
		
		for(int i = 0;i < light_positions.length;i++) {
			light_positions[i] = super.getUniformLocationRaw("lightPosition[" + i + "]");
			System.out.println(i + ": " + super.getUniformLocationRaw("lightPosition[" + i + "]"));
		}
	}
	
	public void loadProjectionMatrix(float[] data) {
		super.loadMatrix4f(uniforms.get("projectionMatrix"), data);
	}
	
	public static void setLights(int amount) {
		addConstant("__lightNum__", "" + amount);
		light_positions = new int[amount];
	}
	
	public void loadLight(Light lights, int index) {
		//System.out.println(light_positions[index]);
		super.loadVec2(light_positions[index], lights.pos.x, lights.pos.y);
		//super.loadVec2(uniforms.get("lightPos"), lights.pos.x, lights.pos.y);
	}
	
	public void loadTransformationMatrix(float[] data) {
		super.loadMatrix4f(uniforms.get("transformationMatrix"), data);
	}
}
