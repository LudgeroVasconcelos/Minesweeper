package model.events;

import model.grid.SquareState;

/**
 * The event representing the act of toggling the mark on a square.
 * 
 * @author Ludgero
 * 
 */
public class ToggleMarkEvent extends SquareEvent {

	private int flaggedMines;
	private SquareState state;

	/**
	 * Constructs a new toggle event initialized with the specified
	 * coordinates and the number of current flagged mines.
	 * 
	 * @param x
	 *            The x coordinate of the toggled square
	 * @param y
	 *            The y coordinate of the toggled square
	 * @param state
	 *            The state of the toggled square. See {@link model.grid.SquareState}
	 * @param flaggedMines
	 *            The current number of flagged mines
	 */
	public ToggleMarkEvent(int x, int y, SquareState state, int flaggedMines) {
		super(x, y);

		this.state = state;
		this.flaggedMines = flaggedMines;
	}

	/**
	 * @return The current number of flagged mines
	 */
	public int getNumberOfFlaggedMines() {
		return flaggedMines;
	}

	public SquareState getState() {
		return state;
	}
}
