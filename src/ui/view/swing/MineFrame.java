package ui.view.swing;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Map.Entry;

import javax.swing.JFrame;

import minesweeper.MineProperties;

@SuppressWarnings("serial")
public class MineFrame extends JFrame {

	private GridPanel grid;
	private UpperPanel upper;

	public MineFrame(int rows, int columns) {
		grid = new GridPanel(rows, columns);
		upper = new UpperPanel();

		setLayout(new BorderLayout());
		setTitle("Minesweeper");
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(grid, BorderLayout.CENTER);
		add(upper, BorderLayout.NORTH);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void revealButtons(Iterable<Entry<Point, Integer>> revealedSquares) {
		grid.revealButtons(revealedSquares);
	}

	public void toggleFlag(int x, int y, int flaggedMines) {
		grid.toggleFlag(x, y);
		upper.setNumberOfRemainingMines(MineProperties.INSTANCE.NUMBER_OF_MINES - flaggedMines);
	}

	public void clearGrid() {
		grid.clear();
		upper.stopTimer();
		upper.resetTimer();
		upper.setNumberOfRemainingMines(MineProperties.INSTANCE.NUMBER_OF_MINES);
		upper.setHappyFace();
	}
	
	public void endGame(int x, int y, Iterable<Point> mines) {
		grid.endGame(x, y, mines);
		upper.stopTimer();
		upper.setSadFace();
	}

	public void addSquaresListener(MouseListener squaresListener) {
		grid.addMouseListener(squaresListener);
	}

	public void addSmileListener(ActionListener clearGrid) {
		upper.addSmileListener(clearGrid);
	}

	public void startGame() {
		upper.startTimer();
	}
}
