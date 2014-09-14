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
	private MineMenu menu;

	public MineFrame() {
		grid = new GridPanel(MineProperties.INSTANCE.ROWS, MineProperties.INSTANCE.COLUMNS);
		upper = new UpperPanel(MineProperties.INSTANCE.NUMBER_OF_MINES);
		menu = new MineMenu();

		setJMenuBar(menu);
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
		upper.setRemainingMines(MineProperties.INSTANCE.NUMBER_OF_MINES
				- flaggedMines);
	}

	public void clearGrid() {
		grid.clear();
		upper.clear();
	}

	public void gameOver(int x, int y, Iterable<Point> mines) {
		grid.gameOver(x, y, mines);
		upper.gameOver();
	}

	public void addSquaresListener(MouseListener squaresListener) {
		grid.addMouseListener(squaresListener);
	}

	public void addClearListener(ActionListener clearGrid) {
		upper.addClearListener(clearGrid);
		menu.addClearListener(clearGrid);
	}

	public void startGame() {
		upper.startGame();
	}

	public void gameWon(Iterable<Point> unmarkedSquares) {
		grid.gameWon(unmarkedSquares);
		upper.gameWon();
	}

	public void mousePressed() {
		upper.mousePressed();
	}
	
	public void mouseReleased() {
		upper.mouseReleased();
	}

	public void addMenuListeners(ActionListener quitListener, ActionListener diffListener){
		menu.addQuitListener(quitListener);
		menu.addDiffListener(diffListener);
	}

	public void resizeGrid(int rows, int columns) {
		grid.resize(rows, columns);
		pack();
		repaint();
		setLocationRelativeTo(null);
	}
}
