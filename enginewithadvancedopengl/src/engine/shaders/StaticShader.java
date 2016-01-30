package engine.shaders;

import java.util.HashMap;

import engine.objects.Light;

public class StaticShader extends ShaderProgram{
	
	public static StaticShader basicShader = null;
	public static int amountOfLights = 0;
	private static HashMap<String, String> consts = new HashMap<String, String>();
	private static boolean lightInited = false;
	private static boolean lightsOn = false;
	
	public static void init() {
		if(!lightInited) {
			setLights(1, false);
		}
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
		
		for(int i = 0;i < amountOfLights;i++) {
			super.getUniformLocation("lightPosition[" + i + "]");
		}
		
		for(int i = 0;i < amountOfLights;i++) {
			super.getUniformLocation("lightAttenuation[" + i + "]");
		}
		
		for(int i = 0;i < amountOfLights;i++) {
			super.getUniformLocation("lightColor[" + i + "]");
		}
		
		for(int i = 0;i < amountOfLights;i++) {
			super.getUniformLocation("lightIntensity[" + i + "]");
		}
		
		for(int i = 0;i < amountOfLights;i++) {
			super.getUniformLocation("lightZ[" + i + "]");
		}
		
		for(int i = 0;i < amountOfLights;i++) {
			super.getUniformLocation("lightRange[" + i + "]");
		}
		
		//lightRange
		
		super.getUniformLocation("lightsOn");
		
		basicShader.start();
		super.loadBool(uniforms.get("lightsOn"), lightsOn);
		stopShaders();
	}
	
	public void loadProjectionMatrix(float[] data) {
		super.loadMatrix4f(uniforms.get("projectionMatrix"), data);
	}
	
	public static void setLights(int amount) {
		setLights(amount, true);
	}
	
	protected static void setLights(int amount, boolean lights) {
		addConstant("__lightNum__", "" + amount);
		amountOfLights = amount;
		lightInited = true;
		Light.initLightArray(amount);
		lightsOn = lights;
	}
	
	public void loadLight(Light light) {
		super.loadVec2(uniforms.get("lightPosition[" + light.ID + "]"), light.pos.x, light.pos.y);
		super.loadVec3(uniforms.get("lightAttenuation[" + light.ID + "]"), light.attenuation.x, light.attenuation.y, light.attenuation.z);
		super.loadVec3(uniforms.get("lightColor[" + light.ID + "]"), light.color.x, light.color.y, light.color.z);
		super.loadFloat(uniforms.get("lightIntensity[" + light.ID + "]"), light.intensity);
		super.loadFloat(uniforms.get("lightZ[" + light.ID + "]"), light.z);
		super.loadFloat(uniforms.get("lightRange[" + light.ID + "]"), light.range);
	}
	
	public void loadTransformationMatrix(float[] data) {
		super.loadMatrix4f(uniforms.get("transformationMatrix"), data);
	}
}
