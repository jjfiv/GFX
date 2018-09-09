# GFX
Smith College / CSC212 / GFX library to simplify Graphics2D access.

## Motivation

Getting a drawable canvas in Swing can require many steps, and event handlers and the EventQueue thread are full of traps for newcomers. This library simplifies all that:

```java
import java.awt.Color;
import java.awt.Graphics2D;

import me.jjfoley.gfx.GFX;

public class MyDrawing extends GFX{
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect(0, 0, 200, 200);
	}

	public static void main(String[] args) {
		MyDrawing app = new MyDrawing();
		app.start();
	}
}
```

## Use from Maven
Jitpack.io?
