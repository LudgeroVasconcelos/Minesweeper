package controller.swing;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import model.IMineFacade;
import model.events.ClearEvent;
import model.events.GameLostEvent;
import model.events.GameWonEvent;
import model.events.StartGameEvent;
import view.UiFacade;

/**
 * 
 * @author Ludgero
 *
 */
public class TimeController implements Observer, Runnable {

	private IMineFacade domainHandler;
	private UiFacade uiHandler;

	private ScheduledExecutorService executor;

	/**
	 * Constructs and initializes a new controller for the timer.
	 * 
	 * @param domainHandler
	 *            The model component of the mvc architecture pattern
	 * @param uiHandler
	 *            The view component of the mvc architecture pattern
	 */
	public TimeController(IMineFacade domainHandler, UiFacade uiHandler) {
		this.domainHandler = domainHandler;
		this.uiHandler = uiHandler;

		init();
	}

	/**
	 * Creates the executor responsible for updating the view and sets the timer
	 * to zero.
	 */
	private void init() {
		executor = Executors.newSingleThreadScheduledExecutor();
		uiHandler.setTime(0);
	}

	/**
	 * Adds this controller as an observer that will listen to notifications
	 * from the model component.
	 */
	public void addObservers() {
		domainHandler.addObserver(this);
	}

	@Override
	public void run() {
		int time = domainHandler.getCurrentTime();
		uiHandler.setTime(time);
	}

	@Override
	public void update(Observable arg0, Object hint) {

		if (hint instanceof StartGameEvent)
			executor.scheduleAtFixedRate(this, 0, 500, TimeUnit.MILLISECONDS);

		else if (hint instanceof GameLostEvent || hint instanceof GameWonEvent)
			executor.shutdown();

		else if (hint instanceof ClearEvent) {
			executor.shutdown();
			init();
		}
	}
}
