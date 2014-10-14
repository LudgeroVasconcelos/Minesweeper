package domain.reveal;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import domain.grid.NormalSquare;
import domain.grid.Square;

/**
 * This class specifies how squares are revealed.
 * 
 * When a square with no mines around is clicked, all squares around it are
 * revealed. This process continues until a square with at least one mine
 * surrounding it is revealed.
 * 
 * @author Ludgero
 * 
 */
public class RevealUntilNum implements IReveal {

	@Override
	public Map<Point, Integer> revealSquares(Square[][] grid, int x, int y) {
		// iterative DFS

		Map<Point, Integer> map = new HashMap<Point, Integer>();
		Stack<Point> stack = new Stack<Point>();

		stack.push(new Point(x, y));

		while (!stack.isEmpty()) {
			Point p = stack.pop();

			if (!map.containsKey(p)) {
				NormalSquare ns = (NormalSquare) grid[p.x][p.y];
				int minesAround = ns.getNumOfMinesAround();
				ns.reveal();

				map.put(p, minesAround);

				if (minesAround == 0) {
					List<Point> squaresAround = getSquaresAround(grid, p.x, p.y);

					for (Point po : squaresAround)
						stack.push(po);
				}
			}
		}
		return map;
	}

	/**
	 * Determines and returns a list containing the positions of squares
	 * surrounding the given (x, y) square. The determined squares are not
	 * marked nor revealed.
	 * 
	 * @param grid
	 *            The grid containing all squares of the game
	 * @param x
	 *            The x coordinate of the specified square
	 * @param y
	 *            The y coordinate of the specified square
	 * 
	 * @return A list as specified.
	 */
	private List<Point> getSquaresAround(Square[][] grid, int x, int y) {
		List<Point> list = new ArrayList<Point>();
		int rows = grid.length;
		int columns = grid[0].length;

		for (int i = x - 1; i <= x + 1; i++)
			for (int j = y - 1; j <= y + 1; j++)
				if (i >= 0 && i < rows && j >= 0 && j < columns)
					if (!grid[i][j].isMarked() && !grid[i][j].isRevealed())
						list.add(new Point(i, j));

		return list;
	}
}
