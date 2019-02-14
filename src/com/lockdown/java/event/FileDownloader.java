package com.lockdown.java.event;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

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
			FileUtils.copyURLToFile(downloadUrl, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String source = downloadDirectory + fileName;
		if (fileName.substring(fileName.length() - 4).equals(".zip")) {

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
		} else if (fileName.substring(fileName.length() - 4).equals(".pkg")) {
			EventHandler.passEvent("execute_command|installer -pkg " + source + " -target ~", false, 1000,
					-1);
		}
		
		file.delete();
	}
	
}