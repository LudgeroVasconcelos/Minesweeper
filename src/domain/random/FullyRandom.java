package domain.random;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 
 * @author ludgero
 */
public class FullyRandom implements IRandom {

	private Set<Point> generated = new LinkedHashSet<Point>();

	@Override
	public Iterable<Point> getMinesPosition(int x, int y, int rows,
			int columns, int minesNumber) {
		generated.clear();
		Random r = new Random();
		List<Point> pointsAround = getPointsAround(x, y);
		int max = rows * columns;

		while (generated.size() < minesNumber) {
			int randomInt = r.nextInt(max);
			int i = randomInt / columns;
			int j = randomInt - (columns * i); 

			Point p = new Point(i, j);
			if (!pointsAround.contains(p))
				generated.add(p);
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
