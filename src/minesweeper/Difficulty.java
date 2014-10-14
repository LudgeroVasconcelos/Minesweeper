package minesweeper;

/**
 * This enumerator specifies the classes of difficulty of the game as well as
 * the number of mines, rows and columns for each one.
 * 
 * @author Ludgero
 * 
 */
public enum Difficulty {
	BEGINNER(9, 9, 10), INTERMEDIATE(16, 16, 40), EXPERT(16, 30, 99);

	private final int rows;
	private final int columns;
	private final int mines;

	private Difficulty(int rows, int columns, int mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
	}

	/**
	 * @return The number of rows of the specified Difficulty
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @return The number of columns of the specified Difficulty
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * @return The number of mines of the specified Difficulty
	 */
	public int getMines() {
		return mines;
	}
}
