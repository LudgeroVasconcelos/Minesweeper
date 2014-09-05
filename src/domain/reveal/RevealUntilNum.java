package domain.reveal;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import minesweeper.MineProperties;
import domain.grid.NormalSquare;
import domain.grid.Square;

/**
 * Reveal the squares until a square with mines around greater than zero is
 * found
 * 
 * @author ludgero
 * 
 */
public class RevealUntilNum implements IReveal {

	@Override
	public Iterable<Point> getSquaresToReveal(Square[][] grid, int x, int y) {
		// iterative DFS

		Set<Point> set = new HashSet<Point>();
		Stack<Point> stack = new Stack<Point>();

		stack.push(new Point(x, y));

		while (!stack.isEmpty()) {
			Point p = stack.pop();
			if (!set.contains(p)) {
				set.add(p);

				if (((NormalSquare) grid[p.x][p.y]).getNumOfMinesAround() == 0) {

					Iterable<Point> squaresAround = getSquaresAround(grid, p.x,
							p.y);

					for (Point po : squaresAround)
						stack.push(po);
				}
			}
		}

		return set;
	}

	private Iterable<Point> getSquaresAround(Square[][] grid, int x, int y) {
		List<Point> list = new ArrayList<Point>();
		int rows = MineProperties.INSTANCE.ROWS;
		int columns = MineProperties.INSTANCE.COLUMNS;

		for (int i = x - 1; i <= x + 1; i++)
			for (int j = y - 1; j <= y + 1; j++)
				if (i >= 0 && i < rows && j >= 0 && j < columns)
					if (!(i == x && j == y))
						if (!grid[i][j].isMarked())
							list.add(new Point(i, j));

		return list;
	}
}
