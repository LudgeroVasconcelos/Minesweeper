package ui.view.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicBorders;

import minesweeper.MineProperties;

/**
 * This class represents the panel located above the grid. It contains
 * components used to give feedback to the player.
 * 
 * @author Ludgero
 * 
 */
@SuppressWarnings("serial")
public class UpperPanel extends JPanel {

	private boolean ended;

	private JButton smile;
	private JLabel remainingMines;
	private JLabel timer;

	private Timer t;

	/**
	 * Constructs a new panel containing a timer, a mines' counter and a button.
	 * 
	 * @param mines
	 */
	public UpperPanel(int mines) {

		setLayout(new GridLayout(1, 3, 0, 0));
		setBorder(new CompoundBorder(
				BorderFactory.createBevelBorder(BevelBorder.RAISED),
				BorderFactory.createBevelBorder(BevelBorder.LOWERED)));

		// timer label
		JPanel timerPanel = new JPanel();
		timer = new JLabel();
		setLabelOptions(timer);
		timerPanel.add(timer);
		resetTimer();
		add(timerPanel);

		// button
		JPanel smilePanel = new JPanel();
		smile = new JButton();
		smile.setBackground(new Color(210, 210, 210));
		smile.setPreferredSize(new Dimension(35, 35));
		smile.setActionCommand("new");
		setFace(MineProperties.INSTANCE.SMILE_IMAGE);
		smilePanel.add(smile);
		add(smilePanel);

		// mines label
		JPanel minesPanel = new JPanel();
		remainingMines = new JLabel();
		setLabelOptions(remainingMines);
		setRemainingMines(MineProperties.INSTANCE.NUMBER_OF_MINES);
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
	 * Starts the timer to be seen on the timer label.
	 */
	public void startGame() {
		t = new Timer();
		TimerTask tt = new TimerTask() {

			@Override
			public void run() {
				String txt = timer.getText();
				int n = Integer.parseInt(txt);
				if (n < 999) {
					txt = n < 9 ? "00" : n < 99 ? "0" : "";
					txt += String.valueOf(n + 1);
				}
				timer.setText(txt);
			}
		};
		t.schedule(tt, 0, 1000);
	}

	/**
	 * Changes the current number of remaining mines.
	 * 
	 * @param mines
	 *            The integer corresponding to the number of remaining mines on
	 *            the grid.
	 */
	public void setRemainingMines(int mines) {
		String s = String.valueOf(mines);
		s = mines > 99 ? s : mines > 9 ? "0" + s : mines < 0 ? "000" : "00" + s;

		remainingMines.setText(s);
	}

	/**
	 * Changes this panel components to a game won state
	 */
	public void gameWon() {
		setFace(MineProperties.INSTANCE.SMILE_HAPPY_IMAGE);
		stopTimer();
		setRemainingMines(0);
		ended = true;
	}

	/**
	 * Changes this panel components to a game over state.
	 */
	public void gameOver() {
		stopTimer();
		setFace(MineProperties.INSTANCE.SMILE_SAD_IMAGE);
		ended = true;
	}

	/**
	 * Changes this panel components to a new game state.
	 */
	public void clear() {
		stopTimer();
		resetTimer();
		setRemainingMines(MineProperties.INSTANCE.NUMBER_OF_MINES);
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
	 * Resets timer to 000.
	 */
	private void resetTimer() {
		timer.setText("000");
	}

	/**
	 * Stops the timer.
	 */
	private void stopTimer() {
		if (t != null)
			t.cancel();
	}

	/**
	 * Paints an image on the button.
	 * 
	 * @param face
	 *            The image that will be painted.
	 */
	private void setFace(Image face) {
		smile.setIcon(new ImageIcon(face.getScaledInstance(35, 35,
				Image.SCALE_SMOOTH)));
	}

	/**
	 * Sets the appropriate options to a label.
	 * 
	 * @param label
	 *            The label where the options will be set
	 */
	private void setLabelOptions(JLabel label) {
		label.setBorder(BasicBorders.getTextFieldBorder());
		label.setFont(MineProperties.INSTANCE.DIGITAL_FONT);
		label.setForeground(Color.RED);
		label.setBackground(Color.BLACK);
		label.setOpaque(true);
	}
}
