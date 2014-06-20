package domain.random;

import java.awt.Point;

public interface IRandom {

	/**
	 * 
	 * @param x The x position where user clicked
	 * @param y The y position where user clicked
	 * @param rows
	 * @param columns
	 * @param minesNumber
	 * @return
	 */
	public Iterable<Point> getMinesPosition(int x, int y, int rows, int columns, int minesNumber);
}
