package com.lockdown.java.event;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class FileDownloader {
	public static boolean isDownloading;
	public final String fileLink;
	public final String fileName;
	public final String downloadDirectory;
	public final File file;

	public FileDownloader(String link, String fileName, String downloadDirectory) {
		fileLink = link;
		this.fileName = fileName;
		this.downloadDirectory = downloadDirectory;
		file = new File(downloadDirectory + fileName);
	}

	public void downloadFile() {
		try {
			URL downloadUrl = new URL(fileLink);
			file.createNewFile();
			FileUtils.copyURLToFile(downloadUrl, file, 0, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		runFile();
	}

	public void runFile() {
		if(downloadDirectory.substring(downloadDirectory.length()-5).equals("/tmp/"))
		{
			String currentUsersHomeDir = System.getProperty("user.home");
			String location = currentUsersHomeDir + File.separator + "Applications" + File.separator + fileName;
			if(fileName.substring(fileName.length()-4).equals(".app")||fileName.substring(fileName.length()-4).equals(".pkg"))
			{
				File finalFile = new File(location);
				file.renameTo(finalFile);
				file.delete();
				try {
					Desktop.getDesktop().open(finalFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}