package ui.view.swing;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MineMenu extends JMenuBar {

	JMenuItem newItem;
	JMenuItem beginnerItem;
	JMenuItem intermediateItem;
	JMenuItem expertItem;
	JMenuItem quitItem;
	
	public MineMenu(){
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
	
	public void addClearListener(ActionListener clearListener){
		newItem.addActionListener(clearListener);
	}

	public void addQuitListener(ActionListener quitListener) {
		quitItem.addActionListener(quitListener);
		
	}

	public void addDiffListener(ActionListener diffListener) {
		beginnerItem.addActionListener(diffListener);
		intermediateItem.addActionListener(diffListener);
		expertItem.addActionListener(diffListener);
	}
}
