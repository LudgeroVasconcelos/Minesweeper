package model;

import java.util.Observer;

import minesweeper.MineProperties;
import minesweeper.Util;
import model.grid.Grid;
import model.grid.IGrid;
import model.grid.fill.FillRandom;
import model.grid.fill.IFill;
import model.grid.reveal.IReveal;
import model.grid.reveal.RevealUntilNum;

/**
 * This class implements the game operations.
 * 
 * @author Ludgero
 * 
 */
public class MineFacade implements IMineFacade {

	private IGrid grid;
	private MineTimer timer;

	/**
	 * Constructs a new minesweeper game
	 */
	public MineFacade() {
		IFill filler = new FillRandom(MineProperties.INSTANCE.ROWS,
				MineProperties.INSTANCE.COLUMNS,
				MineProperties.INSTANCE.NUMBER_OF_MINES);

		IReveal revealer = new RevealUntilNum();

		grid = new Grid(filler, revealer);
		timer = new MineTimer();
	}

	@Override
	public void clearGrid() {
		grid.clearGrid();
		timer.reset();
	}

	@Override
	public void reveal(int x, int y) {
		if(Util.isValid(x, y)){
			
			if (!grid.isFilled()){
				grid.fill(x, y);
				timer.start();
			}
	
			if (!grid.gameHasEnded()){
				grid.reveal(x, y);
				
				if(grid.gameHasEnded())
					timer.stop();
			}
		}
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

	@Override
	public void setDifficulty(Difficulty diff) {
		if (diff.getRows() != MineProperties.INSTANCE.ROWS
				|| diff.getColumns() != MineProperties.INSTANCE.COLUMNS)

			grid.setDifficulty(diff);
	}

	@Override
	public int getCurrentTime() {
		return timer.getCurrentTime();
	}
}
