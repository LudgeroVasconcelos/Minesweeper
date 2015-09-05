package view;

import java.awt.Point;
import java.util.EventListener;
import java.util.Map.Entry;

import javax.swing.JFrame;

import com.shephertz.app42.paas.sdk.java.game.Game.Score;

/**
 * The main UI class.
 * 
 * @author Ludgero
 * 
 */
@SuppressWarnings("serial")
public abstract class UiFacade extends JFrame {

	public abstract void start();

	/**
	 * Shows the revealed squares.
	 * 
	 * @param revealedSquares
	 *            The Iterable containing the positions of the revealed squares
	 *            and the corresponding number of neighboring mines of each
	 *            square
	 */
	public abstract void revealButtons(Iterable<Entry<Point, Integer>> revealedSquares);

	/**
	 * Paints a flag over the square at (x, y) if it is not already flagged,
	 * otherwise, the flag is erased.
	 * 
	 * @param x
	 *            The x coordinate of the square to be painted
	 * @param y
	 *            The y coordinate of the square to be painted
	 * @param flagged
	 *            Indicates whether the square at (x, y) is flagged or not
	 * @param flaggedMines
	 *            The number of flags on the grid after the painting
	 */
	public abstract void toggleFlag(int x, int y, boolean flagged, int flaggedMines);

	/**
	 * Clears the grid. Resets timer and the number of mines left.
	 */
	public abstract void clearGrid();

	/**
	 * Shows where unmarked mines are hidden and which one got exploded. Paints
	 * a sad face image on the button. Stops the timer.
	 * 
	 * @param x
	 *            The x coordinate of the exploded mine
	 * @param y
	 *            The y coordinate of the exploded mine
	 * @param mines
	 *            The positions of unmarked mines to be shown
	 * @param mistakenMarks
	 *            The positions of marked squares that are not mined
	 */
	public abstract void gameLost(int x, int y, Iterable<Point> mines, Iterable<Point> mistakenMarks);

	/**
	 * When only mined squares are left to be revealed, i.e., the game is won,
	 * this method will flag every square not yet flagged. Sets the number of
	 * remaining mines to zero and paints a winning image on the button.
	 * 
	 * @param unmarkedSquares
	 *            The positions of the squares to be flagged
	 * @param score
	 *            The score of the player
	 */
	public abstract void gameWon(Iterable<Point> unmarkedSquares, int score);

	/**
	 * Sets the timer.
	 */
	public abstract void setTime(int time);

	/**
	 * Paints a surprised image on the button.
	 */
	public abstract void mousePressed();

	/**
	 * Paints a normal state image on the button.
	 */
	public abstract void mouseReleased();

	/**
	 * Resizes the grid to fit the given number of rows and columns.
	 * 
	 * @param rows
	 *            The number of rows the resized grid will have
	 * @param columns
	 *            The number of columns the resized grid will have
	 */
	public abstract void resizeGrid(int rows, int columns);

	public abstract void addGameListener(EventListener listener);

	public abstract void addLeaderboardListener(EventListener listener);

	public abstract void showRankings(int userPos, String username, int score, String level, Iterable<Score> scoreList);

	public abstract void showErrorMessage();

}
