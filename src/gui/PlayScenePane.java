package gui;

import config.Config;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import logic.GameState;
import sound.SoundManager;
import update.Updatable;
import utility.ResourceManager.SceneResource;
import utility.ResourceManager.SoundResource;

public class PlayScenePane extends StackPane implements Updatable {

	GameText pauseText;

	public PlayScenePane() {
		pauseText = new GameText(Config.PAUSE_MESSAGE, Config.SCREEN_H / 5);
		pauseText.setAlignment(Pos.CENTER);
		pauseText.setVisible(false);
		this.getChildren().addAll(GameState.getGameMap(), pauseText);
	}

	public void update() {
		if (SoundManager.getCurrentSoundResource() != SoundResource.PLAYING) {
			SoundManager.setCurrentSound(SoundResource.PLAYING);
		}
		pauseText.setVisible(GameState.isPause());
	}

	public boolean isRemoveFromUpdate() {
		return GameState.getSceneResource() != SceneResource.PLAYING;
	}

}
