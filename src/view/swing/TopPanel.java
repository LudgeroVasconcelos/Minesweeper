package view.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicBorders;

import minesweeper.MineProperties;
import model.Difficulty;

/**
 * This class represents the panel located above the grid. It contains
 * components used to give feedback to the player such as a timer, a counter of
 * remaining mines and a yellow expression-faced button
 * 
 * @author Ludgero
 * 
 */
@SuppressWarnings("serial")
public class TopPanel extends JPanel {

	private static final int BUTTON_WIDTH = 35;
	private static final int BUTTON_HEIGHT = 35;
	
	private boolean ended;

	private JButton smile;
	private JLabel remainingMines;
	private JLabel timer;

	/**
	 * Constructs a new panel containing a timer, a remaining mines' counter and
	 * a button.
	 * 
	 * @param mines
	 */
	public TopPanel(int mines) {

		setLayout(new GridLayout(1, 3, 0, 0));
		setBorder(new CompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),
				BorderFactory.createBevelBorder(BevelBorder.LOWERED)));

		// timer label
		JPanel timerPanel = new JPanel();
		timer = new JLabel();
		setLabelSettings(timer);
		timerPanel.add(timer);
		add(timerPanel);

		// button
		JPanel smilePanel = new JPanel();
		smile = new JButton();
		smile.setBackground(new Color(210, 210, 210));
		smile.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		smile.setBorderPainted(false);
		smile.setActionCommand("new");
		smile.setFocusPainted(false);
		setFace(MineProperties.INSTANCE.SMILE_IMAGE);
		smilePanel.add(smile);
		add(smilePanel);

		// mines label
		JPanel minesPanel = new JPanel();
		remainingMines = new JLabel();
		setLabelSettings(remainingMines);
		setRemainingMines(mines);
		minesPanel.add(remainingMines);
		add(minesPanel);
	}

	/**
	 * Adds a clear game listener to the button.
	 * 
	 * @param clearGrid
	 *            The listener to be added
	 */
	public void addClearListener(ActionListener clearGrid) {
		smile.addActionListener(clearGrid);
	}

	/**
	 * Changes the current number of remaining mines.
	 * 
	 * @param mines
	 *            The integer corresponding to the number of remaining mines on
	 *            the grid.
	 */
	public void setRemainingMines(int mines) {
		remainingMines.setText(String.format("%03d", mines));
	}

	/**
	 * Changes this panel components to a game won state
	 */
	public void gameWon() {
		setFace(MineProperties.INSTANCE.SMILE_HAPPY_IMAGE);
		setRemainingMines(0);
		ended = true;
	}

	/**
	 * Changes this panel components to a game over state.
	 */
	public void gameOver() {
		setFace(MineProperties.INSTANCE.SMILE_SAD_IMAGE);
		ended = true;
	}

	/**
	 * Changes this panel components to a new game state.
	 */
	public void clear() {
		setRemainingMines(Difficulty.getCurrentDifficulty().getMines());
		setFace(MineProperties.INSTANCE.SMILE_IMAGE);
		ended = false;
	}

	/**
	 * Changes the button image to a surprised face image.
	 */
	public void mousePressed() {
		if (!ended)
			setFace(MineProperties.INSTANCE.SMILE_SURPRISED_IMAGE);
	}

	/**
	 * Changes the button image to a normal face image.
	 */
	public void mouseReleased() {
		if (!ended)
			setFace(MineProperties.INSTANCE.SMILE_IMAGE);
	}

	/**
	 * Sets the current time to the timer label.
	 * 
	 * @param time
	 *            The current time to be shown
	 */
	public void setTime(int time) {
		timer.setText(String.format("%03d", time));
	}

	/**
	 * Paints an image on the button.
	 * 
	 * @param face
	 *            The image that will be painted.
	 */
	private void setFace(Image face) {
		smile.setIcon(new ImageIcon(face.getScaledInstance(BUTTON_WIDTH - 2, BUTTON_HEIGHT - 2, Image.SCALE_SMOOTH)));
	}

	/**
	 * Sets the appropriate settings to a label.
	 * 
	 * @param label
	 *            The label on which the settings are to be set
	 */
	private void setLabelSettings(JLabel label) {
		label.setBorder(BasicBorders.getTextFieldBorder());
		label.setFont(MineProperties.INSTANCE.DIGITAL_FONT);
		label.setForeground(Color.RED);
		label.setBackground(Color.BLACK);
		label.setOpaque(true);
	}
}
