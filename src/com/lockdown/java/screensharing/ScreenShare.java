package com.lockdown.java.screensharing;

import java.awt.AWTException;
import java.awt.Color;

/* 
 * Source for this code (unedited) can be found at https://github.com/UltimatePea/ScreenSharer/
 */

import java.awt.Dimension;
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
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ScreenShare {
	Socket socket;
	public ScreenShare() {
		String port = "10000"; // This will be set by the server when a teacher requests for it
		server(Integer.parseInt(port));
	}

	private void server(int port) {
		try {
			ServerSocket server = new ServerSocket(port);
			Robot r = new Robot();
			BufferedImage img;
			Graphics g;
			Point mouse;
			ObjectOutputStream outstream;
			while(true){
				try{
					socket = server.accept();
					outstream = new ObjectOutputStream(socket.getOutputStream());
					img = r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
					mouse = MouseInfo.getPointerInfo().getLocation();
					g = img.getGraphics();
					g.setColor(Color.BLACK);
					g.fillRect(mouse.x, mouse.y, 30, 30);
					ImageIO.write(img, "jpg", outstream);
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}  catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
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
}

class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage img;

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (img != null)
			g.drawImage(img, 0, 0, this);
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

}