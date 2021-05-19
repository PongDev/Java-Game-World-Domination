package character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import config.Config;
import input.InputManager;
import input.Inputable;
import javafx.scene.input.KeyCode;
import javafx.util.Pair;
import logic.GameState;
import object.ObjectManager;
import render.RenderManager;
import update.Updatable;
import update.UpdateManager;
import utility.Logger;
import utility.Position;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import utility.Utility;
import weapon.Weapon;

public class MainCharacter extends Character implements Inputable, Updatable {

	private static Comparator<Pair<Pair<Integer, Double>, Position>> movingVectorComparator = (
			Pair<Pair<Integer, Double>, Position> obj1, Pair<Pair<Integer, Double>, Position> obj2) -> {
		int manhattanDistance1 = obj1.getKey().getKey();
		int manhattanDistance2 = obj2.getKey().getKey();
		double euclideanDistance1 = obj1.getKey().getValue();
		double euclideanDistance2 = obj1.getKey().getValue();

		if (manhattanDistance1 < manhattanDistance2) {
			return -1;
		} else if (manhattanDistance1 > manhattanDistance2) {
			return 1;
		} else {
			return euclideanDistance1 < euclideanDistance2 ? -1 : 1;
		}
	};

	private int[][] distanceFromCharacter;

	public MainCharacter(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense,
			int speed, Weapon weapon, int team, int centerPosX, int centerPosY) {
		this(imageResource, width, height, name, maxHealth, defense, speed, weapon, team,
				new Position(centerPosX, centerPosY));
	}

	public MainCharacter(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense,
			int speed, Weapon weapon, int team, Position centerPos) {
		super(imageResource, width, height, name, maxHealth, defense, speed, weapon, team, centerPos);
		InputManager.addInputableObject(this);
		UpdateManager.add(this);
		RenderManager.add(this);

		distanceFromCharacter = new int[GameState.getMapHeight()][GameState.getMapWidth()];
	}

	private void calculateDistanceFromCharacter() {
		int posRow = (int) (this.getCenterPos().Y / Config.TILE_H);
		int posCol = (int) (this.getCenterPos().X / Config.TILE_W);

		for (int rowPos = 0; rowPos < GameState.getMapHeight(); rowPos++) {
			for (int colPos = 0; colPos < GameState.getMapWidth(); colPos++) {
				distanceFromCharacter[rowPos][colPos] = -1;
			}
		}

		Queue<Pair<Integer, Position>> queue = new LinkedList<>();

		queue.add(new Pair<>(0, new Position(posRow, posCol)));
		while (!queue.isEmpty()) {
			Position pos = queue.peek().getValue();
			int distance = queue.remove().getKey();
			if ((int) pos.X >= 0 && (int) pos.Y >= 0 && (int) pos.X < distanceFromCharacter.length
					&& (int) pos.Y < distanceFromCharacter[0].length
					&& GameState.getGameMap().isWalkable(posCol * Config.TILE_W, posRow * Config.TILE_H)
					&& distanceFromCharacter[(int) pos.X][(int) pos.Y] == -1) {
				distanceFromCharacter[(int) pos.X][(int) pos.Y] = distance;
				int[] shiftRowIndex = { (int) pos.X, (int) pos.X - 1, (int) pos.X + 1 };
				int[] shiftColIndex = { (int) pos.Y, (int) pos.Y - 1, (int) pos.Y + 1 };

				for (int rowPos = 0; rowPos < shiftRowIndex.length; rowPos++) {
					for (int colPos = 0; colPos < shiftColIndex.length; colPos++) {
						if (shiftRowIndex[rowPos] >= 0 && shiftColIndex[colPos] >= 0
								&& shiftRowIndex[rowPos] < distanceFromCharacter.length
								&& shiftColIndex[colPos] < distanceFromCharacter[0].length
								&& GameState.getGameMap().isWalkable(shiftColIndex[colPos] * Config.TILE_W,
										shiftRowIndex[rowPos] * Config.TILE_H)
								&& distanceFromCharacter[shiftRowIndex[rowPos]][shiftColIndex[colPos]] == -1) {
							int deltaRow = (int) (shiftRowIndex[rowPos] - pos.X);
							int deltaCol = (int) (shiftColIndex[colPos] - pos.Y);
							if (Math.abs(deltaRow) == 1 && Math.abs(deltaCol) == 1) {
								if (GameState.getGameMap().isWalkable(
										(shiftColIndex[colPos] - deltaCol) * Config.TILE_W,
										shiftRowIndex[rowPos] * Config.TILE_H)
										&& GameState.getGameMap().isWalkable(shiftColIndex[colPos] * Config.TILE_W,
												(shiftRowIndex[rowPos] - deltaRow) * Config.TILE_H)) {
									queue.add(new Pair<>(distance + 1,
											new Position(shiftRowIndex[rowPos], shiftColIndex[colPos])));
								}
							} else {
								queue.add(new Pair<>(distance + 1,
										new Position(shiftRowIndex[rowPos], shiftColIndex[colPos])));
							}
						}
					}
				}
			}
		}
	}

	public ArrayList<Position> getTowardMovingVector(Position pos) {
		Position mainCharacterBlock = new Position((int) (this.getCenterPos().Y / Config.TILE_H),
				(int) (this.getCenterPos().X / Config.TILE_W));
		ArrayList<Pair<Pair<Integer, Double>, Position>> movingVectorList = new ArrayList<Pair<Pair<Integer, Double>, Position>>();
		ArrayList<Position> returnMovingVectorList = new ArrayList<Position>();

		int posRow = (int) (pos.Y / Config.TILE_H);
		int posCol = (int) (pos.X / Config.TILE_W);

		int[] shiftRowIndex = { posRow, posRow - 1, posRow + 1 };
		int[] shiftColIndex = { posCol, posCol - 1, posCol + 1 };

		for (int rowPos = 0; rowPos < shiftRowIndex.length; rowPos++) {
			for (int colPos = 0; colPos < shiftColIndex.length; colPos++) {
				if ((rowPos != 0 || colPos != 0) && shiftRowIndex[rowPos] >= 0 && shiftColIndex[colPos] >= 0
						&& shiftRowIndex[rowPos] < distanceFromCharacter.length
						&& shiftColIndex[colPos] < distanceFromCharacter[0].length
						&& distanceFromCharacter[shiftRowIndex[rowPos]][shiftColIndex[colPos]] != -1) {
					double euclideanDistance = Utility.euclideanDistance(mainCharacterBlock,
							new Position(shiftRowIndex[rowPos], shiftColIndex[colPos]));

					movingVectorList.add(new Pair<>(
							new Pair<>(distanceFromCharacter[shiftRowIndex[rowPos]][shiftColIndex[colPos]],
									euclideanDistance),
							new Position(shiftColIndex[colPos] - posCol, shiftRowIndex[rowPos] - posRow)));
				}
			}
		}
		Collections.sort(movingVectorList, movingVectorComparator);
		for (Pair<Pair<Integer, Double>, Position> e : movingVectorList) {
			returnMovingVectorList.add(e.getValue());
		}
		returnMovingVectorList.add(new Position(0, 0));
		return returnMovingVectorList;
	}

	public int getZ() {
		return Config.ZINDEX_MAIN_CHARACTER;
	}

	public void processInput() {
		// ESC
		if (InputManager.isKeyClick(KeyCode.ESCAPE)) {
			GameState.setPause(!GameState.isPause());
			Logger.log("Game " + (GameState.isPause() ? "Pause" : "Resume"));
		}
		if (!GameState.isPause()) {

			// W
			if (InputManager.isKeyPress(KeyCode.W)) {
				if (GameState.getGameMap().isWalkable(this, 0, -getSpeed())) {
					pos.Y -= getSpeed();
				} else {
					pos.Y = ((int) (pos.Y / Config.TILE_H)) * Config.TILE_H;
				}
			}
			// A
			if (InputManager.isKeyPress(KeyCode.A)) {
				isTurnLeft = true;
				if (GameState.getGameMap().isWalkable(this, -getSpeed(), 0)) {
					pos.X -= getSpeed();
				} else {
					pos.X = ((int) (pos.X / Config.TILE_W)) * Config.TILE_W;
				}
			}
			// S
			if (InputManager.isKeyPress(KeyCode.S)) {
				if (GameState.getGameMap().isWalkable(this, 0, +getSpeed())) {
					pos.Y += getSpeed();
				} else {
					pos.Y = (((int) ((pos.Y + height + getSpeed()) / Config.TILE_H)) * Config.TILE_H) - height;
				}
			}
			// D
			if (InputManager.isKeyPress(KeyCode.D)) {
				isTurnLeft = false;
				if (GameState.getGameMap().isWalkable(this, +getSpeed(), 0)) {
					pos.X += getSpeed();
				} else {
					pos.X = (((int) ((pos.X + width + getSpeed()) / Config.TILE_W)) * Config.TILE_W) - width;
				}
			}
			// Mouse Click
			if (InputManager.isLeftMousePress()) {
				double degree = Math.toDegrees(Math.atan2((Config.SCREEN_H / 2) - InputManager.getMousePos().Y,
						InputManager.getMousePos().X - (Config.SCREEN_W / 2)));
				weapon.attack(getCenterPos(), degree);
			}
			isTurnLeft = (InputManager.getMousePos().X < Config.SCREEN_W / 2);
		}
	}

	public void update() {
		calculateDistanceFromCharacter();
		if (GameState.getSceneResource() == SceneResource.PLAYING) {
			ObjectManager.collideWithBullet(this);
		}
	}

	public boolean isRemoveFromUpdate() {
		return false;
	}

}
