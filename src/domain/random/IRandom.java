package domain.random;

import java.awt.Point;

public interface IRandom {

	/**
	 * 
	 * @param x
	 *            The x position where user clicked
	 * @param y
	 *            The y position where user clicked
	 * @param rows
	 * @param columns
	 * @param minesNumber
	 * @return a generated iterable containing all mined points
	 */
	public Iterable<Point> getMinesPosition(int x, int y, int rows,
			int columns, int minesNumber);
}
