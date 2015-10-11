package engine.sound;

import static org.lwjgl.openal.AL10.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.WaveData;
import org.lwjgl.util.vector.Vector3f;

public class Sound {
	
	int ID = 0;
	int bufferID = 0;
	public Vector3f pos;
	float volume = 0.5f;
	
	public static boolean on = true;
	
	public boolean playing = false;
	private boolean started = false;
	
	public Sound(String path) {
		this(path, new Vector3f(), 0.5f);
	}
	public Sound(String path, Vector3f pos) {
		this(path,pos,0.5f);
	}
	
	public Sound(String path, float volume) {
		this(path,new Vector3f(),volume);
	}
	
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
	
	public float getVolume() {
		return volume;
	}
	
	
	public void setVolume(float vol) {
		volume = vol;
		alSourcef(ID, AL_GAIN, volume);
	}
	
	public void volUp(float amount) {
		volume += amount;
		if(volume > 1) {
			volume = 1;
		}
		
		alSourcef(ID, AL_GAIN, volume);
	}
	
	public void volDown(float amount) {
		volume -= amount;
		if(volume < 0) {
			volume = 0;
		}
		alSourcef(ID, AL_GAIN, volume);
	}
	
	public void destroy() {
		stop();
		alDeleteSources(ID);
		alDeleteBuffers(bufferID);
	}
	
	public void play() {
		if(!on) {
			return;
		}
		alSourcePlay(ID);
		playing = true;
		started = true;
	}
	
	public void pause() {
		if(!on) {
			return;
		}
		alSourcePause(ID);
		playing = false;
	}
	
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
	
	public void resume() {
		if(!on) {
			return;
		}
		if(!playing && started) {
			alSourcePlay(ID);
		}
		playing = true;
	}
	
	public void stop() {
		alSourceStop(ID);
		playing = false;
		started = false;
	}
	
	public void setLooping(boolean loop) {
		if(loop) {
			alSourcei (ID, AL_LOOPING,  AL_TRUE);
		} else {
			alSourcei (ID, AL_LOOPING,  AL_FALSE);
		}
		
	}
}
