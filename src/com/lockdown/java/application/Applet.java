package com.lockdown.java.application;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.apache.commons.lang3.StringUtils;

import com.lockdown.java.event.EventHandler;

public class Applet extends JFrame {

	private static final long serialVersionUID = 1L;
	public static Applet applet;

	public Applet() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
		String data = readFile(System.getProperty("user.dir") + "/src/com/lockdown/html/data.html",
				Charset.defaultCharset());
		JEditorPane html = null;
		html = new JEditorPane();
		html.setContentType("text/html");
		html.setEditable(false);
		html.setText(data);
		html.addHyperlinkListener(new HyperlinkListener() {

			@Override
			public void hyperlinkUpdate(HyperlinkEvent hle) {
				if (hle.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					URL url = hle.getURL();
					EventHandler.passEvent(StringUtils.replace(url.toString(), "https://", ""), false, 1000, -1);
				}
			}
		});
		add(html);
		setTitle("Lockdown Service Application");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		applet = this;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Applet();
			}
		});
	}

	public String readFile(String path, Charset encoding) {
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			return new String(encoded, encoding);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return null;
	}
	
	public void fullScreen()
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		gd.setFullScreenWindow(this);
	}
}
