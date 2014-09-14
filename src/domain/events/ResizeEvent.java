package domain.events;

public class ResizeEvent extends Event {

	private final int rows;
	private final int columns;

	public ResizeEvent(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @return the columns
	 */
	public int getColumns() {
		return columns;
	}
}
