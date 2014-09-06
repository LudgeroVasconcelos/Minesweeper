package ui.view.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Map.Entry;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GridPanel extends JPanel {

	private final MineButton[][] buttons;
	private BufferedImage state;

	public GridPanel(int rows, int columns, int width, int height) {
		super();
		setLayout(new GridLayout(rows, columns));
		setSize(width, height);

		Graphics g = initState(width, height);

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
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].addListeners(al, ml);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(state, 0, 0, this);
	}

	public void toggleFlag(int x, int y) {
		buttons[x][y].toggleFlag();
	}

	public void revealButtons(Iterable<Entry<Point, Integer>> revealedSquares) {
		for (Entry<Point, Integer> entry : revealedSquares) {
			Point p = entry.getKey();
			int mines = entry.getValue();

			buttons[p.x][p.y].reveal(mines);
		}
	}

	public void endGame(int x, int y, boolean[][] mines) {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {

				if (i == x && j == y) {
					buttons[i][j].exploded();
				}
				if ((mines[i][j] && !buttons[i][j].isFlagged())
						|| (!mines[i][j] && buttons[i][j].isFlagged())) {
					buttons[i][j].reveal(0);
					buttons[i][j].setMine();

					if (!mines[i][j] && buttons[i][j].isFlagged())
						buttons[i][j].setCross();
				}
				buttons[i][j].removeListeners();
			}
		}
	}
}