package gui;

import config.Config;
import javafx.scene.layout.GridPane;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;

public class PlayScenePane extends GridPane {

	Map map;

	public PlayScenePane() {
		map = new Map();
		this.getChildren()
				.addAll(ResourceManager.getImageView(ImageResource.BG_TITLE, Config.SCREEN_W, Config.SCREEN_H), map);
	}

}
