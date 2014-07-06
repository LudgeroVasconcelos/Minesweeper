package ui.view.swing;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class MineButton extends JButton {

	private final int x, y;
	private int numberOfMinesAround;
	private boolean flaged;
	private boolean mined;
	private boolean destroyed;
	private boolean wrong;
	
	private ActionListener al;
	private MouseListener ml;

	public MineButton(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public void addListeners(ActionListener al, MouseListener ml){
		addActionListener(al);
		addMouseListener(ml);
		
		this.al = al;
		this.ml = ml;
	}
	
	public void removeListeners(){
		removeActionListener(al);
		removeMouseListener(ml);
	}
	public void setNumOfMinesAround(int numMinesAround) {
		this.numberOfMinesAround = numMinesAround;
	}

	public int getNumOfMinesAround() {
		return numberOfMinesAround;
	}

	public int getPosX() {
		return x;
	}

	public int getPosY() {
		return y;
	}

	public void toggleFlag() {
		flaged = !flaged;
	}

	public boolean isFlagged() {
		return flaged;
	}

	public void setMine() {

		mined = true;
	}

	public boolean isMined() {
		return mined;
	}
	
	public void destroy(){
		destroyed = true;
	}
	
	public boolean isDestroyed(){
		return destroyed;
	}

	public void setWrong() {
		wrong = true;
		
	}
	
	public boolean isWrong(){
		return wrong;
	}
}
