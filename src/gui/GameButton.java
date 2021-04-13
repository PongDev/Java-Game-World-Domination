package gui;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;

public class GameButton extends Button {

	public GameButton(ImageResource imageResource, int width, int height) {
		Background background = new Background(new BackgroundImage(ResourceManager.getImage(imageResource),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(width, height, false, false, false, false)));

		this.setBackground(background);
		this.setMinWidth(width);
		this.setMaxWidth(width);
		this.setMinHeight(height);
		this.setMaxHeight(height);
	}

	public GameButton(String text, ImageResource imageResource, int width, int height) {
		this(imageResource, width, height);
		this.setFont(new Font(height / 2));
		this.setTextFill(Color.BLACK);
		this.setText(text);
	}

}
