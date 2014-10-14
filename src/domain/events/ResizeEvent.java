package domain.events;

/**
 * Represents the resize event which occurs when the difficulty is changed.
 * 
 * @author Ludgero
 * 
 */
public class ResizeEvent extends Event {

	private final int rows;
	private final int columns;

	/**
	 * Constructs and initializes a new resize event with the specified number
	 * of rows and columns.
	 * 
	 * @param rows
	 *            The number of rows the grid will have after resizing
	 * @param columns
	 *            The number of columns the grid will have after resizing
	 */
	public ResizeEvent(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}

	/**
	 * @return the number of rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @return the number of columns
	 */
	public int getColumns() {
		return columns;
	}
}
