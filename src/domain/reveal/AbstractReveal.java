package domain.reveal;

import java.awt.Point;

import domain.grid.IGrid;

public abstract class AbstractReveal {

	final IGrid grid;

	public AbstractReveal(IGrid grid) {
		this.grid = grid;
	}

	/**
	 * 
	 * @param x
	 *            The x position of the square clicked by the user
	 * @param y
	 *            The y position of the square clicked by the user
	 * @return an iterable of squares that should be revealed
	 */
	public abstract Iterable<Point> getSquaresToReveal(int x, int y);
}
