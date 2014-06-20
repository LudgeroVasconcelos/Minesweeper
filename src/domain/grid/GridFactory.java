package domain.grid;

import domain.random.FullyRandom;

final class GridFactory {

	public Grid createGrid(){
		return new Grid(new FullyRandom(), 20, 20, 20);
	}
}
