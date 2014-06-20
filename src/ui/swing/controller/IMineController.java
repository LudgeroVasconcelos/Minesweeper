package ui.swing.controller;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;


public interface IMineController extends Observer{
	
	public ActionListener newGame();
	
	public ActionListener reveal();
	
	public MouseListener toggleMark();
	
	public void addObserverToGrid();

	public void update(Observable obj, Object hint);
	
	
}
