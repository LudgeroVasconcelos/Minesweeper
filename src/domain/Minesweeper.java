package domain;

import java.util.Observer;

import minesweeper.Difficulty;
import minesweeper.MineProperties;
import domain.fill.FillRandom;
import domain.fill.IFill;
import domain.grid.Grid;
import domain.grid.IGrid;
import domain.reveal.IReveal;
import domain.reveal.RevealUntilNum;

/**
 * This class implements the interaction a user may have with the game.
 * 
 * The grid is set to place the mines randomly after the first square is
 * revealed and it reveals all the squares around a square which has zero mines
 * around.
 * 
 * @author Ludgero
 * 
 */
public class Minesweeper implements MineFacade {

	private IGrid grid;

	/**
	 * Constructs a new minesweeper game
	 */
	public Minesweeper() {
		IFill filler = new FillRandom(MineProperties.INSTANCE.ROWS,
				MineProperties.INSTANCE.COLUMNS,
				MineProperties.INSTANCE.NUMBER_OF_MINES);

		IReveal revealer = new RevealUntilNum();

		grid = new Grid(filler, revealer);
	}

	@Override
	public void clearGrid() {
		grid.clearGrid();
	}

	@Override
	public void reveal(int x, int y) {
		if (!grid.isFilled())
			grid.fill(x, y);

		if (!grid.gameHasEnded())
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

	@Override
	public void setDifficulty(Difficulty diff) {
		if (diff.getRows() != MineProperties.INSTANCE.ROWS
				|| diff.getColumns() != MineProperties.INSTANCE.COLUMNS)

			grid.setDifficulty(diff);
	}
}
