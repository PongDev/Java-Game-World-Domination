package gui;

import config.Config;
import javafx.scene.layout.StackPane;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;

public class PlayScenePane extends StackPane {

	public PlayScenePane() {
		this.getChildren()
				.addAll(ResourceManager.getImageView(ImageResource.BG_TITLE, Config.SCREEN_W, Config.SCREEN_H));
	}

}
