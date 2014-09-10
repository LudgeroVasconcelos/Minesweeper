package domain.reveal;

import java.awt.Point;
import java.util.Map;

import domain.grid.Square;

public interface IReveal {

	/**
	 * Determines, reveals and returns all squares that got revealed. Requires x
	 * and y not to be a mined nor a marked square
	 * 
	 * @param grid
	 * @param x
	 * @param y
	 * @return
	 */
	public Map<Point, Integer> revealSquares(Square[][] grid,
			int x, int y);
}
