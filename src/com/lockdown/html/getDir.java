package com.lockdown.html;

public class getDir {
	public static String get()
	{
		String start = getDir.class.getResource("getDir.class").toString();
		return start;
	}
	
}
