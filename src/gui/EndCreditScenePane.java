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

public class EndCreditScenePane extends StackPane implements Inputable {
	/**
	 * Text Showing Skip Message
	 */
	private GameText skipText;

	/**
	 * Is ScenePane Allow Trigger
	 */
	private boolean isAllowTrigger;

	/**
	 * EndCreditScenePane Constructor
	 */
	public EndCreditScenePane() {
		isAllowTrigger = false;
		
		skipText = new GameText(Config.SKIP_TEXT, Config.SCREEN_H / 27, Color.GRAY);
		skipText.setAlignment(Pos.CENTER);
		skipText.setTranslateY(Config.SCREEN_H / 2.25);

		this.getChildren().addAll(
				ResourceManager.getImageView(ImageResource.END_CREDIT, Config.SCREEN_W, Config.SCREEN_H), skipText);
		InputManager.addInputableObject(this);
	}

	/**
	 * Close Program When Player Press [SPACE]
	 */
	public void processInput() {
		if (GameState.getSceneResource() == SceneResource.END_CREDIT) {
			if (InputManager.isKeyClick(KeyCode.SPACE) && isAllowTrigger) {
				GameState.closeGameStage();
			}
			else if (!InputManager.isKeyPress(KeyCode.SPACE) && !isAllowTrigger) {
				isAllowTrigger = true;
			}
		}
	}
}
