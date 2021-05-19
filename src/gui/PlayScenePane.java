package gui;

import config.Config;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import logic.GameState;
import sound.SoundManager;
import update.Updatable;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import utility.ResourceManager.SoundResource;
import utility.ResourceManager;
import utility.WaveManager;

public class PlayScenePane extends StackPane implements Updatable {

	private GameText waveText;
	private GameText pauseText;

	public PlayScenePane() {
		Image cursorImage = ResourceManager.getImage(ImageResource.CROSS_HAIR);
		this.setCursor(new ImageCursor(cursorImage, cursorImage.getWidth() / 2, cursorImage.getHeight() / 2));

		waveText = new GameText("", Config.SCREEN_H / 9);
		waveText.setAlignment(Pos.CENTER);
		waveText.setTranslateY(-Config.SCREEN_H / 3);
		waveText.setVisible(false);

		pauseText = new GameText(Config.PAUSE_MESSAGE, Config.SCREEN_H / 5);
		pauseText.setAlignment(Pos.CENTER);
		pauseText.setVisible(false);
		this.getChildren().addAll(GameState.getGameMap(), waveText, pauseText);
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

		if (SoundManager.getCurrentSoundResource() != SoundResource.PLAYING) {
			SoundManager.setCurrentSound(SoundResource.PLAYING);
		}
		pauseText.setVisible(GameState.isPause());
	}

	public boolean isRemoveFromUpdate() {
		return GameState.getSceneResource() != SceneResource.PLAYING;
	}

}
