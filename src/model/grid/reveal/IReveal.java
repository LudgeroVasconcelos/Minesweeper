package model.grid.reveal;

import java.awt.Point;
import java.util.Map;

import model.grid.Square;

/**
 * This interface defines the contracts for the strategy to reveal the squares.
 * 
 * @author Ludgero
 * 
 */
public interface IReveal {

	/**
	 * Determines and reveals squares. Requires that the square at (x, y) is not
	 * mined nor marked.
	 * 
	 * @param grid
	 *            The grid of squares used to work out which squares are to
	 *            reveal
	 * @param x
	 *            The x coordinate of the clicked square
	 * @param y
	 *            The y coordinate of the clicked square
	 * 
	 * @return A map containing the positions of the revealed squares and their
	 *         corresponding number of mines around
	 */
	public Map<Point, Integer> revealSquares(Square[][] grid, int x, int y);
}
