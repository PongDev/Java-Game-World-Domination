package gui;

import config.Config;
import input.InputManager;
import input.Inputable;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import logic.GameState;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;

/**
 * Good Ending Scene: Display When Player Finish Wave 30 In Normal Mode
 */
public class GoodEndingScenePane extends StackPane implements Inputable {
	
	/**
	 * Current Text
	 */
	private GameText currentText;
	
	/**
	 * Text Showing Skip Message
	 */
	private GameText skipText;
	
	/**
	 * Is ScenePane Allow Trigger
	 */
	private boolean isAllowTrigger;

	/**
	 * GoodEndingScenePane Constructor
	 */
	public GoodEndingScenePane() {
		isAllowTrigger = false;

		skipText = new GameText(Config.SKIP_TEXT, Config.SCREEN_H / 27, Color.GREY);
		skipText.setAlignment(Pos.CENTER);
		skipText.setTranslateY(Config.SCREEN_H / 2.25);

		currentText = new GameText(
				"After hours of fight, you finally defeated all enemies and have been called the \"World Dominator\" ever since.",
				Config.SCREEN_H / 20, Color.WHITE);
		currentText.setPrefWidth(Config.SCREEN_W / 1.25);
		currentText.setAlignment(Pos.CENTER);
		currentText.setTextAlignment(TextAlignment.LEFT);
		currentText.setTranslateY(Config.SCREEN_H / 3);
		currentText.setWrapText(true);

		this.getChildren().addAll(
				ResourceManager.getImageView(ImageResource.ENDING_GOOD_1, Config.SCREEN_W, Config.SCREEN_H), skipText,
				currentText);
		InputManager.addInputableObject(this);
	}

	/**
	 * Change scene When Player Press [SPACE]
	 */
	public void processInput() {
		if (GameState.getSceneResource() == SceneResource.ENDING_GOOD) {
			if (InputManager.isKeyClick(KeyCode.SPACE) && isAllowTrigger) {
				GameState.setSceneResource(SceneResource.END_CREDIT);
			}
			else if (!InputManager.isKeyPress(KeyCode.SPACE) && !isAllowTrigger) {
				isAllowTrigger = true;
			}
		}
	}
}
