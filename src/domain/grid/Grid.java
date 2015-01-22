package domain.grid;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import minesweeper.Difficulty;
import minesweeper.MineProperties;
import domain.events.ClearEvent;
import domain.events.Event;
import domain.events.GameOverEvent;
import domain.events.GameWonEvent;
import domain.events.ResizeEvent;
import domain.events.RevealEvent;
import domain.events.StartGameEvent;
import domain.events.ToggleMarkEvent;
import domain.fill.FillRandom;
import domain.fill.IFill;
import domain.reveal.IReveal;

/**
 * This class represents the grid of the game, contains safe and mined squares
 * and operates on them.
 * 
 * @author Ludgero
 * 
 */
public class Grid extends Observable implements IGrid {

	private Square[][] grid;

	private boolean filled;
	private boolean gameEnded;

	private IFill filler;
	private IReveal revealer;

	private int flaggedSquares;
	private int revealedSquares;

	/**
	 * Constructs a new grid. Initializes the strategies used to fill the grid
	 * and to reveal its squares.
	 * 
	 * @param filler
	 *            A strategy to fill the grid
	 * @param revealer
	 *            A strategy to reveal the squares of the grid
	 */
	public Grid(IFill filler, IReveal revealer) {
		this.filler = filler;
		this.revealer = revealer;
	}

	@Override
	public boolean isFilled() {
		return filled;
	}

	@Override
	public void fill(int x, int y) {
		this.grid = filler.fillGrid(x, y);
		filled = true;

		fireChangedEvent(new StartGameEvent());
	}

	@Override
	public void clearGrid() {
		filled = false;
		gameEnded = false;
		grid = null;
		flaggedSquares = 0;
		revealedSquares = 0;

		fireChangedEvent(new ClearEvent());
	}

	@Override
	public void reveal(int x, int y) {
		if (grid[x][y].isMarked() || grid[x][y].isRevealed())
			return;

		Event event;
		if (grid[x][y] instanceof MinedSquare) {
			event = new GameOverEvent(x, y, getMines(), getMistakenMarks());
			gameEnded = true;

		} else {
			Map<Point, Integer> revealed = revealer.revealSquares(grid, x, y);
			event = new RevealEvent(revealed.entrySet());
			revealedSquares += revealed.size();
		}
		fireChangedEvent(event);

		if (MineProperties.INSTANCE.COLUMNS * MineProperties.INSTANCE.ROWS
				- revealedSquares == MineProperties.INSTANCE.NUMBER_OF_MINES) {
			gameEnded = true;
			fireChangedEvent(new GameWonEvent(getSquaresLeft()));
		}
	}

	@Override
	public void toggleMark(int x, int y) {
		if (!grid[x][y].isRevealed()) {
			grid[x][y].toggleMark();

			boolean marked = grid[x][y].isMarked();
			flaggedSquares = marked ? flaggedSquares + 1 : flaggedSquares - 1;

			fireChangedEvent(new ToggleMarkEvent(x, y, marked, flaggedSquares));
		}
	}

	@Override
	public boolean gameHasEnded() {
		return gameEnded;
	}

	@Override
	public void setDifficulty(Difficulty diff) {
		int rows = diff.getRows();
		int columns = diff.getColumns();
		int mines = diff.getMines();

		MineProperties.INSTANCE.setDimension(rows, columns, mines);
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
	 * Determines the positions of the mines on the grid. Marked squares are not
	 * considered. This method is only called when the game is lost.
	 * 
	 * @return a list containing the mines' positions
	 */
	private List<Point> getMines() {
		List<Point> mines = new ArrayList<Point>();

		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
				if (grid[i][j] instanceof MinedSquare && !grid[i][j].isMarked())
					mines.add(new Point(i, j));

		return mines;
	}

	/**
	 * Determines the squares' positions that are marked but are not mined.
	 * 
	 * @return a list as specified.
	 */
	private List<Point> getMistakenMarks() {
		List<Point> mistakes = new ArrayList<Point>();

		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
				if (grid[i][j] instanceof SafeSquare && grid[i][j].isMarked())
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
}
