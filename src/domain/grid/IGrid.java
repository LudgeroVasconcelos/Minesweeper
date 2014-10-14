package domain.grid;

import java.util.Observer;

import minesweeper.Difficulty;

/**
 * Specifies the operations with the grid.
 * 
 * @author Ludgero
 *
 */
public interface IGrid {

	/**
	 * Fills the grid with mines. The mines will be at least one square distant
	 * from the first clicked square.
	 * 
	 * @param x
	 *            The x coordinate of the first clicked square
	 * @param y
	 *            The y coordinate of the first clicked square
	 */
	public void fill(int x, int y);

	/**
	 * Checks whether the grid is filled.
	 * 
	 * @return true if the grid is filled
	 */
	public boolean isFilled();

	/**
	 * Clears the grid. The game is restarted.
	 */
	public void clearGrid();

	/**
	 * Reveals the square at (x, y). Multiple squares are revealed if the square
	 * has no mines surrounding it. If the square is mined, the game is over.
	 * 
	 * @param x
	 *            The x coordinate of the square to be revealed
	 * @param y
	 *            The y coordinate of the square to be revealed
	 */
	public void reveal(int x, int y);

	/**
	 * Marks the square at (x, y) or removes it depending on whether the square
	 * is marked or not.
	 * 
	 * @param x
	 *            The x coordinate of the square
	 * @param y
	 *            The y coordinate of the square
	 */
	public void toggleMark(int x, int y);

	/**
	 * Adds an observer to the grid that gets notified whenever the state of the
	 * grid is changed.
	 * 
	 * @param observer
	 *            The observer to be added.
	 */
	public void addObserver(Observer observer);

	/**
	 * Checks whether the game has ended or not
	 * 
	 * @return true if the game has already ended.
	 */
	public boolean gameHasEnded();

	/**
	 * Sets a new difficulty to the game. Changes the current size of the grid
	 * and the number of mines it contains to meet the new difficulty rules
	 * given by diff. After this, the grid is cleared.
	 * 
	 * @param diff
	 *            The new difficulty to be set
	 */
	public void setDifficulty(Difficulty diff);
}
