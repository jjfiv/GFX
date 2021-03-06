# GFX [![Build Status](https://travis-ci.org/jjfiv/GFX.svg?branch=master)](https://travis-ci.org/jjfiv/GFX) [![Jitpack.io Badge](https://jitpack.io/v/jjfiv/gfx.svg)](https://jitpack.io/#jjfiv/gfx)
Smith College / CSC212 / GFX library to simplify Graphics2D access.

[JavaDoc](https://jitpack.io/com/github/jjfiv/GFX/1.6.0/javadoc/)


## Motivation

Getting a drawable canvas in Swing can require many steps, and event handlers and the EventQueue thread are full of traps for newcomers. This library simplifies all that:

```java
import java.awt.Color;
import java.awt.Graphics2D;

import me.jjfoley.gfx.GFX;

public class MyDrawing extends GFX {
	// Draw is called 60 times per second.
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect(0, 0, 200, 200);
	}

	// Running with graphics is as simple as calling ``start`` on your class.
	public static void main(String[] args) {
		MyDrawing app = new MyDrawing();
		app.start();
	}
}
```

## Use from Maven
This repository can be used via [jitpack.io](https://jitpack.io). First, add the repository:

```xml
  <repositories>
    <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
    </repository>
  </repositories>
  ```
  
 Next, add this repo:
 ```xml
     <dependency>
      <groupId>com.github.jjfiv</groupId>
      <artifactId>GFX</artifactId>
      <version>1.6.0</version>
    </dependency>
 ```
