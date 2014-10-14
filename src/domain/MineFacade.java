package domain;

import java.util.Observer;

import minesweeper.Difficulty;

/**
 * The facade with the operations to play the game.
 * 
 * @author Ludgero
 * 
 */
public interface MineFacade {

	/**
	 * Clears the grid. The game is restarted.
	 */
	public void clearGrid();

	/**
	 * Reveals the square at (x, y). Multiple squares are revealed if the square
	 * has no mines surrounding it. If the square is mined, the game is over.
	 * 
	 * This method will do nothing if: 1 - the square is marked. 2 - the square
	 * is already revealed. 3 - the game is already over.
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
	 * Sets a new difficulty to the game. Changes the current size of the grid
	 * and the number of mines it contains to meet the new difficulty rules
	 * given by diff. After this, the grid is cleared.
	 * 
	 * @param diff
	 *            The new difficulty to be set
	 */
	public void setDifficulty(Difficulty diff);

	/**
	 * Adds an observer to the grid that gets notified whenever the state of the
	 * grid is changed.
	 * 
	 * @param observer
	 *            The observer to be added.
	 */
	public void addObserver(Observer observer);
}
