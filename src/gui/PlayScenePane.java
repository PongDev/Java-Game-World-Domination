package gui;

import javafx.scene.Group;

public class PlayScenePane extends Group {

	Map map;

	public PlayScenePane() {
		map = new Map();
		this.getChildren().addAll(map);
	}

}
