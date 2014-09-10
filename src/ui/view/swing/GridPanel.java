package ui.view.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JPanel;

import minesweeper.MineProperties;

@SuppressWarnings("serial")
public class GridPanel extends JPanel {

	private MineButton[][] buttons;
	private BufferedImage state;

	private Set<Point> flaggedButtons; // helps to display mines faster

	public GridPanel(int rows, int columns) {
		super();
		int width = MineProperties.INSTANCE.BUTTON_WIDTH * columns;
		int height = MineProperties.INSTANCE.BUTTON_HEIGHT * rows;
		setPreferredSize(new Dimension(width, height));

		state = new BufferedImage(width, height, Image.SCALE_SMOOTH);
		flaggedButtons = new HashSet<Point>();
		buttons = new MineButton[rows][columns];
		addMineButtons(MineProperties.INSTANCE.BUTTON_WIDTH,
				MineProperties.INSTANCE.BUTTON_HEIGHT);
	}

	private void addMineButtons(int width, int height) {
		int coordX = 0, coordY = 0;
		Graphics g = state.getGraphics();
		
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j] = new MineButton(i, j, coordY, coordX, width,
						height, g);
				coordY += width;
			}
			coordX += height;
			coordY = 0;
		}
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
		repaint();
	}

	public void revealButtons(Iterable<Entry<Point, Integer>> revealedSquares) {
		for (Entry<Point, Integer> entry : revealedSquares) {
			Point p = entry.getKey();
			int mines = entry.getValue();

			buttons[p.x][p.y].reveal(mines);
		}
		repaint();
	}


	public void clear() {
		flaggedButtons.clear();
		
		for(int i = 0; i < buttons.length; i++){
			for(int j = 0; j < buttons[i].length; j++){
				buttons[i][j].clear();
			}
		}
		repaint();
	}
	
	public void gameOver(int x, int y, Iterable<Point> mines) {
		for (Point p : mines) {
			if(p.x == x && p.y == y){
				buttons[x][y].exploded();
				buttons[x][y].setMine();
			}
			else if (!buttons[p.x][p.y].isFlagged()) {
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
		repaint();
	}

	public void gameWon(Iterable<Point> unmarkedSquares) {
		for(Point p : unmarkedSquares){
			buttons[p.x][p.y].toggleFlag();
		}
		repaint();
	}
}