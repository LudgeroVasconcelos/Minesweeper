package view.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Map.Entry;

import javax.swing.JPanel;

import minesweeper.MineProperties;
import model.grid.SquareState;

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
	@Override
	public void resize(int rows, int columns) {
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
				buttons[i][j] = new MineButton(coordX, coordY, g);
				coordX += MineProperties.INSTANCE.BUTTON_WIDTH;
			}
			coordY += MineProperties.INSTANCE.BUTTON_HEIGHT;
			coordX = 0;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(state, 0, 0, this);
	}

	/**
	 * Sets the state of the square at (x, y).
	 * 
	 * @param x
	 *            The x coordinate of the square to be painted
	 * @param y
	 *            The y coordinate of the square to be painted
	 * @param state
	 *            The state of the square
	 */
	public void setState(int x, int y, SquareState state) {
		buttons[x][y].clear();
		
		if (state == SquareState.FLAGGED)
			buttons[x][y].setFlag();
		else if(state == SquareState.QUESTION)
			buttons[x][y].setQuestionMark();

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

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				buttons[i][j].clear();

		repaint();
	}

	/**
	 * Shows all mines' positions. A cross is painted on flagged squares that
	 * are not mined, meaning that those squares were flagged by mistake.
	 * 
	 * @param x
	 *            The x coordinate of the exploded mine
	 * @param y
	 *            The y coordinate of the exploded mine
	 * @param mines
	 *            An iterable containing all mines' positions
	 * @param mistakenMarks
	 *            An iterable containing the positions of squares marked by
	 *            mistake
	 */
	public void gameOver(int x, int y, Iterable<Point> mines, Iterable<Point> mistakenMarks) {

		for (Point p : mines) {
			buttons[p.x][p.y].reveal(0);
			buttons[p.x][p.y].setMine();
		}

		for (Point p : mistakenMarks) {
			buttons[p.x][p.y].reveal(0);
			buttons[p.x][p.y].setMine();
			buttons[p.x][p.y].setCross();
		}
		buttons[x][y].exploded();
		repaint();
	}

	/**
	 * Paints a flag on all remaining squares that were not flagged.
	 * 
	 * @param unmarkedSquares
	 *            An iterable containing the positions of the squares that are
	 *            not flagged
	 */
	public void gameWon(Iterable<Point> unmarkedSquares) {
		for (Point p : unmarkedSquares) {
			buttons[p.x][p.y].setFlag();
		}
		repaint();
	}

}