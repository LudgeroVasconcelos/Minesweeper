package domain.grid;

import java.awt.Point;
import java.util.Observable;

import javax.naming.OperationNotSupportedException;

import domain.events.Event;
import domain.events.GameOverEvent;
import domain.events.SquareRevealedEvent;
import domain.events.ToggleMarkEvent;
import domain.random.IRandom;

public class Grid extends Observable implements IGrid {

	private Square[][] grid;

	private IRandom random;
	private int rows;
	private int columns;
	private int minesNumber;
	private Iterable<Point> listOfMines;

	public Grid(IRandom random, int rows, int columns, int minesNumber) {
		this.random = random;
		this.minesNumber = minesNumber;
		this.rows = rows;
		this.columns = columns;

		grid = new Square[rows][columns];
	}

	@Override
	public int getColumns() {
		return columns;
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public void start(int x, int y) {
		this.listOfMines = random.getMinesPosition(x, y, rows, columns,
				minesNumber);
		
		int[][] nums = getNums();
		fillGrid(nums);
	}

	@Override
	public void newGame() {
		grid = new Square[rows][columns];
	}

	@Override
	public boolean endGame() {

		return false;
	}

	private void fillGrid(int[][] nums) {
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (nums[i][j] < 0)
					grid[i][j] = new MinedSquare();
				else
					grid[i][j] = new NormalSquare(nums[i][j]);
			}
		}

	}

	private int[][] getNums() {

		int[][] nums = new int[rows][columns];

		for (Point point : listOfMines) {
			for (int i = point.x - 1; i <= point.x + 1; i++) {
				for (int j = point.y - 1; j <= point.y + 1; j++) {
					if (i >= 0 && i < rows && j >= 0 && j < columns) {
						if (i == point.x && j == point.y) // mine
							nums[i][j] = -1000;
						else
							nums[i][j]++;
					}
				}
			}
		}
		return nums;
	}

	public void reveal(int x, int y) {
		grid[x][y].reveal();

		Event event;
		if (isMined(x, y)) {
			event = new GameOverEvent(x, y, listOfMines);
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

	public void toggleMark(int x, int y) {
		grid[x][y].toggleMark();

		fireChangedEvent(new ToggleMarkEvent(x, y));
	}

	@Override
	public boolean isMined(int x, int y) {
		return grid[x][y] instanceof MinedSquare;
	}

	@Override
	public int getNumOfMinesAround(int x, int y) throws OperationNotSupportedException {
		if (grid[x][y] instanceof NormalSquare) {
			NormalSquare ns = (NormalSquare) grid[x][y];
			return ns.getNumOfMinesAround();
		} else {
			throw new OperationNotSupportedException(
					"Cannot obtain number of mines around a minedSquare");
		}

	}

	private void fireChangedEvent(Event event) {
		setChanged();
		notifyObservers(event);
	}

}
