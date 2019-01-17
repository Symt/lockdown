package com.lockdown.java.application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import com.lockdown.java.screensharing.ScreenShare;
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
	public Thread screenshare;

	public Applet() {
		super("Lockdown Service Application");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				/*
				 * Closing Event
				 */
				System.exit(0);
			}
		});
		screenshare = new Thread(new Runnable() {
			public void run() {
				new ScreenShare();
			}
		});
		/*
		 * screenshare.start() <-- event performed when requested by server
		 */
		initComponents();
	}

	private void initComponents() {
		full = false;
		numDownloads = initNum();
		createScene();
		add(jfxPanel, BorderLayout.CENTER);
		setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		setMinimumSize(new Dimension(800, 800));
		setPreferredSize(new Dimension(800, 800));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
					String editHTML = StringUtils.replace(oldHTML,
							"<body onload=\"start("
									+ StringUtils.substringBetween(oldHTML, "<body onload=\"start(", ")\">") + ")\">",
							"<body onload=\"start(" + numDownloads + ")\">");
					Files.write(Paths.get(temp.toURI()), editHTML.getBytes());
					engine.load((f.toURI()).toString());
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

	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = null;
		encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				browser = new Applet();
				browser.setVisible(true);
				browser.loadURL("/com/lockdown/html/student.html");

			}
		});
	}

}
