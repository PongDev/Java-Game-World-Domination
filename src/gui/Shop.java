package gui;

import config.Config;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class Shop extends TilePane {

	public Shop() {
		this.setVisible(false);
		this.setMinHeight(Config.SCREEN_H / 1.1);
		this.setMaxHeight(Config.SCREEN_H / 1.1);
		this.setMinWidth(Config.SCREEN_W / 1.1);
		this.setMaxWidth(Config.SCREEN_W / 1.1);
		this.setBackground(
				new Background(new BackgroundFill(Color.rgb(160, 160, 160, 0.8), CornerRadii.EMPTY, new Insets(5))));
	}

	public void toggleVisible() {
		this.setVisible(!this.isVisible());
	}

}
