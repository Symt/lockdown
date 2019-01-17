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
					FileDownloader fd = new FileDownloader("https://" + arg[1], arg[2],
							System.getProperty("user.home") + arg[0]);
					fd.downloadFile();
				};
				break;
			case "execute_command":
				t = (arg) -> {
					String[] arguments = new String[] { "/bin/bash", "-c", arg[0] };
					try {
						(new ProcessBuilder(arguments)).start();
					} catch (IOException e) {
						e.printStackTrace();
					}
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
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			System.err.println("Malformed command: " + event
					+ "\nIt should contain more/less parts to match up with the command. See the documentation (Comming soon)");
		}
		new Thread((new Runnable() {
			String event;
			boolean repeats;
			long repeatDelay;
			int iterations;
			Trigger t;
			String[] args;

			public void run() {
				new Event(event, repeats, repeatDelay, iterations, t, args).execute();
			}

			public Runnable passArgs(String event, boolean repeats, long repeatDelay, int iterations, Trigger t,
					String[] args) {
				this.event = event;
				this.repeats = repeats;
				this.repeatDelay = repeatDelay;
				this.iterations = iterations;
				this.t = t;
				this.args = args;
				return this;
			}
		}).passArgs(event, repeats, repeatDelay, iterations, t, args)).start();
	}

}