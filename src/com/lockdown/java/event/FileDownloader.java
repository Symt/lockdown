package com.lockdown.java.event;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class FileDownloader {
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