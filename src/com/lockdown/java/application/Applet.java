package com.lockdown.java.application;

import javax.swing.JFrame;

public class Applet extends JFrame {
	public Applet() {
		setTitle("Lockdown Service Application");
		
		
		setupApplication();
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Applet();
	}
	
	
	public void setupApplication() {
		
	}
}
