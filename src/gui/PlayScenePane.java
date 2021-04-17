package gui;

import javafx.scene.Group;
import logic.GameState;

public class PlayScenePane extends Group {

	public PlayScenePane() {
		this.getChildren().addAll(GameState.getGameMap());
	}

}
