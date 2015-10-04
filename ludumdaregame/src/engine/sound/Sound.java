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
	int vol = 0;
	
	public static boolean on = true;
	
	public boolean playing = false;
	private boolean started = false;
	
	public Sound(String path, Vector3f pos) {
		this.pos = pos;
		
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
		
		SoundManager.addSound(this);
	}
	
	public void volUp(float amount) {
		pos.z += amount;
		alSource3f(ID, AL_POSITION, pos.x,pos.y,pos.z);
	}
	
	public void volDown(float amount) {
		pos.z -= amount;
		alSource3f(ID, AL_POSITION, pos.x,pos.y,pos.z);
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
