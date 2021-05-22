package gui;

import java.util.ArrayList;

import config.Config;
import input.InputManager;
import input.Inputable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import logic.GameState;
import sound.SoundManager;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import utility.ResourceManager.SoundResource;
import utility.WaveManager;

public class BeginningLorePane extends StackPane implements Inputable {
	private ArrayList<ImageResource> beginningLoreImage = new ArrayList<ImageResource>();
	private ArrayList<String> text = new ArrayList<String>();
	private GameText currentText, skipText;
	private ImageView imageView;
	private int currentImageIndex;

	public BeginningLorePane() {
		currentImageIndex = 0;

		beginningLoreImage.add(ImageResource.BEGINNING_LORE_1);
		beginningLoreImage.add(ImageResource.BEGINNING_LORE_2);
		beginningLoreImage.add(ImageResource.BEGINNING_LORE_3);
		beginningLoreImage.add(ImageResource.BEGINNING_LORE_4);
		beginningLoreImage.add(ImageResource.BEGINNING_LORE_5);
		beginningLoreImage.add(ImageResource.BEGINNING_GAME_CONTROL);

		text.add("You are a normal girl. But one day, an unfortunate event happen to you.");
		text.add("You got hit by a truck kun.");
		text.add("...");
		text.add("Am I... Am I dead yet? \n Where am I?");
		text.add(
				"Suddenly you woke up and found out that you have been travel back to the great war. You must fight to survive!");
		text.add("");

		skipText = new GameText(Config.SKIP_TEXT, Config.SCREEN_H / 27, Color.GREY);
		skipText.setAlignment(Pos.CENTER);
		skipText.setTranslateY(Config.SCREEN_H / 2.25);

		currentText = new GameText(text.get(0), Config.SCREEN_H / 20, Color.WHITE);
		currentText.setPrefWidth(Config.SCREEN_W / 1.25);
		currentText.setAlignment(Pos.CENTER);
		currentText.setTextAlignment(TextAlignment.LEFT);
		currentText.setTranslateY(Config.SCREEN_H / 3);
		currentText.setWrapText(true);

		imageView = new ImageView();
		imageView.setImage(ResourceManager.getImage(beginningLoreImage.get(0)));
		imageView.setFitWidth(Config.SCREEN_W);
		imageView.setFitHeight(Config.SCREEN_H);
		this.getChildren().addAll(imageView, currentText, skipText);
		InputManager.addInputableObject(this);
	}

	public void processInput() {
		if (GameState.getSceneResource() == SceneResource.BEGINNING_LORE) {
			if (InputManager.isKeyClick(KeyCode.SPACE)) {
				if (currentImageIndex == beginningLoreImage.size() - 1) {
					WaveManager.initialize();
					GameState.setSceneResource(SceneResource.PLAYING);
				} else {
					currentImageIndex += 1;
					if (currentImageIndex == 1) {
						SoundManager.playSoundEffect(SoundResource.CAR_CRASH, 0.5);
					}
					imageView.setImage(ResourceManager.getImage(beginningLoreImage.get(currentImageIndex)));
					currentText.setText(text.get(currentImageIndex));
				}
			}
		}
	}
}
