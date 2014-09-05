package domain.reveal;

import java.awt.Point;

import domain.grid.Square;

public interface IReveal {

	/**
	 * Determines and returns all squares that must be revealed. Requires x and
	 * y not to be a mined nor a marked square
	 * @param grid
	 * @param x
	 * @param y
	 * @return
	 */
	public Iterable<Point> getSquaresToReveal(Square[][] grid, int x, int y);
}
