package gui;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import utility.ResourceManager;

/**
 * Game Text Class
 */
public class GameText extends Label {

	/**
	 * GameText Main Constructor
	 * @param text 
	 * @param fontSize
	 */
	public GameText(String text, double fontSize) {
		Font font = Font.loadFont(ResourceManager.getFontResourceStream(), fontSize);
		this.setFont(font);
		this.setText(text);
	}

	/**
	 * GameText Alternative Constructor
	 * @param text
	 * @param fontSize
	 * @param color
	 */
	public GameText(String text, double fontSize, Color color) {
		this(text, fontSize);
		this.setTextFill(color);
	}

}
