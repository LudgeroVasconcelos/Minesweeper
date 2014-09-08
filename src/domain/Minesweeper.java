package domain;

import java.util.Observer;

import minesweeper.MineProperties;
import domain.fill.FillRandom;
import domain.grid.Grid;
import domain.grid.IGrid;
import domain.reveal.RevealUntilNum;

public class Minesweeper implements MineFacade {

	private IGrid grid;

	public Minesweeper() {
		grid = new Grid(new FillRandom(MineProperties.INSTANCE.ROWS,
				MineProperties.INSTANCE.COLUMNS,
				MineProperties.INSTANCE.NUMBER_OF_MINES), new RevealUntilNum());
	}

	
	@Override
	public void clearGrid() {
		grid.clearGrid();
	}

	@Override
	public void reveal(int x, int y) {
		if(grid.gameHasEnded())
			return;
		
		if (!grid.isFilled()) {
			grid.fill(x, y);
		}
		grid.reveal(x, y);
	}

	@Override
	public void toggleMark(int x, int y) {
		if (grid.isFilled() && !grid.gameHasEnded())
			grid.toggleMark(x, y);
	}

	@Override
	public void addObserver(Observer observer) {
		grid.addObserver(observer);
	}
}
