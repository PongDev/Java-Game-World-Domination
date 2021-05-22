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
	/**
	 * Game Name First Half Text
	 */
	private GameText textGameName1;
	/**
	 * Game Name Second Half Text
	 */
	private GameText textGameName2;
	/**
	 * New Game Button
	 */
	private GameButton btnNewGame;
	/**
	 * Exit Game Button
	 */
	private GameButton btnExitGame;
	/**
	 * VBox Contains BtnNewGame, BtnExitGame
	 */
	private VBox menuBar;

	/**
	 * TitleScenePane Constructor
	 */
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

		btnExitGame = new GameButton("Exit", ImageResource.BTN, Config.TITLE_BTN_W, Config.TITLE_BTN_H);
		btnExitGame.setTextFill(Color.WHITE);
		btnExitGame.setOnMouseClicked((e) -> {
			Logger.log("Button Exit Game Click");
			GameState.closeGameStage();
		});
		btnExitGame.setOnHoverBackground(ImageResource.BTN_HOVER);

		menuBar = new VBox();
		menuBar.setSpacing(Config.TITLE_BTN_SPACING);
		menuBar.getChildren().addAll(btnNewGame, btnExitGame);
		menuBar.setAlignment(Pos.CENTER);
		menuBar.setTranslateY(Config.SCREEN_H / 5);

		this.getChildren().addAll(
				ResourceManager.getImageView(ImageResource.BG_TITLE, Config.SCREEN_W, Config.SCREEN_H), menuBar,
				textGameName1, textGameName2);
	}

	/**
	 * Play BackGround Music
	 */
	public void update() {
		if (SoundManager.getCurrentBGMSoundResource() != SoundResource.TITLE) {
			SoundManager.setCurrentBGM(SoundResource.TITLE);
		}
	}
	
	/**
	 * Is Scene Removed From Update
	 */
	public boolean isRemoveFromUpdate() {
		return GameState.getSceneResource() != SceneResource.TITLE;
	}

}
