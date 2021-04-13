package gui;

import java.util.ArrayList;
import java.util.Iterator;

import config.Config;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import utility.ResourceManager.ImageResource;

public class Map extends GridPane {

	private ArrayList<Tile> mapData = new ArrayList<Tile>();

	public Map() {
		this.setAlignment(Pos.CENTER);
		this.setPrefWidth(Config.SCREEN_W);
		this.setPrefHeight(Config.SCREEN_H);
		for (int rowPos = 0; rowPos < Config.MAP_TILE_H; rowPos++) {
			for (int colPos = 0; colPos < Config.MAP_TILE_W; colPos++) {
				Tile tile = new Tile(ImageResource.TILE_FLOOR);

				mapData.add(tile);
				this.add(tile, colPos, rowPos);
			}
		}
	}

}
