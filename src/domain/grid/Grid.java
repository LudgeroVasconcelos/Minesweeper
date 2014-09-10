package domain.grid;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import minesweeper.MineProperties;
import domain.events.ClearEvent;
import domain.events.Event;
import domain.events.GameOverEvent;
import domain.events.GameWonEvent;
import domain.events.SquareRevealedEvent;
import domain.events.StartGameEvent;
import domain.events.ToggleMarkEvent;
import domain.fill.IFill;
import domain.reveal.IReveal;

public class Grid extends Observable implements IGrid {

	private Square[][] grid;
	private boolean filled;
	private boolean gameEnded;
	private IFill filler;
	private IReveal ar;
	private int flaggedSquares;
	private int revealedSquares;

	public Grid(IFill filler, IReveal ar) {
		this.filler = filler;
		this.ar = ar;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see domain.grid.IGrid#reveal(int, int)
	 */
	@Override
	public void reveal(int x, int y) {
		if (grid[x][y].isMarked() || grid[x][y].isRevealed())
			return;

		Event event;
		if (grid[x][y] instanceof MinedSquare) {
			event = new GameOverEvent(x, y, getMines());
			gameEnded = true;

		} else {
			Map<Point, Integer> revealed = ar.revealSquares(grid, x, y);
			event = new SquareRevealedEvent(x, y, revealed.entrySet());
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

			if (grid[x][y].isMarked())
				flaggedSquares++;
			else
				flaggedSquares--;

			fireChangedEvent(new ToggleMarkEvent(x, y, flaggedSquares));
		}
	}

	@Override
	public boolean hasEnded() {
		return gameEnded;
	}

	private List<Point> getSquaresLeft() {
		List<Point> unmarked = new ArrayList<Point>();

		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
				if (!grid[i][j].isMarked() && !grid[i][j].isRevealed())
					unmarked.add(new Point(i, j));

		return unmarked;
	}

	private List<Point> getMines() {
		List<Point> mines = new ArrayList<Point>();

		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
				if (grid[i][j] instanceof MinedSquare)
					mines.add(new Point(i, j));

		return mines;
	}
	
	private void fireChangedEvent(Event event) {
		setChanged();
		notifyObservers(event);
	}
}
