package tower;

import java.util.ArrayList;

import config.Config;
import item.Buyable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.util.Pair;
import logic.GameState;
import object.GameObject;
import object.ObjectManager;
import render.RenderManager;
import sound.SoundManager;
import update.Updatable;
import update.UpdateManager;
import utility.ResourceManager;
import utility.Utility;
import weapon.Weapon;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;
import utility.ResourceManager.SceneResource;
import utility.ResourceManager.SoundResource;

/**
 * Subclass Of GameObject: Game Tower Base Class
 */
public abstract class Tower extends GameObject implements Updatable, Buyable {

	/**
	 * Tower Name
	 */
	private String name;

	/**
	 * Tower Max Health
	 */
	private int maxHealth;

	/**
	 * Tower Health
	 */
	private int health;

	/**
	 * Tower Defense
	 */
	private double defense;

	/**
	 * Tower Weapon
	 */
	private Weapon weapon;

	/**
	 * Is Tower Destroyed
	 */
	protected boolean isDestroyed = false;

	/**
	 * Is Tower Deployed
	 */
	private boolean isDeployed = false;

	/**
	 * Distance From Tower To Map Tile
	 */
	private int[][] distanceFromTower;

	/**
	 * Last Tower Attack Degree
	 */
	private double lastAttackDegree = 0;

	/**
	 * Row Tower Deployed
	 */
	private int towerRow;

	/**
	 * Column Tower Deployed
	 */
	private int towerCol;

	/**
	 * Tower Head Part ImageResource
	 */
	private ImageResource towerHeadImageResource;

	/**
	 * ArrayList Contain Received Tower ItemResource And Received Amount On Buy
	 */
	protected ArrayList<Pair<ItemResource, Integer>> itemOnBuy = new ArrayList<Pair<ItemResource, Integer>>();

	/**
	 * Tower Main Constructor
	 */
	public Tower(ImageResource imageResource, ImageResource towerHeadImageResource, String name, int maxHealth,
			int defense, Weapon weapon, int team, int row, int col) {
		super(imageResource, Config.TILE_W, Config.TILE_H, (int) (Config.TILE_W * (col + 0.5)),
				(int) (Config.TILE_H * (row + 0.5)));
		this.towerHeadImageResource = towerHeadImageResource;
		this.setName(name);
		this.setMaxHealth(maxHealth);
		this.setHealth(maxHealth);
		this.setDefense(defense);
		this.setWeapon(weapon);
		this.towerRow = row;
		this.towerCol = col;
		this.team = team;
		distanceFromTower = new int[GameState.getMapHeight()][GameState.getMapWidth()];
		calculateDistanceFromTower();
	}

	/**
	 * Update distanceFromTower Array
	 */
	private void calculateDistanceFromTower() {
		Utility.calculateDistanceFromGameObject(this, distanceFromTower);
	}

	/**
	 * Tower Render Function
	 */
	public void render() {
		GraphicsContext gc = GameState.getGameMap().getGraphicsContext2D();

		gc.drawImage(ResourceManager.getImage(imageResource), -GameState.getGameMap().getMapPos().X + pos.X,
				-GameState.getGameMap().getMapPos().Y + pos.Y, width, height);

		if (towerHeadImageResource != null) {
			gc.save();
			Rotate rotate = new Rotate(-this.lastAttackDegree,
					-GameState.getGameMap().getMapPos().X + pos.X + (width / 2),
					-GameState.getGameMap().getMapPos().Y + pos.Y + (height / 2));
			gc.transform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(),
					rotate.getTy());
			gc.drawImage(ResourceManager.getImage(towerHeadImageResource),
					-GameState.getGameMap().getMapPos().X + pos.X, -GameState.getGameMap().getMapPos().Y + pos.Y, width,
					height);
			gc.restore();
		}

		int hpBarWidth = width + (2 * (Config.SCREEN_W / 35));
		int hpBarHeight = Config.SCREEN_H / 30;

		gc.setFill(Color.rgb(160, 160, 160));
		gc.fillRect(-GameState.getGameMap().getMapPos().X + pos.X - ((hpBarWidth - width) / 2),
				-GameState.getGameMap().getMapPos().Y + pos.Y - hpBarHeight, hpBarWidth, hpBarHeight);
		gc.setFill(Color.RED);
		gc.fillRect(-GameState.getGameMap().getMapPos().X + pos.X - ((hpBarWidth - width) / 2),
				-GameState.getGameMap().getMapPos().Y + pos.Y - hpBarHeight,
				hpBarWidth * ((double) getHealth() / getMaxHealth()), hpBarHeight);
		gc.setStroke(Color.BLACK);
		gc.strokeRect(-GameState.getGameMap().getMapPos().X + pos.X - ((hpBarWidth - width) / 2),
				-GameState.getGameMap().getMapPos().Y + pos.Y - hpBarHeight, hpBarWidth, hpBarHeight);

		if (!name.isBlank()) {
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setFill(Color.rgb(64, 64, 64));
			gc.fillText(name, -GameState.getGameMap().getMapPos().X + pos.X + (width / 2),
					-GameState.getGameMap().getMapPos().Y + pos.Y);
		}
	}

	/**
	 * Is Tower Allow Render
	 */
	public boolean isAllowRender() {
		return GameState.getSceneResource() == SceneResource.PLAYING;
	}

	/**
	 * Is Tower Destroyed
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * Play Tower Destroyed Sound
	 */
	public void onDestroyed() {
		SoundManager.playSoundEffect(SoundResource.TOWER_DESTROYED,0.4);
	}

	/**
	 * Is Tower Should Remove From Update
	 */
	public boolean isRemoveFromUpdate() {
		return isDestroyed;
	}

	/**
	 * Update Tower
	 */
	public void update() {
		if (GameState.getSceneResource() == SceneResource.PLAYING) {
			if (!GameState.isPause()) {
				calculateDistanceFromTower();
				ObjectManager.collideWithBullet(this);

				if (weapon != null) {
					GameObject targetObject = ObjectManager.findNearestOpponentCharacter(this);
					if (targetObject != null && !targetObject.isDestroyed()) {
						double degree = Math.toDegrees(Math.atan2(
								(this.getCenterPos().Y) - (targetObject.getCenterPos().Y)
										+ (Math.random() * Config.TOWER_DISPERSION * (Math.random() <= 0.5 ? 1 : -1)),
								(targetObject.getCenterPos().X) - (this.getCenterPos().X)
										+ (Math.random() * Config.TOWER_DISPERSION * (Math.random() <= 0.5 ? 1 : -1))));
						if (weapon.attack(getCenterPos(), degree)) {
							this.lastAttackDegree = degree;
						}
					}
				}
			}
		}
	}

	/**
	 * Get Tower ZIndex
	 */
	public int getZ() {
		return Config.ZINDEX_TOWER;
	}

	/**
	 * Get Tower Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set Tower Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get Tower Health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Set Tower Health
	 */
	public void setHealth(int health) {
		if (health > 0) {
			this.health = Math.min(maxHealth, health);
		} else {
			this.health = 0;
			this.onDestroyed();
			this.isDestroyed = true;
		}
	}

	/**
	 * Get Tower Max Health
	 * 
	 * @return
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * Set Tower Man Health
	 */
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = Math.max(0, maxHealth);
		this.health = Math.min(this.maxHealth, this.health);
	}

	/**
	 * Get Tower Defense
	 */
	public double getDefense() {
		return defense;
	}

	/**
	 * Set Tower Defense
	 */
	public void setDefense(double defense) {
		this.defense = defense;
	}

	/**
	 * Get Tower Weapon
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * Set Tower Weapon
	 */
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	/**
	 * Deal Damage To This Tower
	 */
	public void dealDamage(int damage) {
		setHealth(getHealth() - damage);
	}

	/**
	 * Get Manhattan Distance Between Tower And Tile At Row, Column
	 * 
	 * @param row Tile Row
	 * @param col Tile Column
	 * @return Manhattan Distance Between Character And Tile
	 */
	public int getManhattanDistanceFromTower(int row, int col) {
		if (row >= 0 && col >= 0 && row < GameState.getMapHeight() && col < GameState.getMapWidth()) {
			return distanceFromTower[row][col];
		}
		return -1;
	}

	/**
	 * Get Tower Deployed Row
	 */
	public int getTowerRow() {
		return towerRow;
	}

	/**
	 * Get Tower Deployed Column
	 */
	public int getTowerCol() {
		return towerCol;
	}

	/**
	 * Deploy Tower - Register Tower To Update, Render And Object Manager
	 */
	public void deploy() {
		if (!isDeployed) {
			isDeployed = true;
			UpdateManager.add(this);
			RenderManager.add(this);
			ObjectManager.addTower(this);
		}
	}

	/**
	 * Get Item Received On Buy
	 * 
	 * @return ArrayList Contain Received Tower ItemResource And Received Amount On
	 *         Buy
	 */
	public ArrayList<Pair<ItemResource, Integer>> itemOnBuy() {
		return itemOnBuy;
	}

}
