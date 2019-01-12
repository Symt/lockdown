package com.lockdown.java.application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
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

public class Applet extends JFrame {

	private GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private static final long serialVersionUID = 1L;
	private final JFXPanel jfxPanel = new JFXPanel();
	private WebEngine engine;
	private final JPanel panel = new JPanel(new BorderLayout());
	public static Applet browser;
	String url = "";

	public Applet() {
		super();
		initComponents();
	}

	private void initComponents() {
		createScene();

		panel.add(jfxPanel, BorderLayout.CENTER);

		getContentPane().add(panel);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
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
				engine = view.getEngine();
				jfxPanel.setScene(new Scene(view));
				engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
					public void changed(ObservableValue ov, State oldState, State newState) {
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

	public void loadURL(final String url) {
		Platform.runLater(new Runnable() {
			public void run() {
				File f = new File(System.getProperty("user.dir") + "/src/com/lockdown/html/data.html");
				engine.load(f.toURI().toString());
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				browser = new Applet();
				browser.setVisible(true);
				browser.loadURL("");
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
