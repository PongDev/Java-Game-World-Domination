package gui;

import config.Config;
import input.InputManager;
import input.Inputable;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import logic.GameState;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;

/**
 * Bad Ending Scene: Display When Player Died
 */
public class BadEndingScenePane extends StackPane implements Inputable {

	/**
	 * Text Showing Skip Message
	 */
	private GameText skipText;

	/**
	 * Is ScenePane Allow Trigger
	 */
	private boolean isAllowTrigger;

	/**
	 * BadEndingScenePane Constructor
	 */
	public BadEndingScenePane() {
		isAllowTrigger = false;

		skipText = new GameText(Config.SKIP_TEXT, Config.SCREEN_H / 27, Color.BLACK);
		skipText.setAlignment(Pos.CENTER);
		skipText.setTranslateY(Config.SCREEN_H / 2.25);

		this.getChildren().addAll(
				ResourceManager.getImageView(ImageResource.ENDING_BAD, Config.SCREEN_W, Config.SCREEN_H), skipText);
		InputManager.addInputableObject(this);
	}

	/**
	 * Change scene When Player Press [SPACE]
	 */
	public void processInput() {
		if (GameState.getSceneResource() == SceneResource.ENDING_BAD) {
			if (InputManager.isKeyClick(KeyCode.SPACE) && isAllowTrigger) {
				GameState.setSceneResource(SceneResource.END_CREDIT);
			} else if (!InputManager.isKeyPress(KeyCode.SPACE) && !isAllowTrigger) {
				isAllowTrigger = true;
			}
		}
	}
}
