package engine.time;

public class Time {
	
	private long time = 0;
	
	public Time(long time) {
		this.time = time;
	}
	
	public static long secondsToNano(float time) {
		return (long)(time * 1000000 * 1000);
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public long getAsNano() {
		return time;
	}
	
	public float getAsMillis() {
		return (float) (time / 1000000.0f);
	}
	
	public float getAsSeconds() {
		return (float) (time / (1000000.0f * 1000));
	}
	
	public float getAsMinutes() {
		return (float) (time / (1000000.0f * 1000 * 60));
	}
	
	public float getAsHours() {
		return (float) (time / (1000000.0f * 1000 * 60 * 60));
	}
}
