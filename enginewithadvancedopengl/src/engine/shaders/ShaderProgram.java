package engine.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class ShaderProgram {
	
	protected int programID;
	protected int vertexShaderID;
	protected int fragmentShaderID;
	
	public Map<String, Integer> uniforms = new HashMap<String, Integer>();
	protected List<String> uniformNames = new ArrayList<String>();
	
	protected HashMap<String, String> constants = new HashMap<String, String>();
	
	public ShaderProgram() {
		
	}
	
	protected void compile(String vertexFile, String fragmentFile) {
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	protected int getUniformLocation(String uniformName) {
		int location = GL20.glGetUniformLocation(programID, uniformName);
		uniforms.put(uniformName, location);
		return location;
	}
	
	public void loadConstant(String name, String value) {
		constants.put(name, value);
	}
	
	protected int getUniformLocationRaw(String uniformName) {
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	
	protected void loadInt(int location, int value) {
		GL20.glUniform1i(location, value);
	}
	
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	
	public void loadVec2(int location, float x, float y) {
		GL20.glUniform2f(location, x, y);
	}
	
	protected void loadVec3(int location, float x, float y, float z) {
		GL20.glUniform3f(location, x, y, z);
	}
	
	protected void loadVec4(int location, float x, float y, float z, float w) {
		GL20.glUniform4f(location, x, y, z, w);
	}
	
	protected void loadBool(int location, boolean value) {
		if(value) {
			GL20.glUniform1f(location, 1);
		} else {
			GL20.glUniform1f(location, 0);
		}
	}
	
	protected void loadMatrix4f(int location, float[] data) {
		FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
		for(int i =0;i < 16;i++) {
			matrixBuffer.put(data[i]);
		}
		matrixBuffer.flip();
		GL20.glUniformMatrix4fv(location, false, matrixBuffer);
	}
	
	protected abstract void bindAttributes();
	protected abstract void getAllUniformLocations();
	
	public void start() {
		GL20.glUseProgram(programID);
	}
	
	public static void stopShaders() {
		GL20.glUseProgram(0);
	}
	
	public void cleanUp() {
		stopShaders();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteProgram(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	protected void bindAttribute(int attribute, String variable) {
		GL20.glBindAttribLocation(programID, attribute, variable);
	}
	
	private int loadShader(String file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read file!");
			e.printStackTrace();
			System.exit(-1);
		}
		String temp = new String(shaderSource);
		for(String cons:constants.keySet()) {
			temp = temp.replace(cons, constants.get(cons));
		}
		
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, temp);
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}
		return shaderID;
	}
	
}
