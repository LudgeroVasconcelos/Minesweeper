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

public class MineButton {

	private final int x, y;
	private final int coordX, coordY;
	private final int width, height;

	private boolean flagged;
	private Graphics2D g2;

	public MineButton(int x, int y, int coordX, int coordY, int width,
			int height, Graphics g2) {
		super();
		this.x = x;
		this.y = y;
		this.coordX = coordX;
		this.coordY = coordY;
		this.width = width;
		this.height = height;

		this.g2 = (Graphics2D) g2;
		setBackGround();
	}

	public int getPosX() {
		return x;
	}

	public int getPosY() {
		return y;
	}

	public void toggleFlag() {
		if (!flagged)
			drawImage(MineProperties.INSTANCE.FLAG_IMAGE);
		else
			setBackGround();

		flagged = !flagged;
	}

	private void setBackGround() {
		g2.setStroke(new BasicStroke(2));
		g2.setColor(new Color(220, 220, 220));
		g2.fillRect(coordX, coordY, width, height);
		g2.setColor(new Color(255, 255, 255));
		g2.drawLine(coordX, coordY + 1, coordX + width, coordY + 1);
		g2.drawLine(coordX + 1, coordY, coordX + 1, coordY + height);
		g2.setColor(new Color(140, 140, 140));
		g2.drawLine(coordX, coordY + height - 1, coordX + width, coordY + height - 1);
		g2.drawLine(coordX + width - 1, coordY, coordX + width - 1, coordY + height);
		g2.setStroke(new BasicStroke(1));
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void exploded() {
		g2.setColor(Color.RED);
		g2.fillRect(coordX, coordY, width, height);
	}

	public void reveal(int mines) {
		g2.setColor(new Color(190, 190, 190));
		g2.fillRect(coordX, coordY, width, height);
		g2.setColor(new Color(140, 140, 140));
		g2.drawRect(coordX, coordY, width, height);

		if (mines > 0)
			drawNumber(mines);
	}

	public void setMine() {
		drawImage(MineProperties.INSTANCE.MINE_IMAGE);
	}

	public void setCross() {
		drawImage(MineProperties.INSTANCE.CROSS_IMAGE);
	}

	private void drawImage(Image img) {
		g2.drawImage(img, coordX, coordY, width, height, null);
	}

	private void drawNumber(int n) {
		String minesString = String.valueOf(n);

		g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
		FontMetrics fm = g2.getFontMetrics();

		Point centerPos = getTextCenterPos(fm, minesString);

		Color c = (MineProperties.INSTANCE.COLORS)[n - 1];
		g2.setColor(c);
		g2.drawString(minesString, centerPos.x, centerPos.y);
	}

	private Point getTextCenterPos(FontMetrics fm, String mines) {
		int textWidth = fm.stringWidth(mines);
		int textHeight = fm.getHeight();

		int x = coordX + (width - textWidth) / 2;
		int y = coordY + ((height - textHeight) / 2) + fm.getAscent();

		return new Point(x, y);
	}
}
