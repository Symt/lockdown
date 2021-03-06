package com.lockdown.java.application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.lockdown.java.screensharing.ScreenShare;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class Applet extends JFrame {

	private GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private static final long serialVersionUID = 1L;
	private final JFXPanel jfxPanel = new JFXPanel();
	private WebEngine engine;
	public static Applet browser;
	public static boolean full;
	public Thread screenshare;

	public Applet() {
		super("Lockdown Service Application");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
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
							JSObject window = (JSObject) engine.executeScript("window");
							window.setMember("handler", new JavascriptHandlers());
							browser.setVisible(true);
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
					engine.load((f.toURI()).toString());
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void fullScreen() {
		gd.setFullScreenWindow(full ? null : this);
		full = !full;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				browser = new Applet();
				browser.loadURL("/com/lockdown/html/index.html");
			}
		});
	}
}
