package me.jjfoley.gfx;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * A class that simplifies drawing of text in Java.
 * 
 * @author jfoley
 *
 */
public class TextBox {
    /**
     * A pre-cooked font that is plain, size 16.
     */
    public static Font PLAIN_FONT = new Font("Arial", Font.PLAIN, 16);
    /**
     * A pre-cooked font that is italics, size 16.
     */
    public static Font ITALICS_FONT = new Font("Arial", Font.ITALIC, 16);
    /**
     * A pre-cooked font that is bold, size 16.
     */
    public static Font BOLD_FONT = new Font("Arial", Font.BOLD, 16);

    private Font font;
    private String text;
    public Color color;
    private double x = 0;
    private double y = 0;

    /**
     * Go ahead and create a TextBox with the text "TextBox::TBD".
     * I'm expecting you'll be updating it later.
     */
    public TextBox() {
        this(0, 0, "TextBox::TBD", PLAIN_FONT, Color.red);
    }

    /**
     * Create a new TextBox with the given text in "plain" RED at (0,0).
     * 
     * @param text The message to display.
     */
    public TextBox(String text) {
        this(0, 0, text, PLAIN_FONT, Color.red);
    }

    /**
     * Create a new TextBox, filling out every parameter.
     * 
     * @param x     The x-coordinate of where to draw the text.
     * @param y     The y-coordinate of where to draw the text.
     * @param text  The message to draw.
     * @param font  The font to draw our text with.
     * @param color The color of our text.
     */
    public TextBox(double x, double y, String text, Font font, Color color) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = font;
        this.color = color;
        measure();
    }

    /**
     * This is updated only by the measure() method.
     */
    private Rectangle2D measured;

    /**
     * Change the font used for this TextBox.
     * 
     * @param font The new font to use. Try {@link #BOLD_FONT},
     *             {@link #ITALICS_FONT} for examples.
     */
    public void setFont(Font font) {
        this.font = font;
        this.measure();
    }

    /**
     * Make the font bigger or smaller, the default size is 16.
     * 
     * @param size The new size to use!
     */
    public void setFontSize(double size) {
        this.font = font.deriveFont((float) size);
        this.measure();
    }

    /**
     * Change the message in this TextBox.
     * 
     * @param text The new message.
     */
    public void setString(String text) {
        String oldText = this.text;

        this.text = text;
        // Only measure text if it's any different!
        if (!Objects.equals(oldText, text)) {
            this.measure();
        }
    }

    /**
     * If text or font ever changes, we re-measure our object here.
     */
    private void measure() {
        this.measured = FontMeasuring.getBounds(text, font);
    }

    /**
     * Change the color of the font in this TextBox.
     * 
     * @param color The new Color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Center this text (as best as possible) inside another Rectangle.
     * 
     * @param outer The rectangle in which to center this text.
     */
    public void centerInside(Rectangle2D outer) {
        Rectangle2D self = getBoundingBox();
        this.x = outer.getCenterX() - self.getWidth() / 2;
        this.y = outer.getCenterY() - self.getHeight() / 2;
    }

    /**
     * Draw this TextBox to a Graphics2D object.
     * 
     * @param g The graphics to draw to.
     */
    public void draw(Graphics2D g) {
        // Measuring is important for font because it tells us how far down letters
        // should be and how much to offset the start of the text.
        g.setColor(color);
        g.setFont(this.font);
        g.drawString(text, (float) (x + measured.getX()), (float) (y - measured.getY()));
    }

    /**
     * Get the dimensions of this text (with this font and size) as a Rectangle.
     * 
     * @return The width and height of this text.
     */
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D.Double(0, 0, measured.getWidth(), measured.getHeight());
    }

    /**
     * We trick Java into measuring text before we are draw it by creating a single
     * pixel and "drawing" to that.
     */
    private static class FontMeasuring {
        private static BufferedImage forGraphics = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        private static Graphics2D forMeasuring = forGraphics.createGraphics();

        private static synchronized Rectangle2D getBounds(String text, Font font) {
            return forMeasuring.getFontMetrics(font).getStringBounds(text, forMeasuring);
        }
    }
}
