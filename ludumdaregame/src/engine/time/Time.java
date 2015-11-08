package engine.time;

public class Time {
	
	public static float deltaTime = 0;
	
	private static long startTime = 0;
	private static long currentTime = 0;
	
	public static void init() {
		startTime = System.nanoTime();
		currentTime = System.nanoTime();
	}
	
	public static void tick() {
		currentTime = System.nanoTime();
		
		deltaTime = (float) ((currentTime - startTime) / (1000000.0f * 1000));
		
		startTime = System.nanoTime();
	}
	
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
