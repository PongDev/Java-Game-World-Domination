package gui;

import config.Config;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logic.GameState;
import sound.SoundManager;
import update.Updatable;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import utility.ResourceManager.SoundResource;
import utility.ResourceManager;
import utility.WaveManager;

public class PlayScenePane extends StackPane implements Updatable {

	private AnchorPane gameUI, statusPane;
	private StackPane statusBox, healhtPotionBox, ammoBox;
	private GameText waveText, pauseText;
	private GameText statusBarWaveText, coinText, healthPotionText, ammoText;
	private ImageView statusPaneImage, healthPotionpPaneImage, ammoPaneImage;
	private int wave = 0;

	public PlayScenePane() {
		Image cursorImage = ResourceManager.getImage(ImageResource.CROSS_HAIR);
		this.setCursor(new ImageCursor(cursorImage, cursorImage.getWidth() / 2, cursorImage.getHeight() / 2));

		statusBarWaveText = new GameText("Wave 99", Config.SCREEN_H / 20, Color.WHITE);
		coinText = new GameText("999", Config.SCREEN_H / 20, Color.WHITE);
		statusPaneImage = ResourceManager.getImageView(ImageResource.STATUS_PANE, (int) (Config.SCREEN_W / 4),
				(int) (Config.SCREEN_H / 15));
		statusPane = new AnchorPane();
	    AnchorPane.setLeftAnchor(statusBarWaveText, 10.0);
	    AnchorPane.setRightAnchor(coinText, 10.0);
	    statusPane.getChildren().addAll(coinText,statusBarWaveText);
		statusBox = new StackPane();
		statusBox.getChildren().addAll(statusPaneImage,statusPane);
		
		healthPotionText = new GameText("0", Config.SCREEN_H / 20, Color.WHITE);//***
		healthPotionText.setAlignment(Pos.CENTER);
		healthPotionText.setTranslateY(Config.SCREEN_H / 25);
		healthPotionpPaneImage = ResourceManager.getImageView(ImageResource.HEALTH_POTION_PANE, (int) (Config.SCREEN_W / 10),
				(int) (Config.SCREEN_H / 6.5));
		healhtPotionBox = new StackPane();
		healhtPotionBox.getChildren().addAll(healthPotionpPaneImage,healthPotionText);
		
		ammoText = new GameText("0", Config.SCREEN_H / 20, Color.WHITE);//***
		ammoText.setAlignment(Pos.CENTER);
		ammoPaneImage = ResourceManager.getImageView(ImageResource.AMMO_PANE, (int) (Config.SCREEN_W / 10),
				(int) (Config.SCREEN_H / 15));
		ammoBox = new StackPane();
		ammoBox.getChildren().addAll(ammoPaneImage,ammoText);
		
		gameUI = new AnchorPane();
		AnchorPane.setTopAnchor(statusBox, 10.0);
	    AnchorPane.setLeftAnchor(statusBox, 10.0);
		
		AnchorPane.setTopAnchor(healhtPotionBox, 10.0);
	    AnchorPane.setRightAnchor(healhtPotionBox, 10.0);
		
		AnchorPane.setBottomAnchor(ammoBox, 10.0);
	    AnchorPane.setRightAnchor(ammoBox, 10.0);
	    gameUI.getChildren().addAll(ammoBox,healhtPotionBox,statusBox);
		
		waveText = new GameText("", Config.SCREEN_H / 9);
		waveText.setAlignment(Pos.CENTER);
		waveText.setTranslateY(-Config.SCREEN_H / 3);
		waveText.setVisible(false);

		pauseText = new GameText(Config.PAUSE_MESSAGE, Config.SCREEN_H / 5);
		pauseText.setAlignment(Pos.CENTER);
		pauseText.setVisible(false);
		this.getChildren().addAll(GameState.getGameMap(), waveText, pauseText, gameUI);
	}

	public void update() {
		if (!WaveManager.getDisplayWaveText().isBlank()) {
			if (WaveManager.getDisplayWaveText() != waveText.getText()) {
				waveText.setText(WaveManager.getDisplayWaveText());
			}
			waveText.setVisible(true);
		} else {
			waveText.setVisible(false);
		}
		
		if(wave != WaveManager.getWave()) {
			statusBarWaveText.setText("Wave "+Integer.toString(WaveManager.getWave()));
			wave = WaveManager.getWave();
		}

		if (SoundManager.getCurrentSoundResource() != SoundResource.PLAYING) {
			SoundManager.setCurrentSound(SoundResource.PLAYING);
		}
		pauseText.setVisible(GameState.isPause());
	}

	public boolean isRemoveFromUpdate() {
		return GameState.getSceneResource() != SceneResource.PLAYING;
	}

}
