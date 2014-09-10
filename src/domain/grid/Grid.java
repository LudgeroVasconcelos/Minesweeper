package domain.grid;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;

import domain.events.ClearEvent;
import domain.events.Event;
import domain.events.GameOverEvent;
import domain.events.SquareRevealedEvent;
import domain.events.StartGameEvent;
import domain.events.ToggleMarkEvent;
import domain.fill.IFill;
import domain.reveal.IReveal;

public class Grid extends Observable implements IGrid {

	private Square[][] grid;
	private boolean filled;
	private boolean exploded;
	private IFill filler;
	private IReveal ar;
	private int flaggedMines;

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
		exploded = false;
		grid = null;
		flaggedMines = 0;
		fireChangedEvent(new ClearEvent());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see domain.grid.IGrid#reveal(int, int)
	 */
	@Override
	public void reveal(int x, int y) {
		if (grid[x][y].isMarked())
			return;

		grid[x][y].reveal();

		Event event;
		if (grid[x][y] instanceof MinedSquare) {
			event = new GameOverEvent(x, y, getMines());
			exploded = true;

		} else {
			Iterable<Entry<Point, Integer>> revealed = ar.revealSquares(grid,
					x, y);
			event = new SquareRevealedEvent(x, y, revealed);
		}
		fireChangedEvent(event);
	}

	private List<Point> getMines() {
		List<Point> mines = new ArrayList<Point>();

		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
				if (grid[i][j] instanceof MinedSquare)
					mines.add(new Point(i, j));

		return mines;
	}

	@Override
	public void toggleMark(int x, int y) {
		if (!grid[x][y].isRevealed()) {
			grid[x][y].toggleMark();
			flaggedMines = grid[x][y].isMarked() ? flaggedMines + 1
					: flaggedMines - 1;
			fireChangedEvent(new ToggleMarkEvent(x, y, flaggedMines));
		}
	}

	@Override
	public boolean hasExploded() {
		return exploded;
	}

	private void fireChangedEvent(Event event) {
		setChanged();
		notifyObservers(event);
	}
}
