package minesweeper;

import java.awt.FontMetrics;
import java.awt.Point;

/**
 * This class provides utility operations globally.
 * 
 * @author Ludgero
 * 
 */
public final class Util {

	private Util() {
	};

	/**
	 * Converts mouse coordinates to square coordinates on the grid.
	 * 
	 * @param coordX
	 *            The x coordinate to be converted
	 * @param coordY
	 *            The y coordinate to be converted
	 * @return a point (x, y) representing the coordinates of the square on the
	 *         grid
	 */
	public static Point getPoint(int coordX, int coordY) {
		int y = coordX / MineProperties.INSTANCE.BUTTON_WIDTH;
		int x = coordY / MineProperties.INSTANCE.BUTTON_HEIGHT;
		return new Point(x, y);
	}

	/**
	 * Determines the coordinates where the given string must be drawn so that
	 * it will be centered on the given square position.
	 * 
	 * @param x
	 *            The x coordinate of the square
	 * @param y
	 *            The y coordinate of the square
	 * @param fm
	 *            The fontMetrics object of the font used
	 * @param string
	 *            The string to be drawn
	 * @return The center coordinates as specified
	 */
	public static Point getTextCenterPos(int x, int y, FontMetrics fm,
			String string) {
		int textWidth = fm.stringWidth(string);
		int textHeight = fm.getHeight();

		int coordX = x + (MineProperties.INSTANCE.BUTTON_WIDTH - textWidth) / 2;
		int coordY = y
				+ ((MineProperties.INSTANCE.BUTTON_HEIGHT - textHeight) / 2)
				+ fm.getAscent();

		return new Point(coordX, coordY);
	}

	/**
	 * Determines if x and y are valid square coordinates.
	 * 
	 * @param x
	 *            The x coordinate to be validated
	 * @param y
	 *            The y coordinate to be validated
	 * @return true if they are valid coordinates; false otherwise
	 */
	public static boolean isValid(int x, int y) {
		return x >= 0 && x < MineProperties.INSTANCE.ROWS && y >= 0
				&& y < MineProperties.INSTANCE.COLUMNS;
	}
}
