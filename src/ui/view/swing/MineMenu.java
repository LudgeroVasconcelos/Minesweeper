package ui.view.swing;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * This class represents the game menu.
 * 
 * @author Ludgero
 * 
 */
@SuppressWarnings("serial")
public class MineMenu extends JMenuBar {

	JMenuItem newItem;
	JMenuItem beginnerItem;
	JMenuItem intermediateItem;
	JMenuItem expertItem;
	JMenuItem quitItem;

	/**
	 * Constructs the menu and adds some menu items to it.
	 */
	public MineMenu() {
		JMenu jm = new JMenu("Game");

		newItem = new JMenuItem("New");
		beginnerItem = new JMenuItem("Beginner");
		intermediateItem = new JMenuItem("Intermediate");
		expertItem = new JMenuItem("Expert");
		quitItem = new JMenuItem("Quit");

		jm.add(newItem);
		jm.addSeparator();
		jm.add(beginnerItem);
		jm.add(intermediateItem);
		jm.add(expertItem);
		jm.addSeparator();
		jm.add(quitItem);

		add(jm);
	}

	/**
	 * Adds a listener to clear the game on the 'new' menu item.
	 * 
	 * @param clearListener
	 *            The listener to be added to the appropriate menu item
	 */
	public void addClearListener(ActionListener clearListener) {
		newItem.addActionListener(clearListener);
	}

	/**
	 * Adds a listener to quit the game on the 'quit' menu item.
	 * 
	 * @param quitListener
	 *            The listener to be added to the appropriate menu item
	 */
	public void addQuitListener(ActionListener quitListener) {
		quitItem.addActionListener(quitListener);

	}

	/**
	 * Adds a listener to change difficulty on the difficulty menu items.
	 * 
	 * @param diffListener
	 *            The listener to be added to the appropriate menu items
	 */
	public void addDiffListener(ActionListener diffListener) {
		beginnerItem.addActionListener(diffListener);
		intermediateItem.addActionListener(diffListener);
		expertItem.addActionListener(diffListener);
	}
}
