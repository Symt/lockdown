package com.lockdown.java.event;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.lockdown.java.application.Applet;

public final class EventHandler {

	public static void passEvent(String event, boolean repeats, long repeatDelay, int iterations) {
		Trigger t = null;
		event = StringUtils.replace(StringUtils.replace(event, "{", ""), "}", "");
		String[] old = event.split("\\|");
		String[] args = new String[1];
		if (old.length - 1 > 0) {
			args = Arrays.copyOfRange(old, 1, old.length);
		}
		try {
			switch (old[0]) {
			case "download_file":
				t = (arg) -> {
					FileDownloader fd = new FileDownloader("https://" + arg[0], arg[1], System.getProperty("user.home") + "/Downloads/");
					fd.downloadFile();
				};
				break;
			case "execute_command":
				t = (arg) -> {
					String[] arguments = new String[] { "/bin/bash", "-c", arg[0] };
					try {
						Process proc = new ProcessBuilder(arguments).start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				};
				break;
			case "minscreen":
				t = (nothing) -> {
					Applet.browser.minScreen();
				};
				break;
			case "fullscreen":
				t = (nothing) -> {
					Applet.browser.fullScreen();
				};
				break;

			case "no_internal_action":
				return; // No reason to waste processing power doing nothing, so might as well exit the
						// method.
			default:
				return;
			}
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e ) {
			System.out.println("Malformed command: " + event + "\nIt should contain more/less parts to match up with the command. See the documentation (Comming soon)");
		}
		Event e = new Event(event, repeats, repeatDelay, iterations, t, args);
		e.execute();
	}

}