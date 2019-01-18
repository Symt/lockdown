package com.lockdown.java.event;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.lockdown.java.application.Applet;

import net.lingala.zip4j.exception.ZipException;

import net.lingala.zip4j.core.ZipFile;

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
					String header = StringUtils.countMatches(arg[1].substring(0,arg[1].indexOf('/')),'.')==3?"http://":"https://";
					FileDownloader fd = new FileDownloader(header + arg[1], arg[2], arg[0]);
					fd.downloadFile();
					if(arg[2].substring(arg[2].length()-4).equals(".zip"))
					{
						String source = arg[0] + arg[2];
					    String destination = System.getProperty("user.home") + "/Applications/";
					    try {
					         ZipFile zipFile = new ZipFile(source);
					         java.util.zip.ZipFile entries = new java.util.zip.ZipFile(source);
					         String zipName = StringUtils.split(entries.entries().nextElement().getName(), "/")[0];
					         entries.close();
					         zipFile.extractAll(destination);
					         String path = destination + zipName + "/Contents/MacOS/";
					         path += new File(path).list()[0];
					         EventHandler.passEvent("execute_command|chmod +x " + path, false, 1000, -1);
					         
					    } catch (ZipException | IOException e) {
					        e.printStackTrace();
					    }
					}
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