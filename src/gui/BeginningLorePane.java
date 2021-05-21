package gui;

import java.util.ArrayList;

import config.Config;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;

public class BeginningLorePane extends StackPane {
	private ArrayList<ImageResource> beginningLoreImage = new ArrayList<ImageResource>();
	private ImageView currentImage;
	
	public BeginningLorePane() {
		beginningLoreImage.add(ImageResource.BEGINNING_LORE);
		
		currentImage = ResourceManager.getImageView(beginningLoreImage.get(0), Config.SCREEN_W, Config.SCREEN_H);
		this.getChildren().addAll(currentImage);
	}
}
