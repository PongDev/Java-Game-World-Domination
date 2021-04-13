package gui;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import utility.ResourceManager;

public class GameText extends Label {

	public GameText(String text, double fontSize) {
		Font font = Font.loadFont(ResourceManager.getFontResourceStream(), fontSize);
		this.setFont(font);
		this.setText(text);
	}

}
