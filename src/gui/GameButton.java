package gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import utility.Logger;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;

public class GameButton extends Button {

	public GameButton(ImageResource imageResource) {
		ImageView imageView = ResourceManager.getImage(imageResource);

		this.setGraphic(imageView);
		this.setMinWidth(imageView.getFitWidth());
		this.setMaxWidth(imageView.getFitWidth());
		this.setMinHeight(imageView.getFitHeight());
		this.setMaxHeight(imageView.getFitHeight());
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Logger.log("Button Click");
			}
		});
	}

}
