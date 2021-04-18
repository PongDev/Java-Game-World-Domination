package character;

import javafx.scene.canvas.GraphicsContext;
import logic.GameState;
import object.GameObject;
import utility.Position;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import weapon.Weapon;

public abstract class Character extends GameObject {

	private String name;
	private int health;
	private int maxHealth;
	private double defense;
	private int speed;
	private Weapon weapon;
	protected boolean isTurnLeft = false;

	public Character(ImageResource imageResource, int width, int height, int centerPosX, int centerPosY) {
		this(imageResource, width, height, new Position(centerPosX, centerPosY));
	}

	public Character(ImageResource imageResource, int width, int height, Position centerPos) {
		super(imageResource, width, height, centerPos);
	}

	public void render() {
		GraphicsContext gc = GameState.getGameMap().getGraphicsContext2D();

		gc.drawImage(ResourceManager.getImage(imageResource),
				-GameState.getGameMap().getMapPos().X + pos.X + (isTurnLeft ? width : 0),
				-GameState.getGameMap().getMapPos().Y + pos.Y, width * (isTurnLeft ? -1 : 1), height);
		if (weapon != null) {
			gc.drawImage(ResourceManager.getImage(weapon.getImageResourse()),
					-GameState.getGameMap().getMapPos().X + pos.X + (isTurnLeft ? width : 0),
					-GameState.getGameMap().getMapPos().Y + pos.Y, width * (isTurnLeft ? -1 : 1), height);
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
		this.health = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
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

}
