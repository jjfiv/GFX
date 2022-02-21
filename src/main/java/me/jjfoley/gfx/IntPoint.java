package me.jjfoley.gfx;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * This class represents a location, for a mouse click or other reasons. It
 * implements Java's {@link Point2D} class, which is meant for "decimal" points,
 * e.g., {@link Point2D.Double} or {@link Point2D.Float}.
 * 
 * @author jfoley
 * 
 */
public class IntPoint extends Point2D {
	/**
	 * The x coordinate of the point.
	 */
	public final int x;
	/**
	 * The y coordinate of the point.
	 */
	public final int y;

	/**
	 * A default point is at the origin.
	 */
	public IntPoint() {
		this(0, 0);
	}

	/**
	 * This constructor lets us copy a point.
	 * 
	 * @param p The point to copy.
	 */
	public IntPoint(IntPoint p) {
		this(p.x, p.y);
	}

	/**
	 * This constructor is used to get the coordinates from a {@link MouseEvent}.
	 * 
	 * @param e The MouseEvent (either movement, or click).
	 */
	IntPoint(MouseEvent e) {
		this(e.getX(), e.getY());
	}

	/**
	 * This creates a point from a specific (x,y) pair.
	 * 
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Print the x,y from inside this IntPoint.
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	/**
	 * This method allows us to tell if two IntPoints are equivalent.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o instanceof IntPoint) {
			IntPoint point = (IntPoint) o;
			return x == point.x && y == point.y;
		}
		return false;
	}

	/**
	 * This method allows us to put an IntPoint into a {@link java.util.HashMap} or
	 * {@link java.util.HashSet}.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	/**
	 * This is part of the {@link Point2D} interface.
	 */
	@Override
	public double getX() {
		return x;
	}

	/**
	 * This is part of the {@link Point2D} interface.
	 */
	@Override
	public double getY() {
		return y;
	}

	/**
	 * This is part of the {@link Point2D} interface.
	 */
	@Override
	public void setLocation(double x, double y) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Create a new IntPoint in direction (dx,dy).
	 * 
	 * @param dx - number of x steps.
	 * @param dy - number of y steps.
	 * @return the new point.
	 */
	public IntPoint translate(int dx, int dy) {
		return new IntPoint(x + dx, y + dy);
	}
}
