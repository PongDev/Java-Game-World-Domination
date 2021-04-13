package gui;

import java.util.ArrayList;

import com.sun.media.jfxmedia.logging.Logger;

import config.Config;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import logic.GameState;
import render.Renderable;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;

public class Map extends Canvas implements Renderable {

	private Tile[][] mapData;
	private int mapPosX, mapPosY, mapCenterX, mapCenterY;
	private GraphicsContext gc;

	public Map() {
		super(Config.SCREEN_W, Config.SCREEN_H);
		gc = this.getGraphicsContext2D();
		mapCenterX = (int) (Config.TILE_W * 1.5);
		mapCenterY = (GameState.getMapHeight() * Config.TILE_H) / 2;
		calculateMapPos();

		mapData = new Tile[GameState.getMapHeight()][GameState.getMapWidth()];
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

				mapData[rowPos][colPos] = new Tile(tileImage, tileCode);
			}
		}
		render();
	}

	private void calculateMapPos() {
		mapPosX = mapCenterX - (Config.SCREEN_W / 2);
		mapPosY = mapCenterY - (Config.SCREEN_H / 2);
	}

	public void render() {
		for (int rowPos = 0; rowPos < GameState.getMapHeight(); rowPos++) {
			for (int colPos = 0; colPos < GameState.getMapWidth(); colPos++) {
				if ((-mapPosX + (colPos * Config.TILE_W) <= Config.SCREEN_W
						&& -mapPosY + (rowPos * Config.TILE_H) <= Config.SCREEN_H)
						|| (-mapPosX + (colPos * Config.TILE_W) + Config.TILE_W >= 0
								&& -mapPosY + (rowPos * Config.TILE_H) + Config.TILE_H >= 0)) {
					gc.drawImage(ResourceManager.getImage(mapData[rowPos][colPos].getImageResource()),
							-mapPosX + (colPos * Config.TILE_W), -mapPosY + (rowPos * Config.TILE_H), Config.TILE_W,
							Config.TILE_H);
				}
			}
		}
	}

}
