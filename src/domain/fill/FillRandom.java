package domain.fill;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import domain.grid.MinedSquare;
import domain.grid.NormalSquare;
import domain.grid.Square;

/**
 * 
 * @author ludgero
 */
public class FillRandom implements IFill {

	private int rows;
	private int columns;
	private int numberOfMines;

	public FillRandom(int rows, int columns, int numberOfMines) {
		this.rows = rows;
		this.columns = columns;
		this.numberOfMines = numberOfMines;
	}

	@Override
	public Square[][] fillGrid(int x, int y) {
		Square[][] grid = new Square[rows][columns];

		Iterable<Point> mines = setMines(grid, x, y);
		setNormalSquares(grid, mines);

		return grid;
	}

	private void setNormalSquares(Square[][] grid, Iterable<Point> mines) {
		int[][] nums = getNums(mines);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (nums[i][j] >= 0)
					grid[i][j] = new NormalSquare(nums[i][j]);
			}
		}
	}

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
