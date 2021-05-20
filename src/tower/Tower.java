package tower;

import config.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import logic.GameState;
import object.GameObject;
import object.ObjectManager;
import render.RenderManager;
import update.Updatable;
import update.UpdateManager;
import utility.ResourceManager;
import utility.Utility;
import weapon.Weapon;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;

public abstract class Tower extends GameObject implements Updatable {

	private String name;
	private int maxHealth;
	private int health;
	private double defense;
	private Weapon weapon;
	protected boolean isDestroyed = false;
	private int[][] distanceFromTower;
	private GameObject targetObject = null;
	private double lastAttackDegree = 0;
	private ImageResource towerHeadImageResource;

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
		this.team = team;
		distanceFromTower = new int[GameState.getMapHeight()][GameState.getMapWidth()];
		UpdateManager.add(this);
		RenderManager.add(this);
		ObjectManager.addTower(this);
	}

	private void calculateDistanceFromTower() {
		Utility.calculateDistanceFromGameObject(this, distanceFromTower);
	}

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

	public boolean isAllowRender() {
		return GameState.getSceneResource() == SceneResource.PLAYING;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void onDestroyed() {
		return;
	}

	public boolean isRemoveFromUpdate() {
		return isDestroyed;
	}

	public void update() {
		if (GameState.getSceneResource() == SceneResource.PLAYING) {
			if (!GameState.isPause()) {
				calculateDistanceFromTower();
				ObjectManager.collideWithBullet(this);

				if (targetObject != null && !targetObject.isDestroyed()) {
					double degree = Math.toDegrees(Math.atan2(
							(this.getCenterPos().Y) - (targetObject.getCenterPos().Y)
									+ (Math.random() * Config.TOWER_DISPERSION * (Math.random() <= 0.5 ? 1 : -1)),
							(targetObject.getCenterPos().X) - (this.getCenterPos().X)
									+ (Math.random() * Config.TOWER_DISPERSION * (Math.random() <= 0.5 ? 1 : -1))));
					weapon.attack(getCenterPos(), degree);
					this.lastAttackDegree = degree;
				}
			}
		}
	}

	public int getZ() {
		return Config.ZINDEX_TOWER;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health > 0) {
			this.health = Math.min(maxHealth, health);
		} else {
			this.health = 0;
			this.isDestroyed = true;
		}
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = Math.max(0, maxHealth);
		this.health = Math.min(this.maxHealth, this.health);
	}

	public double getDefense() {
		return defense;
	}

	public void setDefense(double defense) {
		this.defense = defense;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public void dealDamage(int damage) {
		setHealth(getHealth() - damage);
	}

	public int getManhattanDistanceFromTower(int row, int col) {
		if (row >= 0 && col >= 0 && row < GameState.getMapHeight() && col < GameState.getMapWidth()) {
			return distanceFromTower[row][col];
		}
		return -1;
	}

	public void reportDetected(GameObject obj) {
		if (obj.getTeam() != this.getTeam()) {
			if (targetObject == null || targetObject.isDestroyed()) {
				targetObject = obj;
			} else if (Utility.euclideanDistance(this, obj) < Utility.euclideanDistance(this, targetObject)) {
				targetObject = obj;
			}
		}
	}

}
