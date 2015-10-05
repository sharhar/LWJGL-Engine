package engine.time;

public class Clock {
	
	Time time = new Time(0);
	
	boolean countingUp = false;
	boolean countingDown = false;
	boolean counting = false;
	
	Time start = new Time(0);
	Time end = new Time(0);
	Time countDownTime = new Time(0);
	
	public Clock() {
		
	}
	
	public void stop() {
		countingUp = false;
		countingDown = false;
		counting = false;
	}
	
	public void startCountUp() {
		if(counting) {
			System.err.println("Clock arleady counting!");
		}
		
		counting = true;
		countingUp = true;
		time = new Time(0);
		start = new Time(System.nanoTime());
	}
	
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
	
	public Time getTimePassed() {
		end = new Time(System.nanoTime());
		time = new Time(end.getAsNano() - start.getAsNano());
		return time;
	}
	
	public boolean isDone() {
		return getTimePassed().getAsNano() > countDownTime.getAsNano();
	}
}
