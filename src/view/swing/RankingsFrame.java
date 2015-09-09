package view.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.shephertz.app42.paas.sdk.java.game.Game.Score;

import minesweeper.MineProperties;

/**
 * 
 * @author Ludgero
 *
 */
@SuppressWarnings("serial")
public class RankingsFrame extends JFrame {

	private static final String[] COLUMNS_NAMES = { "Rank", "Name", "Score" };
	private static final int ROWS = 10;
	private static final int COLUMNS = 3;

	private Icon icon;
	private ActionListener listener;
	private Component parent;

	private DefaultTableModel dtm;
	private JTable table;

	private static String username;
	private static int pos;

	public RankingsFrame(Component parent) {
		this.parent = parent;

		constructTable();

		Image img = MineProperties.INSTANCE.RANKING_IMAGE;
		img = img.getScaledInstance(90, 50, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);

		setResizable(false);
		setIconImage(MineProperties.INSTANCE.RANKING_IMAGE);

		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane);
	}

	private void constructTable() {
		dtm = new DefaultTableModel(ROWS, COLUMNS);
		dtm.setColumnIdentifiers(COLUMNS_NAMES);
		
		table = new JTable();
		table.setModel(dtm);
		table.setDefaultRenderer(Object.class, new MyTableCellRenderer());
		table.getTableHeader().setDefaultRenderer(new MyHeaderCellRenderer());
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.setFont(new Font("Arial", Font.PLAIN, 18));
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setRowHeight(30);
		table.setShowGrid(false);
		table.setEnabled(false);
	}

	public void addSubmitScoreListener(ActionListener listener) {
		this.listener = listener;
	}

	public void showSubmitScoreFrame(int score) {

		Object obj = JOptionPane.showInputDialog(parent, "Score: " + score + "\nEnter your name", "Submit score",
				JOptionPane.PLAIN_MESSAGE, icon, null, null);
		try {
			listener.actionPerformed(new ActionEvent(obj, score, "submit score"));
		} catch (IllegalArgumentException e) {
			// user canceled. Do nothing.
		}
	}

	public void showRankings(final int pos, final String username, int score, String level, Iterable<Score> scoreList) {
		int rowCount = table.getRowCount();

		// check line 115 to understand this if statement
		if (rowCount > ROWS)
			dtm.removeRow(rowCount - 1);

		RankingsFrame.username = username;
		RankingsFrame.pos = pos;

		Iterator<Score> it = scoreList.iterator();
		for (int i = 0; i < ROWS; i++) {
			Score s = it.hasNext() ? it.next() : null;

			String name = s != null ? s.getUserName() : "";
			Object value = s != null ? -s.getValue().intValue() : "";

			table.setValueAt(i + 1, i, 0);
			table.setValueAt(name, i, 1);
			table.setValueAt(value, i, 2);
		}

		// add one additional row if the user made a score but is not in the top 10
		if (username != null && (pos < 0 || pos > 10)) {
			dtm.addRow(new Object[] { pos < 0 ? "> 100" : pos, username, score });
		}

		table.setPreferredScrollableViewportSize(table.getPreferredSize());

		setTitle(level);
		pack();
		setLocationRelativeTo(parent);
		setVisible(true);
	}

	public static class MyTableCellRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			Color color;

			// set the gold, silver and bronze colors for the top 3 scores
			if (row == 0 && column == 0) {
				color = new Color(255, 215, 0);
			} else if (row == 1 && column == 0) {
				color = new Color(192, 192, 192);
			} else if (row == 2 && column == 0) {
				color = new Color(180, 128, 115);
			} else {
				// alternate between white and gray background colors
				color = row % 2 == 0 ? new Color(242, 242, 242) : Color.WHITE;
			}

			if (username != null && ((row == pos - 1) || ((pos < 0 || pos > ROWS) && row == 10))) {
				color = new Color(164, 212, 255);
				c.setForeground(new Color(120, 0, 0));
			} else {
				c.setForeground(new Color(45, 45, 45));
			}
			c.setBackground(color);
			return c;
		}

		@Override
		public int getHorizontalAlignment() {
			return SwingConstants.CENTER;
		}

		@Override
		public int getVerticalAlignment() {
			return SwingConstants.CENTER;
		}

	}

	public static class MyHeaderCellRenderer extends DefaultTableCellRenderer {

		@Override
		public int getHorizontalAlignment() {
			return SwingConstants.CENTER;
		}

		@Override
		public Font getFont() {
			return new Font("Arial", Font.PLAIN, 15);
		}

		@Override
		public Color getBackground() {
			return Color.LIGHT_GRAY;
		}
	}
}
