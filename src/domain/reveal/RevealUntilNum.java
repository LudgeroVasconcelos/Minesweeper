package domain.reveal;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javax.naming.OperationNotSupportedException;

import domain.grid.IGrid;

/**
 * Reveal the squares until a square with mines around greater than zero is
 * found
 * 
 * @author ludgero
 * 
 */
public class RevealUntilNum extends AbstractReveal {

	public RevealUntilNum(IGrid grid) {
		super(grid);
	}

	@Override
	public Iterable<Point> getSquaresToReveal(int x, int y) {
		// iterative DFS

		Set<Point> set = new HashSet<Point>();
		Stack<Point> stack = new Stack<Point>();

		if(grid.isMarked(x, y))
			return set;
		
		stack.push(new Point(x, y));

		while (!stack.isEmpty()) {
			Point p = stack.pop();
			if (!set.contains(p)) {
				set.add(p);

				try {
					if (grid.getNumOfMinesAround(p.x, p.y) == 0) {

						Iterable<Point> squaresAround = getSquaresAround(p.x,
								p.y);
						
						for (Point po : squaresAround)
							stack.push(po);
					}
				} catch (OperationNotSupportedException e) {
					// this can only happen in the first try, in case it's a
					// mine.

					break;
				}
			}
		}

		return set;
	}

	private Iterable<Point> getSquaresAround(int x, int y) {
		List<Point> list = new ArrayList<Point>();
		int rows = grid.getRows();
		int columns = grid.getColumns();

		for (int i = x - 1; i <= x + 1; i++)
			for (int j = y - 1; j <= y + 1; j++)
				if (i >= 0 && i < rows && j >= 0 && j < columns)
					if (!(i == x && j == y))
						if (!grid.isMarked(i, j))
							list.add(new Point(i, j));

		return list;
	}

}
