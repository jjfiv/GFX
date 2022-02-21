package me.jjfoley.gfx;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Extend this class to have a graphical application with Java.
 * 
 * <pre>
 * {@code
import java.awt.Color;
import java.awt.Graphics2D;

import me.jjfoley.gfx.GFX;

public class MyDrawing extends GFX {
	public void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect(0, 0, 200, 200);
	}

	public static void main(String[] args) {
		MyDrawing app = new MyDrawing();
		app.start();
	}
}
}
 * </pre>
 * 
 * @author jfoley
 *
 */
public abstract class GFX {
	/**
	 * The event manager handles key presses, mouse clicks, mouse movement and
	 * window closing.
	 */
	private EventManager events;
	/**
	 * The actual view is a Canvas of fixed size.
	 */
	private GFXView view;
	/**
	 * The window we open is called a JFrame.
	 */
	private JFrame frame;
	/**
	 * This variable controls how fast we draw the screen.
	 */
	public static int FPS = 50;

	/**
	 * This variable tells us whether we are running our application or not. When
	 * set to false, we will eventually close the window.
	 */
	private AtomicBoolean running = new AtomicBoolean(false);

	/**
	 * Update thread, if any, or null.
	 */
	private AtomicReference<Thread> updater = new AtomicReference<>(null);

	/**
	 * The default constructor opens a window that is 500 pixels by 500 pixels.
	 */
	public GFX() {
		this(500, 500);
	}

	/**
	 * This constructor allows you to configure the size of your drawing app.
	 * 
	 * @param width  The width of the window in pixels.
	 * @param height The height of the window in pixels.
	 */
	public GFX(int width, int height) {
		this.view = new GFXView(width, height, this);
		this.events = new EventManager(this);
	}

	/**
	 * Access the current width of the window.
	 * 
	 * @return The width of the window.
	 */
	public final int getWidth() {
		return view.getWidth();
	}

	/**
	 * Access the current height of the window.
	 * 
	 * @return The height of the window.
	 */
	public final int getHeight() {
		return view.getHeight();
	}

	/**
	 * Access the current size of the window as a {@link Rectangle2D} object.
	 * 
	 * @return The window as (0,0,{@link #getWidth()}, {@link #getHeight()}).
	 */
	public final Rectangle2D windowAsRectangle() {
		return new Rectangle2D.Double(0, 0, getWidth(), getHeight());
	}

	/**
	 * Get the last mouseClick and make sure we only retrieve it once.
	 * 
	 * @return The {@link IntPoint} last clicked or null.
	 */
	public final IntPoint processClick() {
		return events.lastClick.getAndSet(null);
	}

	/**
	 * Get the location of the mouse, or null if not in the window.
	 * 
	 * @return a {@link IntPoint} object with x and y information.
	 */
	public final IntPoint getMouseLocation() {
		return events.lastMousePosition.get();
	}

	/**
	 * Gets the state of a key without modifying that state.
	 * 
	 * @param code the keycode, see {@link KeyEvent}, e.g.,
	 *             {@link KeyEvent#VK_SPACE}
	 * @return true if the key is pressed.
	 */
	public final boolean isKeyDown(int code) {
		return events.keyState.containsKey(code);
	}

	/**
	 * Gets the state of a key and "unpresses" it.
	 * 
	 * @param code the keycode, see {@link KeyEvent}, e.g.,
	 *             {@link KeyEvent#VK_SPACE}
	 * @return true if the key is down, false if it was not down.
	 */
	public final boolean processKey(int code) {
		return events.keyState.remove(code) != null;
	}

	/**
	 * Actually open the window (private method!)
	 */
	private void setupSwing() {
		synchronized (this) {
			try {
				SwingUtilities.invokeAndWait(() -> {
					// Build the window which has one thing in it, our 'view' object.
					frame = new JFrame(this.getClass().getSimpleName());
					JPanel panel = new JPanel(new BorderLayout());
					panel.add(view, BorderLayout.CENTER);
					frame.setContentPane(panel);
					frame.pack();
					frame.setResizable(false);
					frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

					// Connect event manager to Java's systems:
					frame.addWindowListener(events);
					frame.addKeyListener(events);
					view.addMouseListener(events);
					view.addMouseMotionListener(events);

					frame.setVisible(true);
					running.set(true);
				});
			} catch (InterruptedException | InvocationTargetException e) {
				throw new RuntimeException("Couldn't start up GUI due to threading bug", e);
			}

		}
	}

	/**
	 * Open the window and run your {@link #draw} method over and over!
	 */
	public final void start() {
		this.setupSwing();

		long lastTime = System.nanoTime();
		try {
			while (running.get()) {
				final int delay_ms = 1000 / FPS;
				frame.requestFocusInWindow();
				final long now = System.nanoTime();
				update((now - lastTime) / 1e9);
				lastTime = now;
				view.render();
				try {
					Thread.sleep(delay_ms);
				} catch (InterruptedException e) {
					e.printStackTrace(System.err);
				}
			}
		} finally {
			this.stop();
			frame.setVisible(false);
		}
	}

	/**
	 * Start the window in the background.
	 * You probably want {@link #start} instead.
	 */
	public final void startViewer() {
		Thread already = null;
		synchronized (this) {
			already = this.updater.get();
		}
		if (already == null) {
			GFX app = this;
			this.setupSwing();
			Thread updater = new Thread(app::start);
			this.updater.set(updater);
			updater.start();
		}
	}

	/**
	 * Change the title of your window.
	 * 
	 * @param title What to change the title to.
	 */
	public final synchronized void setTitle(String title) {
		this.frame.setTitle(title);
	}

	/**
	 * Tell the window to close (e.g., if you want to make a game over
	 * functionality)
	 */
	public final void stop() {
		running.set(false);
		Thread maybeUpdater = updater.getAndSet(null);
		if (maybeUpdater != null) {
			try {
				maybeUpdater.join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Optional method to override if you want to separate updates from drawing, or
	 * to make time-perfect animations.
	 * 
	 * @param dt The time since we last called your update method, in seconds.
	 */
	public void update(double dt) {
	}

	/**
	 * Override this method in order to draw your own animations.
	 * 
	 * @param g A standard Java {@link Graphics2D} object that can make a variety of
	 *          shapes and colors.
	 */
	public abstract void draw(Graphics2D g);
}