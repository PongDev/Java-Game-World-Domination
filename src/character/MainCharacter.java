package character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import config.Config;
import exception.DeployTowerFailedException;
import gui.GameMap;
import gui.Shop;
import input.InputManager;
import input.Inputable;
import item.Potion;
import javafx.scene.input.KeyCode;
import javafx.util.Pair;
import logic.GameState;
import object.ObjectManager;
import render.RenderManager;
import sound.SoundManager;
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
import utility.ResourceManager.SoundResource;
import utility.ResourceManager.UIResource;
import utility.Utility;
import utility.WaveManager;
import weapon.Gun;
import weapon.Weapon;

/**
 * Subclass Of Character: Player Main Character
 */
public class MainCharacter extends Character implements Inputable {

	/**
	 * Comparator For Sort Moving Vector
	 */
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

	/**
	 * Current Selected Tile
	 */
	private Position selectedTile;

	/**
	 * Current Selected Tower
	 */
	private ItemResource selectedTower;

	/**
	 * Current Money
	 */
	private int money = 0;

	/**
	 * Main Character Inventory Storage
	 */
	private Map<ItemResource, Integer> inventory = new HashMap<ItemResource, Integer>();

	/**
	 * Main Character Available Weapon List
	 */
	private ArrayList<Weapon> weaponList = new ArrayList<Weapon>();

	/**
	 * Current Weapon Index Of weaponList
	 */
	private int weaponIndex = 0;

	/**
	 * Main Character Alternative Constructor
	 */
	public MainCharacter(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense,
			int speed, Weapon weapon, int team, int centerPosX, int centerPosY) {
		this(imageResource, width, height, name, maxHealth, defense, speed, weapon, team,
				new Position(centerPosX, centerPosY));
	}

	/**
	 * Main Character Main Constructor
	 */
	public MainCharacter(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense,
			int speed, Weapon weapon, int team, Position centerPos) {
		super(imageResource, width, height, name, maxHealth, defense, speed, weapon, team, centerPos);
		weaponList.add(this.getWeapon());
		InputManager.addInputableObject(this);
		UpdateManager.add(this);
		RenderManager.add(this);
		ObjectManager.addCharacter(this);
	}

	/**
	 * Get Moving Vector List Toward Main Character
	 * 
	 * @param pos Start Position To Find Moving Vector List Toward Main Character
	 * @return Moving Vector List Toward Main Character
	 */
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

	/**
	 * Main Character ZIndex
	 */
	public int getZ() {
		return Config.ZINDEX_MAIN_CHARACTER;
	}

	/**
	 * Process Player Input
	 */
	public void processInput() {
		if (GameState.getSceneResource() == SceneResource.PLAYING) {
			// ESC
			if (InputManager.isKeyClick(KeyCode.ESCAPE)) {
				if (((Shop) ResourceManager.getUI(UIResource.SHOP)).isVisible()) {
					((Shop) ResourceManager.getUI(UIResource.SHOP)).toggleVisible();
				} else {
					GameState.setPause(!GameState.isPause());
					Logger.log("Game " + (GameState.isPause() ? "Pause" : "Resume"));
				}
			}
			if (!GameState.isPause()) {
				// W
				if (InputManager.isKeyPress(KeyCode.W)) {
					if (GameState.getGameMap().isWalkableAndNotCollideTower(this, 0, -getSpeed())) {
						pos.Y -= getSpeed();
					} else {
						pos.Y = ((int) (pos.Y / Config.TILE_H)) * Config.TILE_H;
					}
				}
				// A
				if (InputManager.isKeyPress(KeyCode.A)) {
					if (GameState.getGameMap().isWalkableAndNotCollideTower(this, -getSpeed(), 0)) {
						pos.X -= getSpeed();
					} else {
						pos.X = ((int) (pos.X / Config.TILE_W)) * Config.TILE_W;
					}
				}
				// S
				if (InputManager.isKeyPress(KeyCode.S)) {
					if (GameState.getGameMap().isWalkableAndNotCollideTower(this, 0, +getSpeed())) {
						pos.Y += getSpeed();
					} else {
						pos.Y = (((int) ((pos.Y + height + getSpeed()) / Config.TILE_H)) * Config.TILE_H) - height;
					}
				}
				// D
				if (InputManager.isKeyPress(KeyCode.D)) {
					if (GameState.getGameMap().isWalkableAndNotCollideTower(this, +getSpeed(), 0)) {
						pos.X += getSpeed();
					} else {
						pos.X = (((int) ((pos.X + width + getSpeed()) / Config.TILE_W)) * Config.TILE_W) - width;
					}
				}
				// E
				if (InputManager.isKeyClick(KeyCode.E)) {
					weaponIndex = (weaponIndex + 1 + weaponList.size()) % weaponList.size();
					this.setWeapon(weaponList.get(weaponIndex));
					this.weapon.setLastAttack((new Date()).getTime());
				}
				// Q
				if (InputManager.isKeyClick(KeyCode.Q)) {
					weaponIndex = (weaponIndex - 1 + weaponList.size()) % weaponList.size();
					this.setWeapon(weaponList.get(weaponIndex));
					this.weapon.setLastAttack((new Date()).getTime());
				}
				// Space Bar
				if (InputManager.isKeyClick(KeyCode.SPACE)) {
					if (WaveManager.isWaveEnd()) {
						this.money += WaveManager.forceStartNewWave() * Config.DELAY_BETWEEN_WAVE_TO_MONEY;
					}
				}
				// Calculate Degree And Weapon Degree
				double degree = Math.toDegrees(Math.atan2((Config.SCREEN_H / 2) - InputManager.getMousePos().Y,
						InputManager.getMousePos().X - (Config.SCREEN_W / 2)));
				isTurnLeft = (degree > 90 || degree < -90) ? true : false;
				if (isTurnLeft) {
					this.weaponTurningDegree = (180 * (degree > 0 ? 1 : -1)) - degree;
				} else {
					this.weaponTurningDegree = degree;
				}
				// Mouse Press
				if (InputManager.isLeftMousePress() && selectedTower == null) {
					if (weapon instanceof Gun && ((Gun) weapon).getBulletUse() != null) {
						if (this.countItemInInventory(((Gun) weapon).getBulletUse()) > 0) {
							if (weapon.attack(getCenterPos(), degree)) {
								this.removeItemFromInventory(((Gun) weapon).getBulletUse());
							}
						}
					} else {
						weapon.attack(getCenterPos(), degree);
					}
				}
				// Open Shop
				if (InputManager.isLeftMouseClick()) {
					Position mapPos = GameState.getGameMap().getMapPos();
					Position mousePos = InputManager.getMousePos();
					Position selectedTile = new Position((int) ((mousePos.Y + mapPos.Y) / Config.TILE_H),
							(int) ((mousePos.X + mapPos.X) / Config.TILE_W));
					double distantFromShop = Utility.euclideanDistance(this.getCenterPos(),
							new Position((GameMap.getShopPos().X * Config.TILE_W) + Config.TILE_W / 2,
									(GameMap.getShopPos().Y * Config.TILE_H) + Config.TILE_H / 2));
					if (selectedTile.Y == GameMap.getShopPos().X && selectedTile.X == GameMap.getShopPos().Y
							&& distantFromShop <= Config.MAIN_CHARACTER_OPEN_SHOP_RANGE) {
						((Shop) ResourceManager.getUI(UIResource.SHOP)).toggleVisible();
					}
				}
				// Deploy Tower
				if (InputManager.isLeftMouseClick() && selectedTower != null) {
					if (selectedTile != null
							&& GameState.getGameMap().isPlacable((int) selectedTile.X, (int) selectedTile.Y)
							&& this.countItemInInventory(selectedTower) > 0) {
						try {
							this.deployTower(selectedTower, (int) selectedTile.X, (int) selectedTile.Y);
							this.removeItemFromInventory(selectedTower);
							SoundManager.playSoundEffect(SoundResource.DEPLOY_TOWER, 0.5);

						} catch (DeployTowerFailedException e) {
							Logger.log("Detected Exception On Deploy Tower");
						}
					}
					selectedTower = null;
				}
				// Heal
				if (InputManager.isKeyClick(KeyCode.H)) {
					if (this.getHealth() < this.getMaxHealth()
							&& this.countItemInInventory(ItemResource.HEALTH_POTION) > 0) {
						SoundManager.playSoundEffect(SoundResource.HEALTH_POTON, 0.5);
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

					if (GameState.getGameMap().isPlacable((int) selectedTile.X, (int) selectedTile.Y)
							&& !GameState.getGameMap().isCollideTower(selectedTile.Y * Config.TILE_W,
									selectedTile.X * Config.TILE_H)
							&& Utility.euclideanDistance(this.getCenterPos(), new Position(
									(selectedTile.Y + 0.5) * Config.TILE_W,
									(selectedTile.X + 0.5) * Config.TILE_H)) < Config.MAIN_CHARACTER_DEPLOY_RANGE) {
						GameState.getGameMap().setHighLightTile((int) selectedTile.X, (int) selectedTile.Y);
						if (!selectedTile.equals(this.selectedTile)) {
							Logger.log(String.format("Selected Tile Row:%2d Col:%2d", (int) selectedTile.X,
									(int) selectedTile.Y));
							this.selectedTile = selectedTile;
						}
					} else {
						this.selectedTile = null;
					}
				}
				if (WaveManager.isWaveEnd()) {
					// Click 1
					if (InputManager.isKeyClick(KeyCode.DIGIT1)) {
						selectedTower = (selectedTower == ItemResource.BARRIER_TOWER ? null
								: ItemResource.BARRIER_TOWER);
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
				} else {
					selectedTower = null;
				}
				isTurnLeft = (InputManager.getMousePos().X < Config.SCREEN_W / 2);
			}
		}
	}

	/**
	 * Update Main Character
	 */
	public void update() {
		if (GameState.getSceneResource() == SceneResource.PLAYING) {
			if (!GameState.isPause()) {
				calculateDistanceFromCharacter();
				ObjectManager.collideWithBullet(this);
			}
		}
	}

	/**
	 * Is Main Character Destroyed
	 */
	public boolean isDestroyed() {
		// return false;
		return isDestroyed;
	}

	/**
	 * Action On Main Character Destroyed
	 */
	public void onDestroyed() {
		ResourceManager.getSound(SoundResource.PLAYING).stop();
		SoundManager.playSoundEffect(SoundResource.ENDING_BAD, 0.5);
		GameState.setSceneResource(SceneResource.ENDING_BAD);
	}

	/**
	 * Is Main Character Should Remove From Update
	 */
	public boolean isRemoveFromUpdate() {
		return false;
		// return isDestroyed;
	}

	/**
	 * Get Current Main Character Money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Set Current Main Character Money
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * Add Item To Main Character Inventory
	 * 
	 * @param item   ItemResource Of Item To Add
	 * @param amount Amount Of Item To Add
	 */
	public void addItemToInventory(ItemResource item, int amount) {
		if (!inventory.containsKey(item)) {
			inventory.put(item, amount);
		} else {
			inventory.put(item, inventory.get(item) + amount);
		}
	}

	/**
	 * Add 1 Item To Main Character Inventory
	 * 
	 * @param item ItemResource Of Item To Add
	 */
	public void addItemToInventory(ItemResource item) {
		this.addItemToInventory(item, 1);
	}

	/**
	 * Remove Item From Main Character Inventory
	 * 
	 * @param item   ItemResource Of Item To Remove
	 * @param amount Amount Of Item To Remove
	 */
	public void removeItemFromInventory(ItemResource item, int amount) {
		if (inventory.containsKey(item)) {
			inventory.put(item, Math.max(inventory.get(item) - amount, 0));
		}
	}

	/**
	 * Remove 1 Item From Main Character Inventory
	 * 
	 * @param item ItemResource Of Item To Remove
	 */
	public void removeItemFromInventory(ItemResource item) {
		this.removeItemFromInventory(item, 1);
	}

	/**
	 * Count Item In Main Character Inventory
	 * 
	 * @param item ItemResource Of Item To Count
	 */
	public int countItemInInventory(ItemResource item) {
		return inventory.containsKey(item) ? inventory.get(item) : 0;
	}

	/**
	 * Get Current Selected Tower ItemResource
	 */
	public ItemResource getSelectedTower() {
		return selectedTower;
	}

	/**
	 * Deploy Tower To Selected Row, Column
	 * 
	 * @param tower ItemResource Of Tower To Deploy
	 * @param row   Row To Deploy
	 * @param col   Column To Deploy
	 */
	public void deployTower(ItemResource tower, int row, int col) throws DeployTowerFailedException {
		switch (tower) {
		case BARRIER_TOWER:
			GameState.getGameMap().deployTower(new BarricadeTower(row, col, team));
			break;
		case MACHINE_GUN_TOWER:
			GameState.getGameMap().deployTower(new MachineGunTower(row, col, team));
			break;
		case SNIPER_TOWER:
			GameState.getGameMap().deployTower(new SniperTower(row, col, team));
			break;
		default:
			break;
		}
	}

	/**
	 * Add Weapon To Main Character And Set Current Weapon To Added Weapon
	 * 
	 * @param weapon Weapon To Add
	 */
	public void addWeapon(Weapon weapon) {
		weaponList.add(weapon);
		weaponIndex = weaponList.size() - 1;
		this.setWeapon(weaponList.get(weaponIndex));
		this.weapon.setLastAttack((new Date()).getTime());
	}

}
