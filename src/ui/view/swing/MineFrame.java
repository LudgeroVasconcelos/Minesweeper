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

	public MineFrame(int rows, int columns) {
		int width = MineProperties.INSTANCE.BUTTON_WIDTH * columns;
		int height = MineProperties.INSTANCE.BUTTON_HEIGHT * rows;

		grid = new GridPanel(rows, columns, width, height);

		setLayout(new BorderLayout());
		setSize(width, height);
		setLocationRelativeTo(null);
		setTitle("Minesweeper");
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		add(grid, BorderLayout.CENTER);
	}

	public void addListenersToButtons(ActionListener al, MouseListener ml) {
		grid.addListenersToButtons(al, ml);
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

}
