package gui;

import config.Config;
import input.InputManager;
import input.Inputable;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import logic.GameState;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;

public class BadEndingScenePane extends StackPane implements Inputable{
	private ImageView currentImage;
	private GameText skipText;
	
	public BadEndingScenePane(){
		currentImage = ResourceManager.getImageView(ImageResource.ENDING_BAD, Config.SCREEN_W, Config.SCREEN_H);
		
		skipText = new GameText(Config.SKIP_TEXT, Config.SCREEN_H / 25, Color.BLACK);
		skipText.setAlignment(Pos.CENTER);
		skipText.setTranslateY(Config.SCREEN_H / 2.25);
		
		this.getChildren().addAll(currentImage,skipText);
		InputManager.addInputableObject(this);
	}
	
	public void processInput() {
		if (GameState.getSceneResource() == SceneResource.ENDING_BAD) {
			if (InputManager.isKeyClick(KeyCode.SPACE)) {
				GameState.setSceneResource(SceneResource.TITLE);
			}
		}
	}
}
