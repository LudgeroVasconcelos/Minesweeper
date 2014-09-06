package domain.grid;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import domain.events.Event;
import domain.events.GameOverEvent;
import domain.events.SquareRevealedEvent;
import domain.events.ToggleMarkEvent;
import domain.fill.IFill;
import domain.reveal.IReveal;

public class Grid extends Observable implements IGrid {

	private Square[][] grid;
	private boolean filled;
	private IFill filler;
	private IReveal ar;

	public Grid(IFill filler, IReveal ar) {
		this.filler = filler;
		this.ar = ar;

		clearGrid();
	}

	@Override
	public boolean isFilled() {
		return filled;
	}

	@Override
	public void fill(int x, int y) {
		this.grid = filler.fillGrid(x, y);
		filled = true;
	}

	@Override
	public void clearGrid() {
		filled = false;
		grid = null;
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

		} else {
			Iterable<Point> squaresToReveal = ar.getSquaresToReveal(grid, x, y);
			Map<Point, Integer> revealedSquares = new HashMap<Point, Integer>();

			for (Point p : squaresToReveal) {
				grid[p.x][p.y].reveal();

				int mines = ((NormalSquare) grid[p.x][p.y])
						.getNumOfMinesAround();

				revealedSquares.put(p, mines);
			}

			event = new SquareRevealedEvent(x, y, revealedSquares.entrySet());
		}

		fireChangedEvent(event);
	}

	private boolean[][] getMines() {
		boolean[][] mines = new boolean[grid.length][grid[0].length];

		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
				if (grid[i][j] instanceof MinedSquare)
					mines[i][j] = true;

		return mines;
	}

	@Override
	public boolean isRevealed(int x, int y) {
		return grid[x][y].isRevealed();
	}

	@Override
	public boolean isMarked(int x, int y) {
		return grid[x][y].isMarked();
	}

	@Override
	public void toggleMark(int x, int y) {
		grid[x][y].toggleMark();

		fireChangedEvent(new ToggleMarkEvent(x, y));
	}

	private void fireChangedEvent(Event event) {
		setChanged();
		notifyObservers(event);
	}

}
