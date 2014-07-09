package domain.grid;

import java.util.Observable;

import javax.naming.OperationNotSupportedException;

import domain.events.Event;
import domain.events.GameOverEvent;
import domain.events.SquareRevealedEvent;
import domain.events.ToggleMarkEvent;
import domain.fill.IFill;

public class Grid extends Observable implements IGrid {

	private Square[][] grid;
	private boolean filled;
	private IFill filler;
	
	public Grid(IFill filler) {
		this.filler = filler;
		clearGrid();
	}
	
	@Override
	public boolean isFilled(){
		return filled;
	}

	@Override
	public void fill(int x, int y) {
		filled = true;
		this.grid = filler.fillGrid(x, y);
	}

	@Override
	public void clearGrid() {
		filled = false;
		grid = null;
	}

	/* (non-Javadoc)
	 * @see domain.grid.IGrid#reveal(int, int)
	 */
	@Override
	public void reveal(int x, int y) {
		grid[x][y].reveal();

		Event event;
		if (isMined(x, y)) {
			event = new GameOverEvent(x, y, filler.getListOfMines());
		} else {
			event = new SquareRevealedEvent(x, y,
					((NormalSquare) grid[x][y]).getNumOfMinesAround());
		}

		fireChangedEvent(event);
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

	private boolean isMined(int x, int y) {
		return grid[x][y] instanceof MinedSquare;
	}

	@Override
	public int getNumOfMinesAround(int x, int y) throws OperationNotSupportedException {
		if (grid[x][y] instanceof NormalSquare) {
			NormalSquare ns = (NormalSquare) grid[x][y];
			return ns.getNumOfMinesAround();
		} else {
			throw new OperationNotSupportedException(
					"Cannot obtain number of mines around a mined square");
		}

	}

	private void fireChangedEvent(Event event) {
		setChanged();
		notifyObservers(event);
	}

}
