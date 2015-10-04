package engine.sound;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

public class SoundManager {
	
	private static List<Sound> sounds = new ArrayList<Sound>();
	
	public static void init() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addSound(Sound sound) {
		sounds.add(sound);
	}
	
	public static void destroy() {
		for(Sound s:sounds) {
			s.destroy();
		}
		
		AL.destroy();
	}
}
