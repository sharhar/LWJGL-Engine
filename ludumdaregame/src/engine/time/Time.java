package engine.time;

/**
 * This class is used from time management
 * @author Sharhar
 */
public class Time {
	
	public static float deltaTime = 0;
	
	private static long startTime = 0;
	private static long currentTime = 0;
	
	/**
	 * Initialize deltaTime system
	 */
	public static void init() {
		startTime = System.nanoTime();
		currentTime = System.nanoTime();
	}
	/**
	 * update deltaTime variable
	 */
	public static void tick() {
		currentTime = System.nanoTime();
		
		deltaTime = (float) ((currentTime - startTime) / (1000000.0f * 1000));
		
		startTime = System.nanoTime();
	}
	
	private long time = 0;
	
	/**
	 * This is the Time class constructor
	 * @param time the amount of time you want the class to store
	 */
	public Time(long time) {
		this.time = time;
	}
	
	/**
	 * Converts seconds to nanoseconds
	 * @param time amount of seconds
	 * @return amount of nanoseconds
	 */
	public static long secondsToNano(float time) {
		return (long)(time * 1000000 * 1000);
	}
	
	/**
	 * set amount of time in this instance
	 * @param time amount of time in nano seconds
	 */
	public void setTime(long time) {
		this.time = time;
	}
	
	/**
	 * get amount of time in nanosecond format
	 * @return amount of time
	 */
	public long getAsNano() {
		return time;
	}
	
	/**
	 * get amount of time in milliseconds
	 * @return amount of time
	 */
	public float getAsMillis() {
		return (float) (time / 1000000.0f);
	}
	
	/**
	 * get amount of time in seconds
	 * @return amount of time
	 */
	public float getAsSeconds() {
		return (float) (time / (1000000.0f * 1000));
	}
	
	/**
	 * get amount of time in minutes
	 * @return amount of time
	 */
	public float getAsMinutes() {
		return (float) (time / (1000000.0f * 1000 * 60));
	}
	
	/**
	 * get amount of time in hours
	 * @return amount of time
	 */
	public float getAsHours() {
		return (float) (time / (1000000.0f * 1000 * 60 * 60));
	}
}
