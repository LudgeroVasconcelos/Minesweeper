package view.swing;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.UIManager;

import minesweeper.MineProperties;

/**
 * The main UI class.
 * 
 * @author Ludgero
 * 
 */
@SuppressWarnings("serial")
public class UiFacade extends JFrame {

	private GridPanel grid;
	private UpperPanel upper;
	private Menu menu;

	/**
	 * Constructs a new game window
	 */
	public UiFacade() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// just do nothing
			e.printStackTrace();
		}
		grid = new GridPanel(MineProperties.INSTANCE.ROWS,
				MineProperties.INSTANCE.COLUMNS);
		upper = new UpperPanel(MineProperties.INSTANCE.NUMBER_OF_MINES);
		menu = new Menu();

		setJMenuBar(menu);
		setLayout(new BorderLayout());
		setTitle("Minesweeper");
		setIconImage(MineProperties.INSTANCE.MINE_IMAGE);
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
	 *            and the corresponding number of neighboring mines of each
	 *            square
	 */
	public void revealButtons(Iterable<Entry<Point, Integer>> revealedSquares) {
		grid.revealButtons(revealedSquares);
	}

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
	public void toggleFlag(int x, int y, boolean flagged, int flaggedMines) {
		grid.toggleFlag(x, y, flagged);
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
	public void gameOver(int x, int y, Iterable<Point> mines,
			Iterable<Point> mistakenMarks) {
		grid.gameOver(x, y, mines, mistakenMarks);
		upper.gameOver();
	}

	/**
	 * Sets the timer.
	 */
	public void setTime(int time) {
		upper.setTime(time);
	}

	/**
	 * When only mined squares are left to be revealed, i.e., the game is won,
	 * this method will flag every square not yet flagged. It also stops the
	 * timer, sets the number of remaining mines to zero and paints a winning
	 * image on the button.
	 * 
	 * @param unmarkedSquares
	 *            The positions of the squares to be flagged
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
	 * Adds listeners to UI.
	 * 
	 * @param listener
	 *            The listener to be added
	 */
	public void addListener(EventListener listener) {

		if(listener instanceof MouseListener){
			grid.addMouseListener((MouseListener)listener);	
		}
		else if(listener instanceof ActionListener){
			upper.addClearListener((ActionListener)listener);
			menu.addListeners((ActionListener)listener);
		}
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
