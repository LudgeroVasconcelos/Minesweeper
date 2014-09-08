package domain.reveal;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import minesweeper.MineProperties;
import domain.grid.NormalSquare;
import domain.grid.Square;


public class RevealUntilNum implements IReveal {

	
	/* (non-Javadoc)
	 * @see domain.reveal.IReveal#revealSquares(domain.grid.Square[][], int, int)
	 */
	@Override
	public Iterable<Entry<Point, Integer>> revealSquares(Square[][] grid, int x, int y) {
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
		return map.entrySet();
	}

	private List<Point> getSquaresAround(Square[][] grid, int x, int y) {
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
