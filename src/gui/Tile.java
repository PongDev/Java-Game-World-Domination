package gui;

import config.Config;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;

public class Tile extends Pane {

	private Background background;
	private String tileCode;

	public Tile(ImageResource imageResource, String tileCode) {
		this.tileCode = tileCode;
		background = new Background(new BackgroundImage(ResourceManager.getImage(imageResource),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(Config.TILE_W, Config.TILE_H, false, false, false, false)));
		this.setBackground(background);
		this.setPrefWidth(Config.TILE_W);
		this.setPrefHeight(Config.TILE_H);
	}

}
