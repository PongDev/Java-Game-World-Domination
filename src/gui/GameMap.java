package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import config.Config;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.GameState;
import object.GameObject;
import object.ObjectManager;
import render.Renderable;
import tower.Tower;
import utility.Position;
import utility.ResourceManager;
import utility.ResourceManager.GameObjectResource;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import weapon.Bullet;

public class GameMap extends Canvas implements Renderable {

	private Tile[][] mapData;
	private Tower[][] deployedTower;
	private Position mapPos, mapCenter;
	private static Position shopPos;
	private static ArrayList<Position> enemySpawnableTile = new ArrayList<Position>();

	public GameMap() {
		super(Config.SCREEN_W, Config.SCREEN_H);
		mapPos = new Position();
		mapCenter = new Position();

		mapData = new Tile[GameState.getMapHeight()][GameState.getMapWidth()];
		deployedTower = new Tower[GameState.getMapHeight()][GameState.getMapWidth()];
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
				case "S":
					tileImage = ImageResource.TILE_SHOP;
					isWalkable = true;
					isPlacable = false;
					isPenetrable = true;
					isWhitelist.put(0, true);
					isWhitelist.put(1, true);
					shopPos = new Position(colPos, rowPos);
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
	
	public static Position getShopPos() {
		return shopPos;
	}

	public boolean isCollideTower(double posX, double posY) {
		int posRow = (int) (posY / Config.TILE_H);
		int posCol = (int) (posX / Config.TILE_W);

		if (posRow >= 0 && posCol >= 0 && posRow < GameState.getMapHeight() && posCol < GameState.getMapWidth()) {
			if (deployedTower[posRow][posCol] != null) {
				if (deployedTower[posRow][posCol].isDestroyed()) {
					deployedTower[posRow][posCol] = null;
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isCollideTower(GameObject gameObject, double deltaX, double deltaY) {
		return isCollideTower(gameObject.getPos().X + deltaX, gameObject.getPos().Y + deltaY)
				|| isCollideTower(gameObject.getPos().X + gameObject.getWidth() - 1 + deltaX,
						gameObject.getPos().Y + deltaY)
				|| isCollideTower(gameObject.getPos().X + deltaX,
						gameObject.getPos().Y + gameObject.getHeight() - 1 + deltaY)
				|| isCollideTower(gameObject.getPos().X + gameObject.getWidth() - 1 + deltaX,
						gameObject.getPos().Y + gameObject.getHeight() - 1 + deltaY);
	}

	public boolean isCollideTeamTower(double posX, double posY, GameObject gameObject) {
		int posRow = (int) (posY / Config.TILE_H);
		int posCol = (int) (posX / Config.TILE_W);

		if (posRow >= 0 && posCol >= 0 && posRow < GameState.getMapHeight() && posCol < GameState.getMapWidth()) {
			if (deployedTower[posRow][posCol] != null) {
				if (deployedTower[posRow][posCol].isDestroyed()) {
					deployedTower[posRow][posCol] = null;
					return false;
				} else {
					if (gameObject instanceof Bullet
							&& ((Bullet) gameObject).getOwner() == deployedTower[posRow][posCol]) {
						return false;
					}
					return deployedTower[posRow][posCol].getTeam() == gameObject.getTeam();
				}
			}
		}
		return false;
	}

	public boolean isCollideTeamTower(GameObject gameObject, double deltaX, double deltaY) {
		return isCollideTeamTower(gameObject.getPos().X + deltaX, gameObject.getPos().Y + deltaY, gameObject)
				|| isCollideTeamTower(gameObject.getPos().X + gameObject.getWidth() - 1 + deltaX,
						gameObject.getPos().Y + deltaY, gameObject)
				|| isCollideTeamTower(gameObject.getPos().X + deltaX,
						gameObject.getPos().Y + gameObject.getHeight() - 1 + deltaY, gameObject)
				|| isCollideTeamTower(gameObject.getPos().X + gameObject.getWidth() - 1 + deltaX,
						gameObject.getPos().Y + gameObject.getHeight() - 1 + deltaY, gameObject);
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

	public boolean isWalkable(GameObject gameObject, double deltaX, double deltaY) {
		return isWalkable(gameObject.getPos().X + deltaX, gameObject.getPos().Y + deltaY, gameObject)
				&& isWalkable(gameObject.getPos().X + gameObject.getWidth() - 1 + deltaX,
						gameObject.getPos().Y + deltaY, gameObject)
				&& isWalkable(gameObject.getPos().X + deltaX,
						gameObject.getPos().Y + gameObject.getHeight() - 1 + deltaY, gameObject)
				&& isWalkable(gameObject.getPos().X + gameObject.getWidth() - 1 + deltaX,
						gameObject.getPos().Y + gameObject.getHeight() - 1 + deltaY, gameObject);
	}

	public boolean isWalkableAndNotCollideTower(GameObject gameObject, double deltaX, double deltaY) {
		return isWalkable(gameObject, deltaX, deltaY) && !isCollideTower(gameObject, deltaX, deltaY);
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

	public boolean isPlacable(int row, int col) {
		return (row >= 0 && col >= 0 && row < GameState.getMapHeight() && col < GameState.getMapWidth())
				? mapData[row][col].isPlacable()
				: false;
	}

	public void setHighLightTile(int row, int col) {
		if (row >= 0 && col >= 0 && row < GameState.getMapHeight() && col < GameState.getMapWidth()) {
			mapData[row][col].setHighlight(true);
		}
	}

	public boolean deployTower(Tower tower) {
		int row = tower.getTowerRow();
		int col = tower.getTowerCol();

		if (row >= 0 && col >= 0 && row < GameState.getMapHeight() && col < GameState.getMapWidth()
				&& ObjectManager.isTowerDeployable(tower)) {
			deployedTower[row][col] = tower;
			deployedTower[row][col].deploy();
			return true;
		}
		return false;
	}

}
