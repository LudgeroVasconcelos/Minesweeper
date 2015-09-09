package model;

import java.util.Observer;

/**
 * The facade with the operations to play the game.
 * 
 * @author Ludgero
 * 
 */
public interface IMineFacade {

	/**
	 * Clears the grid. The game is restarted.
	 */
	public void clearGrid();

	/**
	 * Reveals the square at (x, y). Multiple squares are revealed if the square
	 * has no mines surrounding it. If the square is mined, the game is over.
	 * 
	 * <pre>
	 * This method will do nothing if:
	 * -The square is marked. 
	 * -The square is already revealed.
	 * -The game is already over.
	 * </pre>
	 * 
	 * @param x
	 *            The x coordinate of the square to be revealed
	 * @param y
	 *            The y coordinate of the square to be revealed
	 */
	public void reveal(int x, int y);

	/**
	 * Toggles the square at (x, y) between normal, flagged or question mark
	 * 
	 * @param x
	 *            The x coordinate of the square
	 * @param y
	 *            The y coordinate of the square
	 */
	public void toggle(int x, int y);
	

	public void setToggleMode(boolean questioMarkActive);

	/**
	 * Sets a new predefined difficulty to the game. Changes the current size of
	 * the grid and the number of mines it contains to meet the new difficulty
	 * rules given by diff. The grid is cleared after resizing.
	 * 
	 * @param diff
	 *            The new difficulty to be set
	 */
	public void setDifficulty(Difficulty diff);

	/**
	 * Sets a new custom difficulty to the game. Changes the current size of the
	 * grid and the number of mines it contains to meet the new difficulty rules
	 * given by this method parameters. The grid is cleared after resizing.
	 * 
	 * @param rows
	 *            The number of rows
	 * @param columns
	 *            The number of columns
	 * @param mines
	 *            The number of mines
	 */
	public void setDifficulty(int rows, int columns, int mines);

	/**
	 * Adds an observer to the grid that gets notified whenever the state of the
	 * grid is changed.
	 * 
	 * @param observer
	 *            The observer to be added.
	 */
	public void addObserver(Observer observer);

	/**
	 * Retrieves the time lapsed since the start of the game. Zero is returned
	 * if the game has not started.
	 * 
	 * @return the time as specified
	 */
	public int getCurrentTime();
}
