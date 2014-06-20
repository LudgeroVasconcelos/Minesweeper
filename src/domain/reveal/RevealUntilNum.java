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

		Set<Point> list = new HashSet<Point>();
		Stack<Point> stack = new Stack<Point>();

		stack.push(new Point(x, y));

		while (!stack.isEmpty()) {
			Point p = stack.pop();
			if (!list.contains(p)) {
				list.add(p);

				try {
					if (grid.getNumOfMinesAround(p.x, p.y) == 0) {

						Iterable<Point> squaresAround = getSquaresAround(p.x, p.y);
						for (Point po : squaresAround)
							stack.push(po);
				 	}
				} catch (OperationNotSupportedException e) {
					// Contracts haven't been met
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	private Iterable<Point> getSquaresAround(int x, int y) {
		List<Point> list = new ArrayList<Point>();
		int rows = grid.getRows();
		int columns = grid.getColumns();

		for (int i = x - 1; i <= x + 1; i++)
			for (int j = y - 1; j <= y + 1; j++)
				if (i >= 0 && i < rows && j >= 0 && j < columns)
					if (!(i == x && j == y))
						try {
							if (grid.getNumOfMinesAround(i, j) >= 0
									&& !grid.isMarked(i, j))
								list.add(new Point(i, j));
						} catch (OperationNotSupportedException e) {
							// Simply don't add to the list
						}

		return list;
	}
	
}
