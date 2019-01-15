package com.lockdown.java.application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import com.lockdown.java.event.EventHandler;
import com.sun.webkit.dom.HTMLElementImpl;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Applet extends JFrame {

	private GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private static final long serialVersionUID = 1L;
	private final JFXPanel jfxPanel = new JFXPanel();
	private WebEngine engine;
	public static Applet browser;
	public static boolean full;
	public static int numDownloads;

	public Applet() {
		super();
		initComponents();
	}

	private void initComponents() {
		full = false;
		numDownloads = initNum();
		createScene();
		add(jfxPanel, BorderLayout.CENTER);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setMinimumSize(new Dimension(800, 800));
		setPreferredSize(new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight()));
		setTitle("Lockdown Service Application");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();

	}

	private void createScene() {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				WebView view = new WebView();
				view.setContextMenuEnabled(false);
				engine = view.getEngine();
				jfxPanel.setScene(new Scene(view));
				engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
					public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, State oldState,
							State newState) {
						if (newState == State.SUCCEEDED) {
							EventListener listener = new EventListener() {
								public void handleEvent(Event ev) {
									String event = ((HTMLElementImpl) ev.getTarget()).getAttribute("href");
									EventHandler.passEvent(event, false, 1000, -1);
								}
							};
							Document doc = engine.getDocument();
							NodeList list = doc.getElementsByTagName("button");
							for (int i = 0; i < list.getLength(); i++)
								((EventTarget) list.item(i)).addEventListener("click", listener, false);

						}

					}
				});
			}
		});
	}

	public void loadURL(String resourcePath) {
		Platform.runLater(new Runnable() {
			public void run() {
				URL f = this.getClass().getResource(resourcePath);
				try {
					String oldHTML = readFile(f.getFile(), Charset.defaultCharset());
					File temp = new File(f.toURI());
					temp.createNewFile();
					String editHTML = StringUtils.replace(oldHTML, "<body onload=\"start(0)\">",
							"<body onload=\"start(" + numDownloads + ")\">");
					System.out.println(editHTML);
					Files.write(Paths.get(temp.toURI()), editHTML.getBytes());
					engine.load((f.toURI()).toString());
					// Files.write(Paths.get(temp.toURI()), oldHTML.getBytes());
					/*
					 * Okay, so here's the deal.. This shouldn't work.. It should be overwriting the
					 * file, but it isn't.. So heck dude, might as well go with it. Don't question
					 * the logic, just accept it
					 */
				} catch (URISyntaxException | IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void fullScreen() {
		gd.setFullScreenWindow(full ? null : this);
		full = !full;
	}

	public int initNum() {
		String[] arguments = new String[] { "/bin/bash", "-c", "cd ~/Desktop/Server/; ls -d *.app | wc -l" };
		Runtime rt = Runtime.getRuntime();
		Process proc = null;
		int s = 0;
		try {
			proc = rt.exec(arguments);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			s = Integer.parseInt(stdInput.readLine().substring(7));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String readFile(String path, Charset encoding) {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(encoded, encoding);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				browser = new Applet();
				browser.loadURL("/com/lockdown/html/student.html");
				browser.setVisible(true);
			}
		});
	}
}
