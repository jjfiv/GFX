package me.jjfoley.gfx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

/**
 * This class wraps a {@link Canvas} so that we can draw to it.
 */
class GFXView extends Canvas {
	/**
	 * Ignore warnings from Eclipse, since all Swing things must be serializable.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * What application are we rendering?
	 */
	private final GFX app;
	/**
	 * How big are we? -- the application determines this.
	 */
	protected Dimension size;

	/**
	 * Construct a new view of a particular size for an app.
	 * @param width - width of the window in pixels.
	 * @param height - height of the window in pixels.
	 * @param app - the app object itself.
	 */
	public GFXView(int width, int height, GFX app) {
		this.defineSize(width, height);
		this.app = app;
	}

	/**
	 * Java has four set size methods! This calls {@linkplain #setSize(Dimension)},
	 * {@linkplain #setPreferredSize(Dimension)}, {@linkplain #setMinimumSize(Dimension)}, and
	 * {@linkplain #setMaximumSize(Dimension)}. This prevents resizing and getting a window that's too big or small.
	 *
	 * @param width - the pixel width of the window.
	 * @param height - the pixel height of the window.
	 */
	protected final void defineSize(int width, int height) {
		this.size = new Dimension(width, height);
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
	}

	/**
	 * This method actually updates the graphics on this canvas, using a {@linkplain BufferStrategy} and provides a
	 * {@linkplain java.awt.Graphics2D} object to {@linkplain GFX#draw}.
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			bs = getBufferStrategy();
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		app.draw(g);

		g.dispose();
		bs.show();		
	}

}