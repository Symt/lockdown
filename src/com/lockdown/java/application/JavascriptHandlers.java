package com.lockdown.java.application;

import com.lockdown.java.event.EventHandler;

public class JavascriptHandlers {
	public void callEvent(String event) {
		EventHandler.passEvent(event, false, 1000, -1);
	}
}
