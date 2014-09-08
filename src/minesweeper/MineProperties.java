package minesweeper;

import java.awt.Color;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import ui.view.swing.GridPanel;

public enum MineProperties {
	INSTANCE;

	public final int ROWS;
	public final int COLUMNS;
	public final int NUMBER_OF_MINES;

	public Image MINE_IMAGE;
	public Image CROSS_IMAGE;
	public Image FLAG_IMAGE;

	public final int BUTTON_WIDTH;
	public final int BUTTON_HEIGHT;

	public final Color[] COLORS;

	private Properties mineProperties;

	MineProperties() {
		String propertiesFileName = "minesweeper.properties";
		mineProperties = new Properties();
		
		try {
			mineProperties.load(new FileInputStream(propertiesFileName));
		} catch (Exception e) {
			// It was not able to load properties file.
			// Bad luck, proceed with the default values
		}

		ROWS = parseInt("rows", 20);
		COLUMNS = parseInt("columns", 20);
		NUMBER_OF_MINES = parseInt("number_of_mines", 70);

		BUTTON_WIDTH = parseInt("button_width", 25);
		BUTTON_HEIGHT = parseInt("button_height", 25);

		COLORS = new Color[8];
		COLORS[0] = new Color(parseInt("1", 1602765));
		COLORS[1] = new Color(parseInt("2", 2330147));
		COLORS[2] = new Color(parseInt("3", 16711680));
		COLORS[3] = new Color(parseInt("4", 190));
		COLORS[4] = new Color(parseInt("5", 9118499));
		COLORS[5] = new Color(parseInt("6", 1990430));
		COLORS[6] = new Color(parseInt("7", 5197615));
		COLORS[7] = new Color(parseInt("8", 0));

		loadImages();
	}

	private void loadImages() {
		try {
			MINE_IMAGE = ImageIO.read(GridPanel.class.getResource(parseString(
					"mine_image", "images/Mine.png")));
			CROSS_IMAGE = ImageIO.read(GridPanel.class.getResource(parseString(
					"cross_image", "images/Icon_cross.png")));
			FLAG_IMAGE = ImageIO.read(GridPanel.class.getResource(parseString(
					"flag_image", "images/RedFlag.png")));

		} catch (IOException e) {
			System.err.println("Could not load image");
			// bad luck, no images will be shown.
		}
	}

	private int parseInt(String property, int defaultValue) {
		try {
			return Integer.parseInt(mineProperties.getProperty(property));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	private String parseString(String property, String defaultValue) {
		String value = mineProperties.getProperty(property);
		return value == null ? defaultValue : value;
	}
}
