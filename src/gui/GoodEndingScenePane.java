package gui;

import java.util.ArrayList;

import config.Config;
import input.InputManager;
import input.Inputable;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import logic.GameState;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;

public class GoodEndingScenePane extends StackPane implements Inputable {
	private ArrayList<ImageResource> goodEndingImage = new ArrayList<ImageResource>();
	private ArrayList<String> text = new ArrayList<String>();
	private GameText currentText, skipText;
	private ImageView imageView;
	private int currentImageIndex;

	public GoodEndingScenePane() {
		currentImageIndex = 0;
		
		goodEndingImage.add(ImageResource.ENDING_GOOD_1);
		goodEndingImage.add(ImageResource.END_CREDIT);
		
		text.add("After hours of fight, you finally defeated all enemies and have been called the \"World Dominator\" ever since.");
		text.add("");
		
		skipText = new GameText(Config.SKIP_TEXT, Config.SCREEN_H / 25, Color.GREY);
		skipText.setAlignment(Pos.CENTER);
		skipText.setTranslateY(Config.SCREEN_H / 2.25);
		
		currentText = new GameText(text.get(0), Config.SCREEN_H / 20, Color.WHITE);
		currentText.setPrefWidth(Config.SCREEN_W / 1.25);
		currentText.setAlignment(Pos.CENTER);
		currentText.setTextAlignment(TextAlignment.LEFT);
		currentText.setTranslateY(Config.SCREEN_H / 3);
		currentText.setWrapText(true);
		
		imageView = new ImageView();
		imageView.setImage(ResourceManager.getImage(goodEndingImage.get(0)));
		imageView.setFitWidth(Config.SCREEN_W);
		imageView.setFitHeight(Config.SCREEN_H);
		this.getChildren().addAll(imageView, currentText, skipText);
		InputManager.addInputableObject(this);
	}

	public void processInput() {
		if (GameState.getSceneResource() == SceneResource.ENDING_GOOD) {
			if (InputManager.isKeyClick(KeyCode.SPACE)) {
				if(currentImageIndex == goodEndingImage.size()-1) {
					GameState.closeGameStage();
				} else {
					currentImageIndex += 1;
					imageView.setImage(ResourceManager.getImage(goodEndingImage.get(currentImageIndex)));
					currentText.setText(text.get(currentImageIndex));
				}
			}
		}
	}
}
