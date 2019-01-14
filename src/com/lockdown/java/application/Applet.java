 package com.lockdown.java.application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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

public class Applet extends JFrame{

	private GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private static final long serialVersionUID = 1L;
	private final JFXPanel jfxPanel = new JFXPanel();
	private WebEngine engine;
	public static Applet browser;

	public Applet() {
		super();
		initComponents();
	}

	private void initComponents() {
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
					public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, State oldState, State newState) {
						if (newState == State.SUCCEEDED) {
							EventListener listener = new EventListener() {
								public void handleEvent(Event ev) {
									String event = ((HTMLElementImpl) ev.getTarget()).getAttribute("href");
									EventHandler.passEvent(event, false, 1000, -1);
								}
							};
							Document doc = engine.getDocument();
							NodeList list = doc.getElementsByTagName("span");
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
					engine.load((f.toURI()).toString());
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				browser = new Applet();
				browser.loadURL("/com/lockdown/html/data.html");
				browser.setVisible(true);
			}
		});
	}

	public void fullScreen() {
		gd.setFullScreenWindow(this);
	}

	public void minScreen() {
		gd.setFullScreenWindow(null);
	}
}
