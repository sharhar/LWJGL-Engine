package engine.sound;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

/**
 * This class is used to manage the sounds in the game
 * @author Sharhar
 */
public class SoundManager {
	
	private static List<Sound> sounds = new ArrayList<Sound>();
	
	/**
	 * This function initializes the SoundManager 
	 */
	public static void init() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setUniversalSoundVolume(float volume) {
		for(Sound s:sounds) {
			s.setVolume(volume);
		}
	}
	
	/**
	 * This function adds a sound to the list of sounds
	 * @param sound
	 */
	public static void addSound(Sound sound) {
		sounds.add(sound);
	}
	
	/**
	 * This function destroys all the sounds in the game
	 */
	public static void destroy() {
		for(Sound s:sounds) {
			s.destroy();
		}
		
		AL.destroy();
	}
}
