package me.jjfoley.gfx.ex;

import java.awt.Color;
import java.awt.Graphics2D;

import me.jjfoley.gfx.GFX;
import me.jjfoley.gfx.TextBox;

public class ConcurrencyEx extends GFX {
	int count = 0;
	TextBox box;
	
	Thread t1;
	Thread t2;
	
	public ConcurrencyEx() {
		this.count = 0;
		
		box = new TextBox("Hello World");
		box.setFontSize(72.0);
		box.setColor(Color.black);
		
		startThreads();
	}
	
	public void startThreads() {
		final int N = 1_000;
		GFX app = this;
		Runnable upTask = () -> {
			for(int i=0; i<N; i++) {
//				synchronized(app) {
					count += 1;
//				}
				sleep(0);
			}
		};
		Runnable downTask = () -> {
			for (int i=0; i<N; i++) {
				//synchronized(app) {
					count -= 1;
				//}
				sleep(0);
			}
		};
		
		t1 = new Thread(upTask);
		t2 = new Thread(downTask);
		t1.start();
		t2.start();
	}
	
	@Override
	public void draw(Graphics2D g) {
		// white background
		g.setColor(Color.white);
		g.fill(this.windowAsRectangle());

		// centered black text
		box.centerInside(this.windowAsRectangle());
		box.setString(""+count);
		box.draw(g);
		
		// reset on click
		if (this.processClick() != null) {
			this.count = 0;
			startThreads();
		}
	}
	
	public static void main(String[] args) {
		GFX app = new ConcurrencyEx();
		app.start();
	}

	private static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
