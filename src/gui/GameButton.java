package gui;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;

public class GameButton extends Button {

	public GameButton(ImageResource imageResource, int width, int height) {
		ImageView imageView = ResourceManager.getImageView(imageResource, width, height);

		this.setGraphic(imageView);
		this.setMinWidth(imageView.getFitWidth());
		this.setMaxWidth(imageView.getFitWidth());
		this.setMinHeight(imageView.getFitHeight());
		this.setMaxHeight(imageView.getFitHeight());
	}

}
