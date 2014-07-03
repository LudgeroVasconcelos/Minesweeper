package main;

import ui.controller.swing.IMineController;
import ui.controller.swing.MineController;
import domain.MineFacade;
import domain.MinesWeeper;


public class Main {

	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// create the application
				MineFacade mine = new MinesWeeper();
				
				// create controller		(controller -> model)
				IMineController mineController = new MineController(mine);
				
				//add observer
				mineController.addObserverToGrid();
			}
		});
		
	}
}
