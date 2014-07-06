package ui.view.swing;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MineFrame extends JFrame {

	private GridPanel grid;

	public MineFrame(int rows, int columns) {
		grid = new GridPanel(rows, columns);

		setLayout(new BorderLayout());
		setSize(500, 500);
		setLocationRelativeTo(null);
		setTitle("Minesweeper");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		add(grid, BorderLayout.CENTER);
	}

	public void addListenersToButtons(ActionListener al, MouseListener ml){
		grid.addListenersToButtons(al, ml);
	}
	
	public void removeListenersFromButtons(){
		grid.removeListenersFromButtons();
	}
	
	public void removeButton(int x, int y) {
		grid.removeButton(x, y);
	}

	public void displayNumber(int x, int y, int numMinesAround) {
		grid.displayNumber(x, y, numMinesAround);
	}

	public void toggleFlag(int x, int y) {
		grid.toggleFlag(x, y);
		
	}

	public void endGame(int x, int y, Iterable<Point> mines) {
		grid.destroy(x, y);
		
		// remove button on every mine
		for(Point p : mines){
			if(!grid.isFlagged(p.x, p.y))
				removeButton(p.x, p.y);
			grid.setMine(p.x, p.y);
		}
		
		// also remove buttons that have flag but are not mined
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 20; j++){
				if(!grid.isMined(i, j) && grid.isFlagged(i, j)){
					removeButton(i, j);
					grid.setWrong(i, j);
				}
			}
		}
	}

}
