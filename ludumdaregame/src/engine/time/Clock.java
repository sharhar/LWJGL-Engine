package engine.time;

public class Clock {
	
	Time time = new Time(0);
	
	boolean countingUp = false;
	boolean countingDown = false;
	boolean counting = false;
	
	Time start = new Time(0);
	Time end = new Time(0);
	Time countDownTime = new Time(0);
	
	/**
	 * This function stops the clock
	 */
	public void stop() {
		countingUp = false;
		countingDown = false;
		counting = false;
	}
	
	/**
	 * This function starts a count up
	 */
	public void startCountUp() {
		if(counting) {
			System.err.println("Clock arleady counting!");
		}
		
		counting = true;
		countingUp = true;
		time = new Time(0);
		start = new Time(System.nanoTime());
	}
	
	/**
	 * This function starts a count down
	 * @param time amount of time to count for
	 */
	public void startCountDown(float time) {
		if(counting) {
			System.err.println("Clock arleady counting!");
		}
		
		counting = true;
		countingDown = true;
		this.time = new Time(0);
		start = new Time(System.nanoTime());
		countDownTime = new Time(Time.secondsToNano(time));
	}
	
	/**
	 * This function returns the amount of time that has passed
	 * @return amount of time passed
	 */
	public Time getTimePassed() {
		end = new Time(System.nanoTime());
		time = new Time(end.getAsNano() - start.getAsNano());
		return time;
	}
	
	/**
	 * This function checks if the count down is done
	 * @return whether or not the count down is down
	 */
	public boolean isDone() {
		return getTimePassed().getAsNano() > countDownTime.getAsNano();
	}
}
