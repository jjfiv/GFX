package me.jjfoley.gfx;

import java.awt.event.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This is a non-public class that handles receiving all events from Java and
 * putting them into the abstract GFX application.
 * 
 * @author jfoley
 */
class EventManager implements WindowListener, KeyListener, MouseListener, MouseMotionListener {
	/**
	 * The app for which we are collecting events.
	 */
	private final GFX app;

	/**
	 * The atomically referenced location of the most recent mouse click.
	 * Can be cleared by the user or updated by a new event on different threads safely.
	 */
	AtomicReference<IntPoint> lastClick = new AtomicReference<>();
	/**
	 * The atomically referenced location of the mouse when it was last over the window.
	 * Can be cleared by the user or updated by a new event on different threads safely.
	 */
	AtomicReference<IntPoint> lastMousePosition = new AtomicReference<>();
	/**
	 * The atomically-updated mapping from each keycode to their current state up=true, down=false.
	 * Can be read/updated from different threads.
	 */
	ConcurrentHashMap<Integer, Boolean> keyState = new ConcurrentHashMap<>();

	public EventManager(GFX app) {
		this.app = app;
	}

	/** Typing the ESC key will let you quit no matter what. */
	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			app.stop();
		}
	}

	/** Closing the window will let you quit no matter what. */
	@Override
	public void windowClosed(WindowEvent e) {
		app.stop();
	}

	/** Keep track of all the keys that are down. */
	@Override
	public void keyPressed(KeyEvent e) {
		keyState.put(e.getKeyCode(), true);
	}

	/** Take keys out of the map when they are not pressed. */
	@Override
	public void keyReleased(KeyEvent e) {
		keyState.remove(e.getKeyCode());
	}

	/** Keep track of the location of the most recent click. */
	@Override
	public void mouseReleased(MouseEvent e) {
		lastClick.set(new IntPoint(e));
	}

	/** Clear the mouse position if it goes outside the window. */
	@Override
	public void mouseExited(MouseEvent e) {
		lastMousePosition.set(null);
	}

	/** Keep track of the latest mouse position. */
	@Override
	public void mouseMoved(MouseEvent e) {
		lastMousePosition.set(new IntPoint(e));
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}
}
