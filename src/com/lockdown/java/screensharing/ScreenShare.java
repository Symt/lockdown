package com.lockdown.java.screensharing;

/* 
 * Source for this code (unedited) can be found at https://github.com/UltimatePea/ScreenSharer/
 */

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class ScreenShare {
	Socket socket;
	ServerSocket server;

	public ScreenShare() {
		String port = "10000"; // This will be set by the server when a teacher requests for it
		if (available(Integer.parseInt(port))) {
			server(Integer.parseInt(port));
		} else {
			System.err.println("-- Inavlid Port --\nPort: " + port
					+ "\nPlease enter a port that is not in use and is in range\nValid Range: 1024 <= port <= 65535\n");
		}
	}

	private void server(int port) {
		try {
			server = new ServerSocket(port);
			Robot r = new Robot();
			BufferedImage img;
			Graphics g;
			Point mouse;
			ObjectOutputStream outstream;
			while (true) {
				socket = server.accept();
				outstream = new ObjectOutputStream(socket.getOutputStream());
				img = r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
				mouse = MouseInfo.getPointerInfo().getLocation();
				g = img.getGraphics();
				g.setColor(Color.BLACK);
				g.fillRect(mouse.x, mouse.y, 15, 15);
				ImageIO.write(img, "jpg", outstream);
				socket.close();
			}
		} catch (AWTException | IOException e) {
			e.printStackTrace();
		}

	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

	/*
	 * Courtesy of Apache Camel
	 */
	public static boolean available(int port) {
		if (port < 1024 || port > 65535) {
			return false;
		}

		ServerSocket ss = null;
		DatagramSocket ds = null;
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {
		} finally {
			if (ds != null) {
				ds.close();
			}

			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					/* should not be thrown */
				}
			}
		}

		return false;
	}
}