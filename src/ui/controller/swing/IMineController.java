package ui.controller.swing;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

public interface IMineController extends Observer {

	public ActionListener clearGrid();

	public MouseListener squaresListener();

	public void addObserverToGrid();

	public void addListeners();

	public void update(Observable obj, Object hint);
}
