package gui;

import javafx.scene.Group;
import logic.GameState;
import sound.SoundManager;
import update.Updatable;
import utility.ResourceManager.SceneResource;
import utility.ResourceManager.SoundResource;

public class PlayScenePane extends Group implements Updatable {

	public PlayScenePane() {
		this.getChildren().addAll(GameState.getGameMap());
	}

	public void update() {
		if (SoundManager.getCurrentSoundResource() != SoundResource.PLAYING) {
			SoundManager.setCurrentSound(SoundResource.PLAYING);
		}
	}
	
	public boolean isRemoveFromUpdate() {
		return GameState.getSceneResource() != SceneResource.PLAYING;
	}

}
