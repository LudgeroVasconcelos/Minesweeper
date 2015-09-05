package minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import model.Difficulty;
import view.UiFacade;

/**
 * The properties of the game.
 * 
 * The Singleton pattern is used here. Only one instance of this class is needed
 * and provides global access point.
 * 
 * @author Ludgero
 * 
 */
public enum MineProperties {
	INSTANCE;

	public final int ROWS_BEGINNER;
	public final int COLUMNS_BEGINNER;
	public final int NUMBER_OF_MINES_BEGINNER;

	public final int ROWS_INTERMEDIATE;
	public final int COLUMNS_INTERMEDIATE;
	public final int NUMBER_OF_MINES_INTERMEDIATE;

	public final int ROWS_EXPERT;
	public final int COLUMNS_EXPERT;
	public final int NUMBER_OF_MINES_EXPERT;

	public Difficulty DEFAULT_DIFFICULTY;

	public Image MINE_IMAGE;
	public Image CROSS_IMAGE;
	public Image FLAG_IMAGE;
	public Image SMILE_IMAGE;
	public Image SMILE_SAD_IMAGE;
	public Image SMILE_HAPPY_IMAGE;
	public Image SMILE_SURPRISED_IMAGE;
	public Image RANKING_IMAGE;

	public final int BUTTON_WIDTH;
	public final int BUTTON_HEIGHT;

	public final Color[] COLORS;

	public final Font DIGITAL_FONT;

	private Properties mineProperties;

	MineProperties() {
		String propertiesFileName = "minesweeper.properties";
		mineProperties = new Properties();

		try {
			mineProperties.load(getClass().getResourceAsStream(
					propertiesFileName));
		} catch (Exception e) {
			// It was not able to load properties file.
			// Bad luck, proceed with the default values
		}

		ROWS_BEGINNER = parseInt("rows_beginner", 9);
		COLUMNS_BEGINNER = parseInt("columns_beginner", 9);
		NUMBER_OF_MINES_BEGINNER = parseInt("number_of_mines_beginner", 10);

		ROWS_INTERMEDIATE = parseInt("rows_intermediate", 16);
		COLUMNS_INTERMEDIATE = parseInt("columns_intermediate", 16);
		NUMBER_OF_MINES_INTERMEDIATE = parseInt("number_of_mines_intermediate",
				40);

		ROWS_EXPERT = parseInt("rows_expert", 16);
		COLUMNS_EXPERT = parseInt("columns_expert", 30);
		NUMBER_OF_MINES_EXPERT = parseInt("number_of_mines_expert", 99);

		String dif = parseString("difficulty", "BEGINNER").toUpperCase();
		DEFAULT_DIFFICULTY = Difficulty.valueOf(dif);

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

		String digitalFont = parseString("digital_font", "font/digital-7.ttf");
		Font font;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,
					UiFacade.class.getResourceAsStream(digitalFont));
		} catch (Exception e) {
			font = new Font("Dialog", Font.BOLD, 30);
		}
		DIGITAL_FONT = font.deriveFont(50f);

		try {
			MINE_IMAGE = ImageIO.read(UiFacade.class.getResource(parseString(
					"mine_image", "images/Mine.png")));
			CROSS_IMAGE = ImageIO.read(UiFacade.class.getResource(parseString(
					"cross_image", "images/Icon_cross.png")));
			FLAG_IMAGE = ImageIO.read(UiFacade.class.getResource(parseString(
					"flag_image", "images/flag.png")));
			SMILE_IMAGE = ImageIO.read(UiFacade.class.getResource(parseString(
					"smile_image", "images/smile.png")));
			SMILE_SAD_IMAGE = ImageIO.read(UiFacade.class
					.getResource(parseString("smile_sad_image",
							"images/smile-sad.png")));
			SMILE_HAPPY_IMAGE = ImageIO.read(UiFacade.class
					.getResource(parseString("smile_happy_image",
							"images/smile-happy.png")));
			SMILE_SURPRISED_IMAGE = ImageIO.read(UiFacade.class
					.getResource(parseString("smile_surprised_image",
							"images/smile-surprised.png")));
			RANKING_IMAGE = ImageIO.read(UiFacade.class
					.getResource(parseString("ranking_image",
							"images/RankingIcon.png")));
		} catch (IOException e) {
			System.err.println("Could not load image");
			// bad luck, no images will be shown.
		}
	}

	/**
	 * Tries to read the property from the properties file and converts it to an
	 * integer. If it fails, the default value will be used.
	 * 
	 * @param property
	 *            The property to be read from the properties file
	 * @param defaultValue
	 *            The default value for when it fails to read the file
	 * 
	 * @return The value of the given property
	 */
	private int parseInt(String property, int defaultValue) {
		try {
			return Integer.parseInt(mineProperties.getProperty(property));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * Tries to read the property from the properties file and converts it to a
	 * String. If it fails, the default value will be used.
	 * 
	 * @param property
	 *            The property to be read from the properties file
	 * @param defaultValue
	 *            The default value for when it fails to read the file
	 * 
	 * @return The value of the given property
	 */
	private String parseString(String property, String defaultValue) {
		String value = mineProperties.getProperty(property);
		return value == null ? defaultValue : value;
	}
}
