package domain.events;

/**
 * The event representing the act of toggling the mark on a square.
 * 
 * @author Ludgero
 * 
 */
public class ToggleMarkEvent extends SquareEvent {

	private int flaggedMines;

	/**
	 * Constructs a new toggle mark event initialized with the specified
	 * coordinates and the number of current flagged mines.
	 * 
	 * @param x
	 *            The x coordinate of the toggled square
	 * @param y
	 *            The y coordinate of the toggled square
	 * @param flaggedMines
	 *            The current number of flagged mines
	 */
	public ToggleMarkEvent(int x, int y, int flaggedMines) {
		super(x, y);
		this.flaggedMines = flaggedMines;
	}

	/**
	 * @return The current number of flagged mines
	 */
	public int getNumberOfFlaggedMines() {
		return flaggedMines;
	}
}
