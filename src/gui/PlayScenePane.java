package gui;

import java.util.Date;
import config.Config;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import logic.GameState;
import sound.SoundManager;
import update.Updatable;
import utility.ResourceManager.SceneResource;
import utility.ResourceManager.SoundResource;
import utility.WaveManager;

public class PlayScenePane extends StackPane implements Updatable {

	private GameText waveText;
	private GameText pauseText;

	public PlayScenePane() {
		waveText = new GameText("Wave " + Integer.toString(WaveManager.getWave()), Config.SCREEN_H / 9);
		waveText.setAlignment(Pos.CENTER);
		waveText.setTranslateY(-Config.SCREEN_H / 3);
		waveText.setVisible(false);

		pauseText = new GameText(Config.PAUSE_MESSAGE, Config.SCREEN_H / 5);
		pauseText.setAlignment(Pos.CENTER);
		pauseText.setVisible(false);
		this.getChildren().addAll(GameState.getGameMap(), waveText, pauseText);
	}

	public void update() {
		if (WaveManager.isDisplayWaveText == true) {
			waveText.setText("Wave " + Integer.toString(WaveManager.getWave()));
			waveText.setVisible(true);
			if ((new Date()).getTime() - WaveManager.time > 2000) {
				WaveManager.isDisplayWaveText = false;
				waveText.setVisible(false);
			}
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
