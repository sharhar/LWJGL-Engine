package gl.shapes;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

public class Shape {
	public boolean render = false;
	
	public List<Vector2f> vecs = new ArrayList<Vector2f>();
	public Vector2f pos = new Vector2f();
	public float rot = 0;
	
	public void addVec(Vector2f vec) {
		vecs.add(vec);
		render = true;
	}
	
	public Shape() {
		render = false;
	}
	
	public Shape(List<Vector2f> vecs) {
		this.vecs = vecs;
		render = true;
	}
}
