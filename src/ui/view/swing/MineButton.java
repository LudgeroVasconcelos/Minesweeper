package ui.view.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class MineButton extends JButton {

	private final int x, y;
	private boolean flagged;

	private ActionListener al;
	private MouseListener ml;
	private Graphics2D g2;

	public MineButton(int x, int y, int width, int height, Graphics g2) {
		super();
		this.x = x;
		this.y = y;
		this.g2 = (Graphics2D) g2;

		setBackground(new Color(220, 220, 220));
	}

	public void addListeners(ActionListener al, MouseListener ml) {
		addActionListener(al);
		addMouseListener(ml);

		this.al = al;
		this.ml = ml;
	}

	public void removeListeners() {
		removeActionListener(al);
		removeMouseListener(ml);
	}

	public int getPosX() {
		return x;
	}

	public int getPosY() {
		return y;
	}

	public void toggleFlag() {
		if (!flagged)
			setIcon(MineButtonProperties.getInstance().getFlagIcon());
		else
			setIcon(null);

		flagged = !flagged;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void exploded() {
		g2.setColor(Color.RED);
		g2.fillRect(getX(), getY(), getWidth(), getHeight());
	}

	public void reveal(int mines) {
		setVisible(false);

		g2.setColor(new Color(150, 150, 150));
		g2.drawRect(getX(), getY(), getWidth() - 1, getHeight() - 1);

		if (mines > 0)
			drawNumber(mines);
	}

	public void setMine() {
		drawImage(MineButtonProperties.getInstance().getMineImage());
	}

	public void setCross() {
		drawImage(MineButtonProperties.getInstance().getCrossImage());
	}

	private void drawNumber(int n) {
		String minesString = String.valueOf(n);

		g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
		FontMetrics fm = g2.getFontMetrics();

		Point centerPos = getTextCenterPos(fm, minesString);

		Color c = MineButtonProperties.getInstance().getColor(n);
		g2.setColor(c);
		g2.drawString(minesString, centerPos.x, centerPos.y);
	}

	private Point getTextCenterPos(FontMetrics fm, String mines) {
		int textWidth = fm.stringWidth(mines);
		int textHeight = fm.getHeight();

		int x = getX() + (getWidth() - textWidth) / 2;
		int y = getY() + ((getHeight() - textHeight) / 2) + fm.getAscent();

		return new Point(x, y);
	}

	private void drawImage(Image img) {
		g2.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
	}
}
