package ui.view.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Map.Entry;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GridPanel extends JPanel {

	private final MineButton[][] buttons;
	private BufferedImage state;

	private Set<Point> flaggedButtons; // helps to display mines faster

	public GridPanel(int rows, int columns, int width, int height) {
		super();
		setLayout(new GridLayout(rows, columns));
		setSize(width, height);

		Graphics g = initState(width, height);
		flaggedButtons = new HashSet<Point>();
		buttons = new MineButton[rows][columns];
		addMineButtons(width, height, g);
	}

	private Graphics initState(int width, int height) {
		state = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = state.getGraphics();
		g.setColor(new Color(190, 190, 190));
		g.fillRect(0, 0, width, height);

		return g;
	}

	private void addMineButtons(int width, int height, Graphics g) {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j] = new MineButton(i, j, g);
				add(buttons[i][j]);
			}
		}
	}

	public void addListenersToButtons(ActionListener al, MouseListener ml) {
		for (int i = 0; i < buttons.length; i++)
			for (int j = 0; j < buttons[i].length; j++)
				buttons[i][j].addListeners(al, ml);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(state, 0, 0, this);
	}

	public void toggleFlag(int x, int y) {
		buttons[x][y].toggleFlag();

		Point p = new Point(x, y);
		if (!flaggedButtons.contains(p))
			flaggedButtons.add(p);
		else
			flaggedButtons.remove(p);
	}

	public void revealButtons(Iterable<Entry<Point, Integer>> revealedSquares) {
		for (Entry<Point, Integer> entry : revealedSquares) {
			Point p = entry.getKey();
			int mines = entry.getValue();

			buttons[p.x][p.y].reveal(mines);
		}
	}

	public void endGame(int x, int y, Iterable<Point> mines) {
		buttons[x][y].exploded();

		for (Point p : mines) {
			if (!buttons[p.x][p.y].isFlagged()) {
				buttons[p.x][p.y].reveal(0);
				buttons[p.x][p.y].setMine();
			} else
				flaggedButtons.remove(p);
		}

		for (Point p : flaggedButtons) {
			buttons[p.x][p.y].reveal(0);
			buttons[p.x][p.y].setMine();
			buttons[p.x][p.y].setCross();
		}
	}
}