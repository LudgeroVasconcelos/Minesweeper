package ui.view.swing;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Map.Entry;

import javax.swing.JFrame;

import minesweeper.MineProperties;

/**
 * The main UI class.
 * 
 * @author Ludgero
 *
 */
@SuppressWarnings("serial")
public class MineFrame extends JFrame {

	private GridPanel grid;
	private UpperPanel upper;
	private MineMenu menu;

	/**
	 * Constructs a new game window
	 */
	public MineFrame() {
		grid = new GridPanel(MineProperties.INSTANCE.ROWS,
				MineProperties.INSTANCE.COLUMNS);
		// the upper panel contains the smile button, current number of
		// remaining mines and a timer
		upper = new UpperPanel(MineProperties.INSTANCE.NUMBER_OF_MINES);
		menu = new MineMenu();

		setJMenuBar(menu);
		setLayout(new BorderLayout());
		setTitle("Minesweeper");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(grid, BorderLayout.CENTER);
		add(upper, BorderLayout.NORTH);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Shows the revealed squares.
	 * 
	 * @param revealedSquares
	 *            The Iterable containing the positions of the revealed squares
	 *            and the corresponding number of neighboring mines of each square
	 */
	public void revealButtons(Iterable<Entry<Point, Integer>> revealedSquares) {
		grid.revealButtons(revealedSquares);
	}

	/**
	 * Paints a flag over the square at (x, y) if it is not already painted,
	 * otherwise, the flag is erased.
	 * 
	 * @param x
	 *            The x coordinate of the square to be painted
	 * @param y
	 *            The y coordinate of the square to be painted
	 * @param flaggedMines
	 *            The number of flags on the grid after the painting
	 */
	public void toggleFlag(int x, int y, int flaggedMines) {
		grid.toggleFlag(x, y);
		upper.setRemainingMines(MineProperties.INSTANCE.NUMBER_OF_MINES
				- flaggedMines);
	}

	/**
	 * Clears the grid. Resets timer and the number of mines left.
	 */
	public void clearGrid() {
		grid.clear();
		upper.clear();
	}

	/**
	 * Shows where all the mines were hiding and which one got exploded. Paints
	 * a sad face image on the button. Stops the timer.
	 * 
	 * @param x
	 *            The x coordinate of the exploded mine
	 * @param y
	 *            The y coordinate of the exploded mine
	 * @param mines
	 *            The positions of the mines to be shown
	 */
	public void gameOver(int x, int y, Iterable<Point> mines) {
		grid.gameOver(x, y, mines);
		upper.gameOver();
	}

	/**
	 * Adds a mouse listener to the grid.
	 * 
	 * @param squaresListener
	 *            The mouse listener to be added
	 */
	public void addSquaresListener(MouseListener squaresListener) {
		grid.addMouseListener(squaresListener);
	}

	/**
	 * Adds a listener to clear the grid. The listener is added to the smile
	 * button and to the 'new' item menu.
	 * 
	 * @param clearGrid
	 *            The listener to be added
	 */
	public void addClearListener(ActionListener clearGrid) {
		upper.addClearListener(clearGrid);
		menu.addClearListener(clearGrid);
	}

	/**
	 * Starts the timer.
	 */
	public void startGame() {
		upper.startGame();
	}

	/**
	 * When only mined squares are left to be revealed, i.e., the game is won,
	 * this method will paint a flag on every square not yet painted. It also
	 * stops the timer, sets the number of remaining mines to zero and paints a
	 * winning image on the button.
	 * 
	 * @param unmarkedSquares
	 *            The positions of the squares that need to be painted with a
	 *            flag
	 */
	public void gameWon(Iterable<Point> unmarkedSquares) {
		grid.gameWon(unmarkedSquares);
		upper.gameWon();
	}

	/**
	 * Paints a surprised image on the button.
	 */
	public void mousePressed() {
		upper.mousePressed();
	}

	/**
	 * Paints a normal state image on the button.
	 */
	public void mouseReleased() {
		upper.mouseReleased();
	}

	/**
	 * Adds listeners for all menu items except the 'new' one which must be
	 * added with the addClearListener method. The listeners will be fired when
	 * a menu item is clicked.
	 * 
	 * @param quitListener
	 *            The action listener for the 'quit' menu item
	 * @param diffListener
	 *            The action listener for the 3 levels of difficulty
	 */
	public void addMenuListeners(ActionListener quitListener,
			ActionListener diffListener) {

		menu.addQuitListener(quitListener);
		menu.addDiffListener(diffListener);
	}

	/**
	 * Resizes the grid to fit the given number of rows and columns.
	 * 
	 * @param rows
	 *            The number of rows the resized grid will have
	 * @param columns
	 *            The number of columns the resized grid will have
	 */
	public void resizeGrid(int rows, int columns) {
		grid.resize(rows, columns);
		pack();
		repaint();
		setLocationRelativeTo(null);
	}
}
