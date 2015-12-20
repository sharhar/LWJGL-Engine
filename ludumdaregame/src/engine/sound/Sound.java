package engine.sound;

import static org.lwjgl.openal.AL10.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.WaveData;
import org.lwjgl.util.vector.Vector3f;

/**
 * This class is used to play sounds
 * @author Sharhar
 */
public class Sound {
	
	int ID = 0;
	int bufferID = 0;
	public Vector3f pos;
	float volume = 0.5f;
	
	public static boolean on = true;
	
	public boolean playing = false;
	private boolean started = false;
	
	/**
	 * This constructor creates the sound from a path
	 * @param path path to sound
	 */
	public Sound(String path) {
		this(path, new Vector3f(), 0.5f);
	}
	
	/**
	 * This constructor creates the sound from a path and position
	 * @param path path to sound
	 * @param pos position of sound
	 */
	public Sound(String path, Vector3f pos) {
		this(path,pos,0.5f);
	}
	
	/**
	 * This constructor creates the sound from a path and a volume
	 * @param path path to sound
	 * @param volume volume of sound
	 */
	public Sound(String path, float volume) {
		this(path,new Vector3f(),volume);
	}
	
	/**
	 * This constructor creates the sound from a path, position, and a volume
	 * @param path path to sound
	 * @param pos position of sound
	 * @param volume volume of sound
	 */
	public Sound(String path, Vector3f pos, float volume) {
		this.pos = pos;
		
		this.volume = volume;
		
		WaveData data = WaveData.create(path);
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		alGenBuffers(buffer);
		alBufferData(buffer.get(0), data.format, data.data, data.samplerate);
		data.dispose();
		
		bufferID = buffer.get(0);
		
		ID = alGenSources();
		
		alSourcei(ID, AL_BUFFER, buffer.get(0));
		alSource3f(ID, AL_POSITION, pos.x,pos.y,pos.z);
		alSource3f(ID, AL_VELOCITY, 0,0,0);
		alSourcef(ID, AL_GAIN, volume);
		
		SoundManager.addSound(this);
	}

	/**
	 * This function returns the volume of this sound
	 * @return volume of this sound
	 */
	public float getVolume() {
		return volume;
	}
	
	/**
	 * This function sets the volume of a sound
	 * @param vol volume
	 */
	public void setVolume(float vol) {
		volume = vol;
		alSourcef(ID, AL_GAIN, volume);
	}
	
	/**
	 * This function raises the volume of the sound
	 * @param amount amount to raise the volume by
	 */
	public void volUp(float amount) {
		volume += amount;
		if(volume > 1) {
			volume = 1;
		}
		
		alSourcef(ID, AL_GAIN, volume);
	}
	
	/**
	 * This function lowers the volume of the sound
	 * @param amount amount to lower the volume by
	 */
	public void volDown(float amount) {
		volume -= amount;
		if(volume < 0) {
			volume = 0;
		}
		alSourcef(ID, AL_GAIN, volume);
	}
	
	/**
	 * This function destroys the sound and deletes it from memory
	 */
	public void destroy() {
		stop();
		alDeleteSources(ID);
		alDeleteBuffers(bufferID);
	}
	
	/**
	 * This function starts the playback of the sound
	 */
	public void play() {
		if(!on) {
			return;
		}
		alSourcePlay(ID);
		playing = true;
		started = true;
	}
	
	/**
	 * This function pauses the playback of the sound
	 */
	public void pause() {
		if(!on) {
			return;
		}
		alSourcePause(ID);
		playing = false;
	}
	
	/**
	 * This function toggles playback between paused and playing
	 */
	public void toggle() {
		if(!on) {
			return;
		}
		if(!playing && started) {
			alSourcePlay(ID);
			playing = true;
		} else if (playing) {
			alSourcePause(ID);
			playing = false;
		}
	}
	
	
	/**
	 * This function resumes playback of the sound
	 */
	public void resume() {
		if(!on) {
			return;
		}
		if(!playing && started) {
			alSourcePlay(ID);
		}
		playing = true;
	}
	
	/**
	 * This function stops the playback of the sound
	 */
	public void stop() {
		alSourceStop(ID);
		playing = false;
		started = false;
	}
	
	/**
	 * This function sets whether or not the sound will loop
	 * @param loop whether the sound will loop or not
	 */
	public void setLooping(boolean loop) {
		if(loop) {
			alSourcei (ID, AL_LOOPING,  AL_TRUE);
		} else {
			alSourcei (ID, AL_LOOPING,  AL_FALSE);
		}
		
	}
}
