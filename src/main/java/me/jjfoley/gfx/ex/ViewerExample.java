package me.jjfoley.gfx.ex;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import me.jjfoley.gfx.GFX;
import me.jjfoley.gfx.TextBox;

/**
 * This shows an example of running in the background so you can take text-input from the console and display graphics.
 * Every time you hit enter, it updates the text in a TextBox.
 */
public class ViewerExample extends GFX {
	TextBox guess;
	
	public ViewerExample() {
		this.guess = new TextBox("Your Latest Text");
		this.guess.setColor(Color.white);
		this.guess.centerInside(this.windowAsRectangle());
	}

	@Override
	public void draw(Graphics2D g) {
		guess.draw(g);
	}
	
	public void run() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			System.out.print("> ");
			String text = reader.readLine();
			if (text == null) {
				break;
			}
			guess.setString(text);
			guess.centerInside(this.windowAsRectangle());
		}
	}

	public static void main(String[] args) throws IOException {
		ViewerExample ex = new ViewerExample();
		ex.startViewer();
		ex.run();
	}
}
