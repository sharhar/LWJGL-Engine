package engine.sound;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALContext;
import org.lwjgl.openal.ALDevice;

/**
 * This class is used to manage the sounds in the game
 * @author Sharhar
 */
public class SoundManager {
	
	private static List<Sound> sounds = new ArrayList<Sound>();
	
	public static ALContext context;
	public static ALDevice device;
	public static ALCCapabilities capabilities;
	
	/**
	 * This function initializes the SoundManager 
	 */
	public static void init() {
		context = ALContext.create();
		device = context.getDevice();
		context.makeCurrent();
		
		ALCCapabilities capabilities = device.getCapabilities();
		if(!capabilities.OpenALC10) {
			throw new RuntimeException("OpenAL Context Creation failed");
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
		
		device.destroy();
		context.destroy();
	}
}
