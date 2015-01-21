package ui.view.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JPanel;

import minesweeper.MineProperties;

/**
 * This class represents the grid in the game window.
 * 
 * @author Ludgero
 * 
 */
@SuppressWarnings("serial")
public class GridPanel extends JPanel {

	private int rows;
	private int columns;
	
	private MineButton[][] buttons;
	private BufferedImage state;

	private Set<Point> flaggedButtons; // helps to display mines faster

	/**
	 * Constructs a new grid.
	 * 
	 * @param rows
	 *            The number of rows in the grid
	 * @param columns
	 *            The number of columns in the grid
	 */
	public GridPanel(int rows, int columns) {
		super();
		resize(rows, columns);
	}
	
	/**
	 * Resizes this grid and initializes a new game.
	 */
	public void resize(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		
		init();
	}
	
	/**
	 * Initializes this grid.
	 */
	private void init() {
		int width = MineProperties.INSTANCE.BUTTON_WIDTH * columns;
		int height = MineProperties.INSTANCE.BUTTON_HEIGHT * rows;

		setPreferredSize(new Dimension(width, height));
		state = new BufferedImage(width, height, Image.SCALE_SMOOTH);

		flaggedButtons = new HashSet<Point>();
		addMineButtons();
	}

	/**
	 * Adds square buttons to this grid.
	 * 
	 * @param width
	 *            The width of each square button
	 * @param height
	 *            The height of each square button
	 */
	private void addMineButtons() {
		buttons = new MineButton[rows][columns];
		int coordX = 0, coordY = 0;
		Graphics g = state.getGraphics();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				buttons[i][j] = new MineButton(coordY, coordX, g);
				coordY += MineProperties.INSTANCE.BUTTON_WIDTH;
			}
			coordX += MineProperties.INSTANCE.BUTTON_HEIGHT;
			coordY = 0;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(state, 0, 0, this);
	}

	/**
	 * Paints a flag on the square located at (x, y) if it is not already
	 * painted, otherwise, the flag is removed.
	 * 
	 * @param x
	 *            The x coordinate of the square to be painted
	 * @param y
	 *            The y coordinate of the square to be painted
	 */
	public void toggleFlag(int x, int y) {
		buttons[x][y].toggleFlag();

		Point p = new Point(x, y);
		if (!flaggedButtons.contains(p))
			flaggedButtons.add(p);
		else
			flaggedButtons.remove(p);
		repaint();
	}

	/**
	 * Paints an integer on each revealed square. This integer is the number of
	 * mines surrounding the square to be painted.
	 * 
	 * @param revealedSquares
	 *            An Iterable containing the positions of the revealed squares
	 *            and the corresponding numbers of neighboring mines
	 */
	public void revealButtons(Iterable<Entry<Point, Integer>> revealedSquares) {
		for (Entry<Point, Integer> entry : revealedSquares) {
			Point p = entry.getKey();
			int mines = entry.getValue();

			buttons[p.x][p.y].reveal(mines);
		}
		repaint();
	}

	/**
	 * Clears the grid to return to its original state.
	 */
	public void clear() {
		flaggedButtons.clear();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				buttons[i][j].clear();
			}
		}
		repaint();
	}

	/**
	 * Shows all mines' positions. A cross is painted on squares that are
	 * wrongly flagged (wrongly flagged buttons are flagged buttons that do not
	 * hide mines).
	 * 
	 * @param x
	 *            The x coordinate of the exploded mine
	 * @param y
	 *            The y coordinate of the exploded mine
	 * @param mines
	 *            An iterable containing all mines' positions
	 */
	public void gameOver(int x, int y, Iterable<Point> mines) {
		for (Point p : mines) {
			if (!buttons[p.x][p.y].isFlagged()) {
				buttons[p.x][p.y].reveal(0);
				buttons[p.x][p.y].setMine();
			} else
				// after the loop, flaggedButtons will only contain buttons that
				// are flagged but do not hide a mine
				flaggedButtons.remove(p);
		}
		// now every button in flaggedButtons is wrongly flagged (they do not
		// hide mines)
		for (Point p : flaggedButtons) {
			buttons[p.x][p.y].reveal(0);
			buttons[p.x][p.y].setMine();
			buttons[p.x][p.y].setCross();
		}
		buttons[x][y].exploded();
		buttons[x][y].setMine();

		repaint();
	}

	/**
	 * Paints flags on all squares that are not flagged and contain mines.
	 * 
	 * @param unmarkedSquares
	 *            An iterable containing the positions of the squares that are
	 *            not flagged and contain mines
	 */
	public void gameWon(Iterable<Point> unmarkedSquares) {
		for (Point p : unmarkedSquares) {
			buttons[p.x][p.y].toggleFlag();
		}
		repaint();
	}

}