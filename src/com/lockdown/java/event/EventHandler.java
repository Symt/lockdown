package com.lockdown.java.event;

public final class EventHandler {
	
	public static void passEvent(String event, boolean repeats, long repeatDelay, int iterations) {
		Trigger t = () -> {
			
		};
		switch(event) {
		/*
		 * Event handling will happen here
		 */
		}
		Event e = new Event(event, repeats, repeatDelay, iterations, t);
		e.execute();
	}

}