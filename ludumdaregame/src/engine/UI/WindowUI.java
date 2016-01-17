package engine.UI;

import java.util.ArrayList;
import java.util.List;

public class WindowUI {
	
	public List<UIObject> objects = new ArrayList<UIObject>();
	
	public void addObject(UIObject object) {
		objects.add(object);
	}
	
	public void render() {
		for(UIObject o:objects) {
			o.render();
		}
	}
	
	public void tick() {
		for(UIObject o:objects) {
			o.tick();
		}
	}
}
