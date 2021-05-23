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

/**
 * Game Button Class
 */
public class GameButton extends Button {

	/**
	 * Image Of The Button
	 */
	private Background background;

	/**
	 * Image Of The Button When Hover
	 */
	private Background onHoverBackground;

	/**
	 * Width Of The Button
	 */
	private int width;

	/**
	 * Height Of The Button
	 */
	private int height;

	/**
	 * GameButton Main Constructor
	 * 
	 * @param imageResource ImageResource Of the button
	 * @param width         Button Width
	 * @param height        Button Height
	 */
	public GameButton(ImageResource imageResource, int width, int height) {
		this.width = width;
		this.height = height;
		background = new Background(new BackgroundImage(ResourceManager.getImage(imageResource),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(width, height, false, false, false, false)));

		this.setBackground(background);
		this.setPrefWidth(width);
		this.setPrefHeight(height);
	}

	/**
	 * GameButton Alternative ConStructor
	 * 
	 * @param text          Text To Display On Button
	 * @param imageResource ImageResource Of The Button
	 * @param width         Button Width
	 * @param height        Button Height
	 */
	public GameButton(String text, ImageResource imageResource, int width, int height) {
		this(imageResource, width, height);
		this.setFont(Font.loadFont(ResourceManager.getFontResourceStream(), height / 2));
		this.setTextFill(Color.BLACK);
		this.setText(text);
	}

	/**
	 * Set Button Image When Hover
	 * 
	 * @param imageResource ImageResource Of Hover Image
	 */
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
