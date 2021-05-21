package character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import config.Config;
import gui.Shop;
import input.InputManager;
import input.Inputable;
import item.Potion;
import javafx.scene.input.KeyCode;
import javafx.util.Pair;
import logic.GameState;
import object.ObjectManager;
import render.RenderManager;
import tower.BarricadeTower;
import tower.MachineGunTower;
import tower.SniperTower;
import update.UpdateManager;
import utility.Logger;
import utility.Position;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;
import utility.ResourceManager.SceneResource;
import utility.ResourceManager.UIResource;
import utility.Utility;
import utility.WaveManager;
import weapon.Weapon;

public class MainCharacter extends Character implements Inputable {

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

	private Position selectedTile;
	private ItemResource selectedTower;
	private int money = 0;
	private Map<ItemResource, Integer> inventory = new HashMap<ItemResource, Integer>();

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
		ObjectManager.addCharacter(this);
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
			if (((Shop) ResourceManager.getUI(UIResource.SHOP)).isVisible()) {
				((Shop) ResourceManager.getUI(UIResource.SHOP)).toggleVisible();
			} else {
				GameState.setPause(!GameState.isPause());
				Logger.log("Game " + (GameState.isPause() ? "Pause" : "Resume"));
			}
		}
		// B
		if (InputManager.isKeyClick(KeyCode.B)) {
			((Shop) ResourceManager.getUI(UIResource.SHOP)).toggleVisible();
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
			// Space Bar
			if (InputManager.isKeyClick(KeyCode.SPACE)) {
				if (WaveManager.isWaveEnd()) {
					this.money += WaveManager.forceStartNewWave() * Config.DELAY_BETWEEN_WAVE_TO_MONEY;
				}
			}
			// Mouse Press
			if (InputManager.isLeftMousePress() && selectedTower == null) {
				double degree = Math.toDegrees(Math.atan2((Config.SCREEN_H / 2) - InputManager.getMousePos().Y,
						InputManager.getMousePos().X - (Config.SCREEN_W / 2)));
				weapon.attack(getCenterPos(), degree);
			}
			// Deploy Tower
			if (InputManager.isLeftMouseClick() && selectedTower != null) {
				if (this.countItemInInventory(selectedTower) > 0) {
					this.removeItemFromInventory(selectedTower);
					this.deployTower(selectedTower, (int) selectedTile.X, (int) selectedTile.Y);
					selectedTower = null;
				}
			}
			// Heal
			if (InputManager.isKeyClick(KeyCode.H)) {
				if (this.getHealth() < this.getMaxHealth()
						&& this.countItemInInventory(ItemResource.HEALTH_POTION) > 0) {
					this.removeItemFromInventory(ItemResource.HEALTH_POTION);
					((Potion) ResourceManager.getItem(ItemResource.HEALTH_POTION)).use(this);
				}
			}
			// Mouse Select Tile
			if (GameState.getSceneResource() == SceneResource.PLAYING && selectedTower != null) {
				Position mapPos = GameState.getGameMap().getMapPos();
				Position mousePos = InputManager.getMousePos();
				Position selectedTile = new Position((int) ((mousePos.Y + mapPos.Y) / Config.TILE_H),
						(int) ((mousePos.X + mapPos.X) / Config.TILE_W));

				GameState.getGameMap().setHighLightTile((int) selectedTile.X, (int) selectedTile.Y);
				if (!selectedTile.equals(this.selectedTile)) {
					Logger.log(
							String.format("Selected Tile Row:%2d Col:%2d", (int) selectedTile.X, (int) selectedTile.Y));
					this.selectedTile = selectedTile;
				}
			}
			// Click 1
			if (InputManager.isKeyClick(KeyCode.DIGIT1)) {
				selectedTower = (selectedTower == ItemResource.BARRIER_TOWER ? null : ItemResource.BARRIER_TOWER);
			}
			// Click 2
			if (InputManager.isKeyClick(KeyCode.DIGIT2)) {
				selectedTower = (selectedTower == ItemResource.MACHINE_GUN_TOWER ? null
						: ItemResource.MACHINE_GUN_TOWER);
			}
			// Click 3
			if (InputManager.isKeyClick(KeyCode.DIGIT3)) {
				selectedTower = (selectedTower == ItemResource.SNIPER_TOWER ? null : ItemResource.SNIPER_TOWER);
			}
			isTurnLeft = (InputManager.getMousePos().X < Config.SCREEN_W / 2);
		}
	}

	public void update() {
		if (GameState.getSceneResource() == SceneResource.PLAYING) {
			if (!GameState.isPause()) {
				calculateDistanceFromCharacter();
				ObjectManager.collideWithBullet(this);
			}
		}
	}

	public boolean isDestroyed() {
		return false;
	}

	public void onDestroyed() {

	}

	public boolean isRemoveFromUpdate() {
		return false;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void addItemToInventory(ItemResource item, int amount) {
		if (!inventory.containsKey(item)) {
			inventory.put(item, amount);
		} else {
			inventory.put(item, inventory.get(item) + amount);
		}
	}

	public void addItemToInventory(ItemResource item) {
		this.addItemToInventory(item, 1);
	}

	public void removeItemFromInventory(ItemResource item, int amount) {
		if (inventory.containsKey(item)) {
			inventory.put(item, Math.max(inventory.get(item) - amount, 0));
		}
	}

	public void removeItemFromInventory(ItemResource item) {
		this.removeItemFromInventory(item, 1);
	}

	public int countItemInInventory(ItemResource item) {
		return inventory.containsKey(item) ? inventory.get(item) : 0;
	}

	public ItemResource getSelectedTower() {
		return selectedTower;
	}

	public void deployTower(ItemResource tower, int row, int col) {
		switch (tower) {
		case BARRIER_TOWER:
			(new BarricadeTower(row, col, team)).deploy();
			break;
		case MACHINE_GUN_TOWER:
			(new MachineGunTower(row, col, team)).deploy();
			break;
		case SNIPER_TOWER:
			(new SniperTower(row, col, team)).deploy();
			break;
		default:
			break;
		}
	}

}
