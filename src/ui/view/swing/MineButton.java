package ui.view.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import minesweeper.MineProperties;

/**
 * This class represents a square in the grid of the game window.
 * 
 * @author Ludgero
 * 
 */
public class MineButton {

	private final int coordX, coordY;
	private final int width, height;

	private Graphics2D g2;

	/**
	 * Constructs and initializes a new button.
	 * 
	 * @param coordX
	 *            The x coordinate of this button on the window frame
	 * @param coordY
	 *            The y coordinate of this button on the window frame
	 * @param g2
	 *            The graphics where this button will be painted to
	 */
	public MineButton(int coordX, int coordY, Graphics g2) {
		super();
		this.coordX = coordX;
		this.coordY = coordY;
		this.width = MineProperties.INSTANCE.BUTTON_WIDTH;
		this.height = MineProperties.INSTANCE.BUTTON_HEIGHT;

		this.g2 = (Graphics2D) g2;
		paint();
	}

	public void setFlag() {
		drawImage(MineProperties.INSTANCE.FLAG_IMAGE);
	}

	public void removeFlag() {
		paint();
	}

	/**
	 * This method will paint the square to its original state.
	 */
	private void paint() {
		g2.setStroke(new BasicStroke(2));
		g2.setColor(new Color(210, 210, 210));
		g2.fillRect(coordX, coordY, width, height);
		g2.setColor(new Color(255, 255, 255));
		g2.drawLine(coordX, coordY + 1, coordX + width, coordY + 1);
		g2.drawLine(coordX + 1, coordY, coordX + 1, coordY + height);
		g2.setColor(new Color(140, 140, 140));
		g2.drawLine(coordX, coordY + height - 1, coordX + width, coordY
				+ height - 1);
		g2.drawLine(coordX + width - 1, coordY, coordX + width - 1, coordY
				+ height);
		g2.setStroke(new BasicStroke(1));
	}

	/**
	 * This square got exploded. A red background is painted on it.
	 */
	public void exploded() {
		g2.setColor(Color.RED);
		g2.fillRect(coordX, coordY, width, height);
		setMine();
	}

	/**
	 * Reveals this square. Draws the number of mines surrounding this square.
	 * 
	 * @param mines
	 *            The number of mines surrounding this square
	 */
	public void reveal(int mines) {
		g2.setColor(new Color(190, 190, 190));
		g2.fillRect(coordX, coordY, width, height);
		g2.setColor(new Color(140, 140, 140));
		g2.drawRect(coordX, coordY, width, height);

		if (mines > 0)
			drawNumber(mines);
	}

	/**
	 * Draws a mine image on this square.
	 */
	public void setMine() {
		drawImage(MineProperties.INSTANCE.MINE_IMAGE);
	}

	/**
	 * Draws a cross over this square.
	 */
	public void setCross() {
		drawImage(MineProperties.INSTANCE.CROSS_IMAGE);
	}

	/**
	 * Returns this square to its original state.
	 */
	public void clear() {
		paint();
	}

	/**
	 * Draws an image on this button.
	 * 
	 * @param img
	 *            The image to be drawn
	 */
	private void drawImage(Image img) {
		g2.drawImage(img, coordX, coordY, width, height, null);
	}

	/**
	 * Draws a number on this button.
	 * 
	 * @param n
	 *            The number to be drawn
	 */
	private void drawNumber(int n) {
		String minesString = String.valueOf(n);

		g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
		FontMetrics fm = g2.getFontMetrics();

		Point centerPos = getTextCenterPos(fm, minesString);

		Color c = (MineProperties.INSTANCE.COLORS)[n - 1];
		g2.setColor(c);
		g2.drawString(minesString, centerPos.x, centerPos.y);
	}

	/**
	 * Determines the coordinates where the given string must be drawn so that
	 * it will be centered on this square.
	 * 
	 * @param fm
	 *            The FontMetrics object of the font used to draw the given text
	 * @param mines
	 *            The text to be drawn
	 * 
	 * @return The coordinates as specified
	 */
	private Point getTextCenterPos(FontMetrics fm, String mines) {
		int textWidth = fm.stringWidth(mines);
		int textHeight = fm.getHeight();

		int x = coordX + (width - textWidth) / 2;
		int y = coordY + ((height - textHeight) / 2) + fm.getAscent();

		return new Point(x, y);
	}
}
