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

public class TitleScenePane extends StackPane {

	private GameText textGameName;
	private GameButton btnNewGame, btnLoadGame, btnExitGame;
	private VBox menuBar;

	public TitleScenePane() {
		textGameName = new GameText(Config.GAME_TITLE, Config.SCREEN_H / 15);
		textGameName.setAlignment(Pos.CENTER);
		textGameName.setTranslateY(-Config.SCREEN_H / 3);

		btnNewGame = new GameButton("New game", ImageResource.BTN, Config.TITLE_BTN_W, Config.TITLE_BTN_H);
		btnNewGame.setOnMouseClicked((e) -> {
			Logger.log("Button New Game Click");
			GameState.setSceneResource(SceneResource.MODE_SELECTING);
		});

		btnLoadGame = new GameButton("Load game", ImageResource.BTN, Config.TITLE_BTN_W, Config.TITLE_BTN_H);
		btnLoadGame.setOnMouseClicked((e) -> {
			Logger.log("Button Load Game Click");
		});

		btnExitGame = new GameButton("Exit", ImageResource.BTN, Config.TITLE_BTN_W, Config.TITLE_BTN_H);
		btnExitGame.setOnMouseClicked((e) -> {
			Logger.log("Button Exit Game Click");
			GameState.closeGameStage();
		});

		menuBar = new VBox();
		menuBar.setSpacing(Config.TITLE_BTN_SPACING);
		menuBar.getChildren().addAll(btnNewGame, btnLoadGame, btnExitGame);
		menuBar.setAlignment(Pos.CENTER);
		menuBar.setTranslateY(Config.SCREEN_H / 4);

		this.getChildren().addAll(
				ResourceManager.getImageView(ImageResource.BG_TITLE, Config.SCREEN_W, Config.SCREEN_H), menuBar,
				textGameName);
	}

}
