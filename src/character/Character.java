package character;

import config.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import logic.GameState;
import object.GameObject;
import utility.Position;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import weapon.Weapon;

public abstract class Character extends GameObject {

	private String name;
	private int maxHealth;
	private int health;
	private double defense;
	private int speed;
	protected Weapon weapon;
	protected boolean isTurnLeft = false;
	protected boolean isDestroyed = false;

	public Character(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense,
			int speed, Weapon weapon, int team, int centerPosX, int centerPosY) {
		this(imageResource, width, height, name, maxHealth, defense, speed, weapon, team,
				new Position(centerPosX, centerPosY));
	}

	public Character(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense,
			int speed, Weapon weapon, int team, Position centerPos) {
		super(imageResource, width, height, centerPos);
		this.setName(name);
		this.setMaxHealth(maxHealth);
		this.setHealth(maxHealth);
		this.setDefense(defense);
		this.setSpeed(speed);
		this.setWeapon(weapon);
		this.team = team;
	}

	public void render() {
		GraphicsContext gc = GameState.getGameMap().getGraphicsContext2D();

		gc.drawImage(ResourceManager.getImage(imageResource),
				-GameState.getGameMap().getMapPos().X + pos.X + (isTurnLeft ? width : 0),
				-GameState.getGameMap().getMapPos().Y + pos.Y, width * (isTurnLeft ? -1 : 1), height);

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

		if (weapon != null) {
			gc.drawImage(ResourceManager.getImage(weapon.getImageResourse()),
					-GameState.getGameMap().getMapPos().X + pos.X + (isTurnLeft ? width : 0),
					-GameState.getGameMap().getMapPos().Y + pos.Y, width * (isTurnLeft ? -1 : 1), height);
		}
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
		return false;
	}

	public abstract int getZ();

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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
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

}
