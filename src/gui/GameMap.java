package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import character.Enemy;
import config.Config;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.GameState;
import object.GameObject;
import render.Renderable;
import utility.Position;
import utility.ResourceManager;
import utility.ResourceManager.GameObjectResource;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;

public class GameMap extends Canvas implements Renderable {

	private Tile[][] mapData;
	private Position mapPos, mapCenter;
	private static ArrayList<Position> enemySpawnableTile = new ArrayList<Position>();

	public GameMap() {
		super(Config.SCREEN_W, Config.SCREEN_H);
		mapPos = new Position();
		mapCenter = new Position();

		mapData = new Tile[GameState.getMapHeight()][GameState.getMapWidth()];
		for (int rowPos = 0; rowPos < GameState.getMapHeight(); rowPos++) {
			for (int colPos = 0; colPos < GameState.getMapWidth(); colPos++) {
				ImageResource tileImage;
				String tileCode = ResourceManager.getMapResource()[rowPos][colPos];
				boolean isWalkable, isPlacable, isPenetrable;
				Map<Integer, Boolean> isWhitelist = new HashMap<Integer, Boolean>();

				switch (tileCode) {
				case "0":
					int randomFloorTile = (int) (Math.random() * 100);
					if (randomFloorTile < 80) {
						tileImage = ImageResource.TILE_FLOOR;
					} else if (randomFloorTile < 90) {
						tileImage = ImageResource.TILE_FLOOR_1;
					} else {
						tileImage = ImageResource.TILE_FLOOR_2;
					}
					isWalkable = true;
					isPlacable = true;
					isPenetrable = true;
					isWhitelist.put(0, true);
					isWhitelist.put(1, true);
					break;
				case "1":
					tileImage = ImageResource.TILE_UNPLACABLE_FLOOR;
					isWalkable = true;
					isPlacable = false;
					isPenetrable = true;
					isWhitelist.put(0, true);
					isWhitelist.put(1, true);
					break;
				case "2":
					tileImage = ImageResource.TILE_UNWALKABLE_FLOOR;
					isWalkable = false;
					isPlacable = false;
					isPenetrable = true;
					isWhitelist.put(0, false);
					isWhitelist.put(1, true);
					enemySpawnableTile.add(new Position(colPos, rowPos));
					break;
				case "W":
					tileImage = ImageResource.TILE_WALL;
					isWalkable = false;
					isPlacable = false;
					isPenetrable = false;
					isWhitelist.put(0, false);
					isWhitelist.put(1, false);
					break;
				default:
					tileImage = ImageResource.TILE_WALL;
					isWalkable = false;
					isPlacable = false;
					isPenetrable = false;
					break;
				}

				mapData[rowPos][colPos] = new Tile(tileImage, tileCode, isWalkable, isPlacable, isPenetrable,
						isWhitelist);
			}
		}
		this.getGraphicsContext2D()
				.setFont(Font.loadFont(ResourceManager.getFontResourceStream(), Config.SCREEN_H / 30));
	}

	private void calculateMapPos() {
		mapPos.X = mapCenter.X - (Config.SCREEN_W / 2);
		mapPos.Y = mapCenter.Y - (Config.SCREEN_H / 2);
	}

	public void render() {
		mapCenter = ResourceManager.getGameObject(GameObjectResource.MAIN_CHARACTER).getCenterPos();
		calculateMapPos();
		GraphicsContext gc = this.getGraphicsContext2D();

		gc.setFill(Color.rgb(64, 64, 64));
		gc.fillRect(0, 0, Config.SCREEN_W, Config.SCREEN_H);
		for (int rowPos = 0; rowPos < GameState.getMapHeight(); rowPos++) {
			for (int colPos = 0; colPos < GameState.getMapWidth(); colPos++) {
				if ((-mapPos.X + (colPos * Config.TILE_W) <= Config.SCREEN_W
						&& -mapPos.Y + (rowPos * Config.TILE_H) <= Config.SCREEN_H)
						|| (-mapPos.X + (colPos * Config.TILE_W) + Config.TILE_W >= 0
								&& -mapPos.Y + (rowPos * Config.TILE_H) + Config.TILE_H >= 0)) {
					if (mapData[rowPos][colPos].isHighlight()) {
						mapData[rowPos][colPos].setHighlight(false);
						gc.setGlobalAlpha(0.5);
					}
					gc.drawImage(ResourceManager.getImage(mapData[rowPos][colPos].getImageResource()),
							-mapPos.X + (colPos * Config.TILE_W), -mapPos.Y + (rowPos * Config.TILE_H), Config.TILE_W,
							Config.TILE_H);
					gc.setGlobalAlpha(1.0);
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

	public static ArrayList<Position> getEnemySpawnableTile() {
		return enemySpawnableTile;
	}

	public Position getMapPos() {
		return mapPos;
	}

	public boolean isWalkable(double posX, double posY, int team) {
		int posRow = (int) (posY / Config.TILE_H);
		int posCol = (int) (posX / Config.TILE_W);

		if (posY < 0 || posX < 0 || posRow < 0 || posCol < 0 || posRow >= mapData.length
				|| posCol >= mapData[0].length) {
			return false;
		}

		if (mapData[posRow][posCol].IsWhitelist(team)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isWalkable(double posX, double posY, GameObject gameObject) {
		return isWalkable(posX, posY, gameObject.getTeam());
	}

	public boolean isWalkable(GameObject gameObject, int deltaX, int deltaY) {
		return isWalkable(gameObject.getPos().X + deltaX, gameObject.getPos().Y + deltaY, gameObject)
				&& isWalkable(gameObject.getPos().X + gameObject.getWidth() - 1 + deltaX,
						gameObject.getPos().Y + deltaY, gameObject)
				&& isWalkable(gameObject.getPos().X + deltaX,
						gameObject.getPos().Y + gameObject.getHeight() - 1 + deltaY, gameObject)
				&& isWalkable(gameObject.getPos().X + gameObject.getWidth() - 1 + deltaX,
						gameObject.getPos().Y + gameObject.getHeight() - 1 + deltaY, gameObject);
	}

	public boolean isPenetrable(double posX, double posY) {
		int posRow = (int) (posY / Config.TILE_H);
		int posCol = (int) (posX / Config.TILE_W);

		if (posY < 0 || posX < 0 || posRow < 0 || posCol < 0 || posRow >= mapData.length
				|| posCol >= mapData[0].length) {
			return false;
		}
		return mapData[posRow][posCol].isPenetrable();
	}

	public boolean isPenetrable(GameObject gameObject, double deltaX, double deltaY) {
		return isPenetrable(gameObject.getPos().X + deltaX, gameObject.getPos().Y + deltaY)
				&& isPenetrable(gameObject.getPos().X + gameObject.getWidth() - 1 + deltaX,
						gameObject.getPos().Y + deltaY)
				&& isPenetrable(gameObject.getPos().X + deltaX,
						gameObject.getPos().Y + gameObject.getHeight() - 1 + deltaY)
				&& isPenetrable(gameObject.getPos().X + gameObject.getWidth() - 1 + deltaX,
						gameObject.getPos().Y + gameObject.getHeight() - 1 + deltaY);
	}

	public void setHighLightTile(int row, int col) {
		if (row >= 0 && col >= 0 && row < GameState.getMapHeight() && col < GameState.getMapWidth()) {
			mapData[row][col].setHighlight(true);
		}
	}

}
