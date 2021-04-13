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

	private Background background, onHoverBackground;
	private int width, height;

	public GameButton(ImageResource imageResource, int width, int height) {
		this.width = width;
		this.height = height;
		background = new Background(new BackgroundImage(ResourceManager.getImage(imageResource),
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

	public void setOnHoverBackground(ImageResource imageResource) {
		onHoverBackground = new Background(new BackgroundImage(ResourceManager.getImage(imageResource),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(width, height, false, false, false, false)));

		this.setOnMouseEntered((e) -> {
			this.setBackground(onHoverBackground);
		});
		this.setOnMouseExited((e) -> {
			this.setBackground(background);
		});
	}

}
