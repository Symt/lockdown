package com.lockdown.java.event;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.lockdown.java.application.Applet;

public final class EventHandler {

	public static void passEvent(String event, boolean repeats, long repeatDelay, int iterations) {
		Trigger t = null;
		switch (event) {
		case "{download_file}":
			t = () -> {
				// TODO: Allow for parameter input for file url and file name
				FileDownloader fd = new FileDownloader("", "");
				fd.downloadFile();
			};
			break;
		case "{execute_command}":
			t = () -> {
				String[] args = new String[] {"/bin/bash", "-c", "say Hello World"};
				try {
					Process proc = new ProcessBuilder(args).start();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			};
			break;
			
		case "{FullScreen}":
			t = () -> {
				
				Applet.applet.fullScreen();
				
			};
			break;
			
		case "{no_internal_action}":
			return; // No reason to waste processing power doing nothing, so might as well exit the
					// method.
		default:
			return;
		}
		Event e = new Event(event, repeats, repeatDelay, iterations, t);
		e.execute();
	}

}

class FileDownloader {
	public static boolean isDownloading;
	public final String fileLink;
	public final String fileName;
	public final File file;

	public FileDownloader(String link, String fileName) {
		fileLink = link;
		this.fileName = fileName;
		file = new File(System.getProperty("user.home") + "/Downloads/" + fileName);
	}

	public void downloadFile() {
		try {
			URL downloadUrl = new URL(fileLink);
			file.createNewFile();
			FileUtils.copyURLToFile(downloadUrl, file, 0, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}