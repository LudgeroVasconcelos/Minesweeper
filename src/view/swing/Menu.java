package view.swing;

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
public class Menu extends JMenuBar {

	JMenuItem newItem;
	JMenuItem beginnerItem;
	JMenuItem intermediateItem;
	JMenuItem expertItem;
	JMenuItem quitItem;

	/**
	 * Constructs the menu and adds some menu items to it.
	 */
	public Menu() {
		JMenu jm = new JMenu("Game");

		newItem = new JMenuItem("New");
		newItem.setActionCommand("new");
		beginnerItem = new JMenuItem("Beginner");
		beginnerItem.setActionCommand("difficulty");
		intermediateItem = new JMenuItem("Intermediate");
		intermediateItem.setActionCommand("difficulty");
		expertItem = new JMenuItem("Expert");
		expertItem.setActionCommand("difficulty");
		quitItem = new JMenuItem("Quit");
		quitItem.setActionCommand("quit");

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
	 * Adds a listener to the 'new' menu item.
	 * 
	 * @param listener
	 *            The listener to be added to the appropriate menu item
	 */
	public void addClearListener(ActionListener listener) {
		newItem.addActionListener(listener);
	}

	/**
	 * Adds a listener to the 'quit' menu item.
	 * 
	 * @param listener
	 *            The listener to be added to the appropriate menu item
	 */
	public void addQuitListener(ActionListener listener) {
		quitItem.addActionListener(listener);

	}

	/**
	 * Adds a listener to the difficulty menu items.
	 * 
	 * @param listener
	 *            The listener to be added to the appropriate menu items
	 */
	public void addDiffListener(ActionListener listener) {
		beginnerItem.addActionListener(listener);
		intermediateItem.addActionListener(listener);
		expertItem.addActionListener(listener);
	}

	/**
	 * 
	 * @param listener
	 */
	public void addListeners(ActionListener listener) {
		newItem.addActionListener(listener);
		quitItem.addActionListener(listener);
		beginnerItem.addActionListener(listener);
		intermediateItem.addActionListener(listener);
		expertItem.addActionListener(listener);
	}
}
