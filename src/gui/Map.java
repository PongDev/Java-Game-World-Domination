package gui;

import java.util.ArrayList;
import config.Config;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import logic.GameState;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;

public class Map extends GridPane {

	private ArrayList<Tile> mapData = new ArrayList<Tile>();
	private Rectangle screenClip;

	public Map() {
		screenClip = new Rectangle(Config.SCREEN_W, Config.SCREEN_H);
		this.setMinWidth(GameState.getMapWidth() * Config.TILE_W);
		this.setMaxWidth(GameState.getMapWidth() * Config.TILE_W);
		this.setMinHeight(GameState.getMapHeight() * Config.TILE_H);
		this.setMaxHeight(GameState.getMapHeight() * Config.TILE_H);
		for (int rowPos = 0; rowPos < GameState.getMapHeight(); rowPos++) {
			for (int colPos = 0; colPos < GameState.getMapWidth(); colPos++) {
				ImageResource tileImage;
				String tileCode = ResourceManager.getMap()[rowPos][colPos];

				switch (tileCode) {
				case "0":
					tileImage = ImageResource.TILE_FLOOR;
					break;
				case "1":
					tileImage = ImageResource.TILE_FLOOR;
					break;
				case "2":
					tileImage = ImageResource.TILE_FLOOR;
					break;
				case "W":
					tileImage = ImageResource.TILE_WALL;
					break;
				default:
					tileImage = ImageResource.TILE_WALL;
					break;
				}

				Tile tile = new Tile(tileImage, tileCode);

				mapData.add(tile);
				this.add(tile, colPos, rowPos);
			}
		}
		this.setTranslateY((Config.SCREEN_H / 2) - ((GameState.getMapHeight() * Config.TILE_H) / 2));
	}

}
