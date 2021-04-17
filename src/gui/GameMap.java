package gui;

import config.Config;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import logic.GameState;
import render.Renderable;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;

public class GameMap extends Canvas implements Renderable {

	private Tile[][] mapData;
	private int mapPosX, mapPosY, mapCenterX, mapCenterY;

	public GameMap() {
		super(Config.SCREEN_W, Config.SCREEN_H);
		mapCenterX = (int) (Config.TILE_W * 1.5);
		mapCenterY = (GameState.getMapHeight() * Config.TILE_H) / 2;
		calculateMapPos();

		mapData = new Tile[GameState.getMapHeight()][GameState.getMapWidth()];
		for (int rowPos = 0; rowPos < GameState.getMapHeight(); rowPos++) {
			for (int colPos = 0; colPos < GameState.getMapWidth(); colPos++) {
				ImageResource tileImage;
				String tileCode = ResourceManager.getMapResource()[rowPos][colPos];

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
	}

	private void calculateMapPos() {
		mapPosX = mapCenterX - (Config.SCREEN_W / 2);
		mapPosY = mapCenterY - (Config.SCREEN_H / 2);
	}

	public void render() {
		GraphicsContext gc = this.getGraphicsContext2D();

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

	public boolean isAllowRender() {
		return GameState.getSceneResource() == SceneResource.PLAYING;
	}

	public int getZ() {
		return Config.ZINDEX_MAP;
	}

	public boolean isDestroyed() {
		return false;
	}

	public int getMapPosX() {
		return mapPosX;
	}

	public int getMapPosY() {
		return mapPosY;
	}

}
