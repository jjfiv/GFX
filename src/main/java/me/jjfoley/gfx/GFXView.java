package me.jjfoley.gfx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

class GFXView extends Canvas {
	private static final long serialVersionUID = 1L;
	private final GFX app;
	protected Dimension size;

	public GFXView(int width, int height, GFX app) {
		this.defineSize(width, height);
		this.app = app;
	}

	protected final void defineSize(int width, int height) {
		this.size = new Dimension(width, height);
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
	}

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