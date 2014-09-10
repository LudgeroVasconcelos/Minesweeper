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

@SuppressWarnings("serial")
public class UpperPanel extends JPanel {

	private JButton smile;
	private JLabel remainingMines;
	private JLabel timer;

	private Timer t;

	public UpperPanel() {
		setLayout(new GridLayout(1, 3));
		setBorder(new CompoundBorder(
				BorderFactory.createBevelBorder(BevelBorder.RAISED),
				BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
		JPanel timerPanel = new JPanel();
		timer = new JLabel();
		setLabelOptions(timer);
		timerPanel.add(timer);
		resetTimer();
		add(timerPanel);

		JPanel smilePanel = new JPanel();
		smile = new JButton();
		smile.setBackground(new Color(210, 210, 210));
		smile.setPreferredSize(new Dimension(40, 40));
		setHappyFace();
		smilePanel.add(smile);
		add(smilePanel);

		JPanel minesPanel = new JPanel();
		remainingMines = new JLabel();
		setLabelOptions(remainingMines);
		setNumberOfRemainingMines(MineProperties.INSTANCE.NUMBER_OF_MINES);
		minesPanel.add(remainingMines);
		add(minesPanel);
	}

	public void addSmileListener(ActionListener clearGrid) {
		smile.addActionListener(clearGrid);
	}

	public void startTimer() {
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

	public void stopTimer() {
		if (t != null)
			t.cancel();
	}

	public void resetTimer() {
		timer.setText("000");
	}

	public void setNumberOfRemainingMines(int mines) {
		String s = String.valueOf(mines);
		s = mines > 99 ? s
				: mines > 9 ? "0" + s : mines < 0 ? "000" : "00" + s;
		remainingMines.setText(s);
	}

	private void setLabelOptions(JLabel label) {
		label.setBorder(BasicBorders.getTextFieldBorder());
		label.setFont(MineProperties.INSTANCE.DIGITAL_FONT);
		label.setForeground(Color.RED);
		label.setBackground(Color.BLACK);
		label.setOpaque(true);
	}

	public void setSadFace() {
		smile.setIcon(new ImageIcon(MineProperties.INSTANCE.SMILE_SAD_IMAGE.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
	}

	public void setHappyFace() {
		smile.setIcon(new ImageIcon(MineProperties.INSTANCE.SMILE_IMAGE.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		
	}

}
