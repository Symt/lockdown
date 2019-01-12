package com.lockdown.java.event;

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
		this.downloadDirectory =  downloadDirectory;
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
	}
	
	public void runFile() {
		//TODO: Implementation
		
		/*
		 * If the directory is /tmp/ and the file is either a .app or a .pkg, allow the file to be ran and installed to /Applications (or ~/Applications for now).
		 */
		if(downloadDirectory.substring(downloadDirectory.length()-5).equals("/tmp/"))
		{
			if(fileName.substring(fileName.length()-4).equals(".app")||fileName.substring(fileName.length()-4).equals(".pkg"))
			{
				if(fileName.substring(fileName.length()-4).equals(".app"))
				{
					//TODO: Implementation
					
					/*
					 *  Code to install app
					 */
				}
				else
				{
					//TODO: Implementation
					
					/*
					 * Code to install pkg
					 */
				}
			}
		}
	}
		
		
		
		
		
		
		
		
		
		
	}