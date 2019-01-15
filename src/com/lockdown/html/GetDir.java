package com.lockdown.html;

public class GetDir {
	public static String get()
	{
		String start = GetDir.class.getResource("getDir.class").toString();
		return start;
	}
	
}
