package model.grid;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.Difficulty;
import model.MineTimer;
import model.events.ClearEvent;
import model.events.Event;
import model.events.GameLostEvent;
import model.events.GameWonEvent;
import model.events.ResizeEvent;
import model.events.RevealEvent;
import model.events.StartGameEvent;
import model.events.ToggleMarkEvent;
import model.grid.fill.FillRandom;
import model.grid.fill.IFill;
import model.grid.reveal.IReveal;

/**
 * This class represents the grid of the game, contains safe and mined squares
 * and operates on them.
 * 
 * @author Ludgero
 * 
 */
public class Grid extends Observable {

	private Square[][] grid;
	private MineTimer timer;

	private boolean filled;
	private boolean gameEnded;

	private IFill filler;
	private IReveal revealer;

	private int flaggedSquares;
	private int revealedSquares;

	private boolean questionMarkActive;

	/**
	 * Constructs a new grid. Initializes the strategies used to fill the grid
	 * and to reveal its squares.
	 * 
	 * @param filler
	 *            A strategy to fill the grid
	 * @param revealer
	 *            A strategy to reveal the squares of the grid
	 * @param timer
	 */
	public Grid(IFill filler, IReveal revealer, MineTimer timer) {
		this.filler = filler;
		this.revealer = revealer;
		this.timer = timer;
	}

	/**
	 * Fills the grid with mines.
	 * 
	 * @param x
	 *            The x coordinate of the first clicked square
	 * @param y
	 *            The y coordinate of the first clicked square
	 */
	public void fill(int x, int y) {
		this.grid = filler.fillGrid(x, y);
		filled = true;
		timer.start();
		fireChangedEvent(new StartGameEvent());
	}

	/**
	 * Checks whether the grid is filled.
	 * 
	 * @return true if the grid is filled
	 */
	public boolean isFilled() {
		return filled;
	}

	/**
	 * Clears the grid. The game is restarted.
	 */
	public void clearGrid() {
		filled = false;
		gameEnded = false;
		grid = null;
		flaggedSquares = 0;
		revealedSquares = 0;
		timer.reset();
		fireChangedEvent(new ClearEvent());
	}

	/**
	 * Reveals the square at (x, y). Multiple squares are revealed if the square
	 * has no mines surrounding it. If the square is mined, the game is over.
	 * 
	 * @param x
	 *            The x coordinate of the square to be revealed
	 * @param y
	 *            The y coordinate of the square to be revealed
	 */
	public void reveal(int x, int y) {
		if (grid[x][y].isFlagged() || grid[x][y].isRevealed())
			return;

		Event event;
		if (grid[x][y] instanceof MinedSquare) {
			event = new GameLostEvent(x, y, getMines(), getMistakenMarks());
			endGame();
		} else {
			Map<Point, Integer> revealed = revealer.revealSquares(grid, x, y);
			event = new RevealEvent(revealed.entrySet());
			revealedSquares += revealed.size();
		}
		fireChangedEvent(event);

		if (hasWon()) {
			endGame();
			fireChangedEvent(new GameWonEvent(getSquaresLeft(), timer.getCurrentTime()));
		}
	}

	/**
	 * Marks the square at (x, y) or removes it depending on whether the square
	 * is marked or not.
	 * 
	 * @param x
	 *            The x coordinate of the square
	 * @param y
	 *            The y coordinate of the square
	 */
	public void toggle(int x, int y) {
		if (!grid[x][y].isRevealed()) {
			
			flaggedSquares = grid[x][y].isFlagged() ? flaggedSquares - 1 : flaggedSquares;
			
			grid[x][y].toggle(questionMarkActive);

			flaggedSquares = grid[x][y].isFlagged() ? flaggedSquares + 1 : flaggedSquares;

			SquareState state = grid[x][y].getState();
			fireChangedEvent(new ToggleMarkEvent(x, y, state, flaggedSquares));
		}
	}

	public void setToggleMode(boolean questioMarkActive) {
		this.questionMarkActive = questioMarkActive;
	}

	/**
	 * Checks whether the game has ended or not
	 * 
	 * @return true if the game has already ended.
	 */
	public boolean gameHasEnded() {
		return gameEnded;
	}

	/**
	 * Sets a new difficulty to the game. Changes the current size of the grid
	 * and the number of mines it contains to meet the new difficulty rules
	 * given by diff. After this, the grid is cleared.
	 * 
	 * @param diff
	 *            The new difficulty to be set
	 */
	public void setDifficulty(Difficulty diff) {

		int rows = diff.getRows();
		int columns = diff.getColumns();
		int mines = diff.getMines();

		setDifficulty(rows, columns, mines);
	}

	public void setDifficulty(int rows, int columns, int mines) {

		filler = new FillRandom(rows, columns, mines);

		fireChangedEvent(new ResizeEvent(rows, columns));
		clearGrid();
	}

	/**
	 * Determines the squares that are not marked as a mine. It is used when all
	 * safe squares are revealed, so the squares that are left to mark can be
	 * marked automatically.
	 * 
	 * @return a list containing unmarked squares positions
	 */
	private List<Point> getSquaresLeft() {
		List<Point> unmarked = new ArrayList<Point>();

		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
				if (!grid[i][j].isMarked() && !grid[i][j].isRevealed())
					unmarked.add(new Point(i, j));

		return unmarked;
	}

	/**
	 * Determines the positions of the mines on the grid. Flagged squares are
	 * not considered. This method is only called when the game is lost.
	 * 
	 * @return a list containing the mines' positions
	 */
	private List<Point> getMines() {
		List<Point> mines = new ArrayList<Point>();

		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
				if (grid[i][j] instanceof MinedSquare && !grid[i][j].isFlagged())
					mines.add(new Point(i, j));

		return mines;
	}

	/**
	 * Determines the squares' positions that are flagged but are not mined.
	 * 
	 * @return a list as specified.
	 */
	private List<Point> getMistakenMarks() {
		List<Point> mistakes = new ArrayList<Point>();

		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
				if (grid[i][j] instanceof SafeSquare && grid[i][j].isFlagged())
					mistakes.add(new Point(i, j));

		return mistakes;
	}

	/**
	 * Notifies observers of the occurrence of an event.
	 * 
	 * @param event
	 *            The event to be fired.
	 */
	private void fireChangedEvent(Event event) {
		setChanged();
		notifyObservers(event);
	}

	/**
	 * Returns the current time since the start of the game.
	 * 
	 * @return the current time, in seconds
	 */
	public int getCurrentTime() {
		return timer.getCurrentTime();
	}

	private void endGame() {
		gameEnded = true;
		timer.stop();
	}

	private boolean hasWon() {
		Difficulty dif = Difficulty.getCurrentDifficulty();

		return dif.getColumns() * dif.getRows() - revealedSquares == dif.getMines();
	}
}
