package gui;

import config.Config;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logic.GameState;
import sound.SoundManager;
import update.Updatable;
import utility.Logger;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import utility.ResourceManager.SoundResource;

public class TitleScenePane extends StackPane implements Updatable {

	private GameText textGameName1, textGameName2;
	private GameButton btnNewGame, btnLoadGame, btnExitGame;
	private VBox menuBar;

	public TitleScenePane() {
		textGameName1 = new GameText(Config.TITLE_LABEL1, Config.SCREEN_H / 18);
		textGameName1.setAlignment(Pos.CENTER);
		textGameName1.setTranslateY(-Config.SCREEN_H / 3);

		textGameName2 = new GameText(Config.TITLE_LABEL2, Config.SCREEN_H / 9);
		textGameName2.setAlignment(Pos.CENTER);
		textGameName2.setTranslateY(-Config.SCREEN_H / 5);

		btnNewGame = new GameButton("New game", ImageResource.BTN, Config.TITLE_BTN_W, Config.TITLE_BTN_H);
		btnNewGame.setTextFill(Color.WHITE);
		btnNewGame.setOnMouseClicked((e) -> {
			Logger.log("Button New Game Click");
			GameState.setSceneResource(SceneResource.MODE_SELECTING);
		});
		btnNewGame.setOnHoverBackground(ImageResource.BTN_HOVER);

		btnLoadGame = new GameButton("Load game", ImageResource.BTN, Config.TITLE_BTN_W, Config.TITLE_BTN_H);
		btnLoadGame.setTextFill(Color.WHITE);
		btnLoadGame.setOnMouseClicked((e) -> {
			Logger.log("Button Load Game Click");
		});
		btnLoadGame.setOnHoverBackground(ImageResource.BTN_HOVER);

		btnExitGame = new GameButton("Exit", ImageResource.BTN, Config.TITLE_BTN_W, Config.TITLE_BTN_H);
		btnExitGame.setTextFill(Color.WHITE);
		btnExitGame.setOnMouseClicked((e) -> {
			Logger.log("Button Exit Game Click");
			GameState.closeGameStage();
		});
		btnExitGame.setOnHoverBackground(ImageResource.BTN_HOVER);

		menuBar = new VBox();
		menuBar.setSpacing(Config.TITLE_BTN_SPACING);
		menuBar.getChildren().addAll(btnNewGame, btnLoadGame, btnExitGame);
		menuBar.setAlignment(Pos.CENTER);
		menuBar.setTranslateY(Config.SCREEN_H / 5);

		this.getChildren().addAll(
				ResourceManager.getImageView(ImageResource.BG_TITLE, Config.SCREEN_W, Config.SCREEN_H), menuBar,
				textGameName1, textGameName2);
	}

	public void update() {
		if (SoundManager.getCurrentSoundResource() != SoundResource.TITLE) {
			SoundManager.setCurrentSound(SoundResource.TITLE);
		}
	}

	public boolean isRemoveFromUpdate() {
		return GameState.getSceneResource() != SceneResource.TITLE;
	}

}
