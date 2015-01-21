package domain.fill;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import domain.grid.MinedSquare;
import domain.grid.SafeSquare;
import domain.grid.Square;

/**
 * This class specifies an implementation for filling the grid. This
 * implementation places the mines randomly on the grid except that it
 * guarantees there will be at least one square separating the mines and the
 * first revealed square (all 8 directions).
 * 
 * @author Ludgero
 */
public class FillRandom implements IFill {

	private int rows;
	private int columns;
	private int numberOfMines;

	/**
	 * Constructs a FillRandom object which will be capable of filling a grid
	 * with the specified number of rows, columns and mines.
	 * 
	 * @param rows
	 *            The number of rows of the grid to be filled
	 * @param columns
	 *            The number of columns of the grid to be filled
	 * @param numberOfMines
	 *            The number of mines to be placed on the grid
	 */
	public FillRandom(int rows, int columns, int numberOfMines) {
		this.rows = rows;
		this.columns = columns;
		this.numberOfMines = numberOfMines;
	}

	@Override
	public Square[][] fillGrid(int x, int y) {
		Square[][] grid = new Square[rows][columns];

		// places mines
		Iterable<Point> mines = setMines(grid, x, y);

		// sets the numbers of surrounding mines on each no mined square
		setNormalSquares(grid, mines);

		return grid;
	}

	/**
	 * Randomly determines the mines positions and places them assuring there
	 * will be at least one square separating the mines and the first revealed
	 * square (all 8 directions).
	 * 
	 * @param grid
	 *            The grid where the mines will be placed.
	 * @param x
	 *            The x coordinate of the first revealed square
	 * @param y
	 *            The y coordinate of the first revealed square
	 *            
	 * @return An iterable containing the determined mines positions
	 */
	private Iterable<Point> setMines(Square[][] grid, int x, int y) {
		Set<Point> generated = new LinkedHashSet<Point>();
		Random r = new Random();
		List<Point> pointsAround = getPointsAround(x, y);
		int max = rows * columns;

		while (generated.size() < numberOfMines) {
			int randomInt = r.nextInt(max);
			int i = randomInt / columns;
			int j = randomInt - (columns * i);

			Point p = new Point(i, j);
			if (!pointsAround.contains(p)) {
				generated.add(p);
				grid[i][j] = new MinedSquare();
			}
		}
		return generated;
	}

	/**
	 * Determines and sets the number of mines surrounding each no mined square.
	 * 
	 * @param grid
	 *            The grid in which the numbers are to be set
	 * @param mines
	 *            The mines positions
	 */
	private void setNormalSquares(Square[][] grid, Iterable<Point> mines) {
		int[][] nums = getNums(mines);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (nums[i][j] >= 0)
					grid[i][j] = new SafeSquare(nums[i][j]);
			}
		}
	}

	/**
	 * Determines the number of mines surrounding each square given a list with
	 * all mine positions.
	 * 
	 * @param mines
	 *            The list of mines positions
	 *            
	 * @return a two-dimensional int array, with the same size of the grid,
	 *         containing the number of mines surrounding each square. If (x, y)
	 *         is a mined square, the number will be negative.
	 */
	private int[][] getNums(Iterable<Point> mines) {
		int[][] nums = new int[rows][columns];

		for (Point point : mines) {
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

	/**
	 * Determines and returns a list of (x, y) coordinates surrounding a given
	 * (x, y) coordinate.
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 *            
	 * @return a list containing the (x, y) coordinates as specified
	 */
	private List<Point> getPointsAround(int x, int y) {
		List<Point> list = new ArrayList<Point>();

		for (int k = x - 1; k <= x + 1; k++) {
			for (int l = y - 1; l <= y + 1; l++) {
				list.add(new Point(k, l));
			}
		}
		return list;
	}
}
