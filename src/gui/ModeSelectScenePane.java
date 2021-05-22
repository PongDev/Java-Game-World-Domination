package gui;

import config.Config;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
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
import utility.WaveManager;

public class ModeSelectScenePane extends StackPane implements Updatable {

	private GameText textGameMode;
	private GameText infoText;
	private GameButton btnPlay, btnBack, btnNext, btnPrevious;
	private VBox playBar;
	private ImageView infoImage;
	private boolean isRequestUpdate = false;

	public ModeSelectScenePane() {

		textGameMode = new GameText(GameState.getGameMode().getGameModeName(), Config.SCREEN_H / 15);
		textGameMode.setAlignment(Pos.CENTER);
		textGameMode.setTranslateY(-Config.SCREEN_H / 3);

		infoText = new GameText(GameState.getGameMode().getGameModeInfoText(), Config.SCREEN_H / 18, Color.BLACK);
		infoText.setAlignment(Pos.CENTER);
		infoText.setTranslateY(Config.SCREEN_H / 7);

		btnPlay = new GameButton("Play", ImageResource.BTN, Config.TITLE_BTN_W, Config.TITLE_BTN_H);
		btnPlay.setTextFill(Color.WHITE);
		btnPlay.setOnMouseClicked((e) -> {
			Logger.log("Button Play Game Click");
			
			GameState.setSceneResource(SceneResource.BEGINNING_LORE);
			//WaveManager.initialize();
			//GameState.setSceneResource(SceneResource.PLAYING);
		});
		btnPlay.setOnHoverBackground(ImageResource.BTN_HOVER);

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
			isRequestUpdate = true;
		});
		btnNext.setTranslateX(Config.SCREEN_W / 3);
		btnNext.setTranslateY(Config.SCREEN_H / 3.15);

		btnPrevious = new GameButton(ImageResource.BTN_PREVIOUS, Config.MODE_SELECT_BTN_SIZE,
				Config.MODE_SELECT_BTN_SIZE);
		btnPrevious.setOnMouseClicked((e) -> {
			Logger.log("Button Previous Click");
			GameState.setGameMode(GameState.getGameMode().getPreviousGameMode());
			isRequestUpdate = true;
		});
		btnPrevious.setTranslateX(-Config.SCREEN_W / 3);
		btnPrevious.setTranslateY(Config.SCREEN_H / 3.15);

		playBar = new VBox();
		playBar.setSpacing(Config.TITLE_BTN_SPACING);
		playBar.getChildren().addAll(btnPlay);
		playBar.setAlignment(Pos.CENTER);
		playBar.setTranslateY(Config.SCREEN_H / 3.15);

		infoImage = ResourceManager.getImageView(ImageResource.INFO_NORMALMODE, (int) (Config.SCREEN_W / 1.25),
				(int) (Config.SCREEN_H / 1.25));

		this.getChildren().addAll(
				ResourceManager.getImageView(ImageResource.BG_TITLE, Config.SCREEN_W, Config.SCREEN_H), infoImage,
				playBar, btnBack, btnNext, btnPrevious, textGameMode, infoText);
	}

	public void update() {
		if (isRequestUpdate) {
			textGameMode.setText(GameState.getGameMode().getGameModeName());
			infoImage.setImage(ResourceManager.getImage(GameState.getGameMode().getGameModeInfoImageResource()));
			infoText.setText(GameState.getGameMode().getGameModeInfoText());
			isRequestUpdate = false;
		}
		if (SoundManager.getCurrentBGMSoundResource() != SoundResource.TITLE) {
			SoundManager.setCurrentBGM(SoundResource.TITLE);
		}
	}

	public boolean isRemoveFromUpdate() {
		return GameState.getSceneResource() != SceneResource.MODE_SELECTING;
	}

}
