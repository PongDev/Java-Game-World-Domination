package gui;

import config.Config;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.GameState;
import utility.Logger;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;

public class ModeSelectScenePane extends StackPane implements Updatable {

	private GameText textGameMode;
	private GameButton btnPlay, btnBack, btnNext, btnPrevious;
	private VBox playBar;

	public ModeSelectScenePane() {

		textGameMode = new GameText(GameState.getGameMode().getGameModeName(), Config.SCREEN_H / 15);
		textGameMode.setAlignment(Pos.CENTER);
		textGameMode.setTranslateY(-Config.SCREEN_H / 3);
		btnPlay = new GameButton("Play", ImageResource.BTN, Config.TITLE_BTN_W, Config.TITLE_BTN_H);
		btnPlay.setOnMouseClicked((e) -> {
			Logger.log("Button Play Game Click");
			GameState.setSceneResource(SceneResource.PLAYING);
		});
		btnBack = new GameButton(ImageResource.BTN_BACK, Config.MODE_SELECT_BTN_SIZE, Config.MODE_SELECT_BTN_SIZE);
		btnBack.setOnMouseClicked((e) -> {
			Logger.log("Button Back Click");
			GameState.setSceneResource(SceneResource.TITLE);
		});
		btnBack.setTranslateX(-Config.SCREEN_W / 3);
		btnBack.setTranslateY(-Config.SCREEN_H / 3);
		btnNext = new GameButton(ImageResource.BTN_NEXT, Config.MODE_SELECT_BTN_SIZE, Config.MODE_SELECT_BTN_SIZE);
		btnNext.setOnMouseClicked((e) -> {
			Logger.log("Button Next Click");
			GameState.setGameMode(GameState.getGameMode().getNextGameMode());
			GameState.setRequestSceneUpdate(true);
		});
		btnNext.setTranslateX(Config.SCREEN_W / 3);
		btnNext.setTranslateY(Config.SCREEN_H / 4);
		btnPrevious = new GameButton(ImageResource.BTN_PREVIOUS, Config.MODE_SELECT_BTN_SIZE,
				Config.MODE_SELECT_BTN_SIZE);
		btnPrevious.setOnMouseClicked((e) -> {
			Logger.log("Button Previous Click");
			GameState.setGameMode(GameState.getGameMode().getPreviousGameMode());
			GameState.setRequestSceneUpdate(true);
		});
		btnPrevious.setTranslateX(-Config.SCREEN_W / 3);
		btnPrevious.setTranslateY(Config.SCREEN_H / 4);

		playBar = new VBox();
		playBar.setSpacing(Config.TITLE_BTN_SPACING);
		playBar.getChildren().addAll(btnPlay);
		playBar.setAlignment(Pos.CENTER);
		playBar.setTranslateY(Config.SCREEN_H / 4);

		this.getChildren().addAll(
				ResourceManager.getImageView(ImageResource.BG_TITLE, Config.SCREEN_W, Config.SCREEN_H), playBar,
				btnBack, btnNext, btnPrevious, textGameMode);
	}

	public void update() {
		textGameMode.setText(GameState.getGameMode().getGameModeName());
	}

}
