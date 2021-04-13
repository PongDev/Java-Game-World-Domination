package gui;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class GameText extends Label {

	public GameText(String text, double fontSize) {
		this.setFont(new Font(fontSize));
		this.setText(text);
	}

}
