package model;

public final class MineTimer {

	private static final int MAX = 999;
	private boolean running;
	private long time;

	public void start() {
		running = true;
		time = System.currentTimeMillis();
	}

	public void reset() {
		running = false;
		time = 0;
	}

	public void stop() {
		running = false;
		time = getTimeInSeconds();
	}

	public int getCurrentTime() {
		long currentTime = running ? getTimeInSeconds() : time;

		return currentTime > MAX ? MAX : (int) currentTime;
	}

	private long getTimeInSeconds() {
		return (System.currentTimeMillis() - time) / 1000 + 1;
	}
}
