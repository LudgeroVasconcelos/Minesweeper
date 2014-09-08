package ui.view.swing;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.Map.Entry;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MineFrame extends JFrame {

	private GridPanel grid;

	public MineFrame(int rows, int columns) {
		grid = new GridPanel(rows, columns);

		setLayout(new BorderLayout());
		setTitle("Minesweeper");
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(grid, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void revealButtons(Iterable<Entry<Point, Integer>> revealedSquares) {
		grid.revealButtons(revealedSquares);
	}

	public void toggleFlag(int x, int y) {
		grid.toggleFlag(x, y);
	}

	public void endGame(int x, int y, Iterable<Point> mines) {
		grid.endGame(x, y, mines);
	}

	public void addSquaresListener(MouseListener squaresListener) {
		grid.addMouseListener(squaresListener);
	}
}
