package com.lockdown.java.event;

public class Event {
	private String name;
	private boolean repeat;
	private long repeatDelay;
	private int iterations;
	private Trigger trigger;
	private String[] args;
	
	public Event(String name, boolean repeat, long repeatDelay, int iterations, Trigger trigger, String[] args) {
		this.name = name;
		this.repeat = repeat;
		this.repeatDelay = repeatDelay;
		this.iterations = iterations;
		this.trigger = trigger;
		this.args = args;
	}

	@SuppressWarnings("unused")
	private Event() {

	}

	public long getRepeatDelay() {
		return repeatDelay;
	}

	public boolean isRepeating() {
		return repeat;
	}

	public int getIterations() {
		return iterations;
	}

	public String toString() {
		return name;
	}
	
	public void execute() {
		trigger.trigger(args);
	}
}
