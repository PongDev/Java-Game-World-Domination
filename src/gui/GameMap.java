package gui;

import config.Config;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

	public GameMap() {
		super(Config.SCREEN_W, Config.SCREEN_H);
		mapPos = new Position();
		mapCenter = new Position();

		mapData = new Tile[GameState.getMapHeight()][GameState.getMapWidth()];
		for (int rowPos = 0; rowPos < GameState.getMapHeight(); rowPos++) {
			for (int colPos = 0; colPos < GameState.getMapWidth(); colPos++) {
				ImageResource tileImage;
				String tileCode = ResourceManager.getMapResource()[rowPos][colPos];
				boolean isWalkable, isPlacable;

				switch (tileCode) {
				case "0":
					tileImage = ImageResource.TILE_FLOOR;
					isWalkable = true;
					isPlacable = true;
					break;
				case "1":
					tileImage = ImageResource.TILE_UNPLACABLE_FLOOR;
					isWalkable = true;
					isPlacable = false;
					break;
				case "2":
					tileImage = ImageResource.TILE_UNWALKABLE_FLOOR;
					isWalkable = false;
					isPlacable = false;
					break;
				case "W":
					tileImage = ImageResource.TILE_WALL;
					isWalkable = false;
					isPlacable = false;
					break;
				default:
					tileImage = ImageResource.TILE_WALL;
					isWalkable = true;
					isPlacable = true;
					break;
				}

				mapData[rowPos][colPos] = new Tile(tileImage, tileCode, isWalkable, isPlacable);
			}
		}
	}

	private void calculateMapPos() {
		mapPos.X = mapCenter.X - (Config.SCREEN_W / 2);
		mapPos.Y = mapCenter.Y - (Config.SCREEN_H / 2);
	}

	public void render() {
		mapCenter = ResourceManager.getGameObject(GameObjectResource.MAIN_CHARACTER).getCenterPos();
		calculateMapPos();
		GraphicsContext gc = this.getGraphicsContext2D();

		for (int rowPos = 0; rowPos < GameState.getMapHeight(); rowPos++) {
			for (int colPos = 0; colPos < GameState.getMapWidth(); colPos++) {
				if ((-mapPos.X + (colPos * Config.TILE_W) <= Config.SCREEN_W
						&& -mapPos.Y + (rowPos * Config.TILE_H) <= Config.SCREEN_H)
						|| (-mapPos.X + (colPos * Config.TILE_W) + Config.TILE_W >= 0
								&& -mapPos.Y + (rowPos * Config.TILE_H) + Config.TILE_H >= 0)) {
					gc.drawImage(ResourceManager.getImage(mapData[rowPos][colPos].getImageResource()),
							-mapPos.X + (colPos * Config.TILE_W), -mapPos.Y + (rowPos * Config.TILE_H), Config.TILE_W,
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

	public Position getMapPos() {
		return mapPos;
	}

	public boolean isCollide(double posX, double posY) {
		int posRow = (int) (posY / Config.TILE_H);
		int posCol = (int) (posX / Config.TILE_W);

		return mapData[posRow][posCol].isWalkable();
	}

	public boolean isCollide(GameObject gameObject, int deltaX, int deltaY) {
		return isCollide(gameObject.getPos().X + deltaX, gameObject.getPos().Y + deltaY)
				&& isCollide(gameObject.getPos().X + gameObject.getWidth() + deltaX, gameObject.getPos().Y + deltaY)
				&& isCollide(gameObject.getPos().X + deltaX, gameObject.getPos().Y + gameObject.getHeight() + deltaY)
				&& isCollide(gameObject.getPos().X + gameObject.getWidth() + deltaX,
						gameObject.getPos().Y + gameObject.getHeight() + deltaY);
	}

}
