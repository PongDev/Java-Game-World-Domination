package character;

import javafx.scene.canvas.GraphicsContext;
import logic.GameState;
import object.GameObject;
import render.Renderable;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;

public abstract class Character extends GameObject {

	private String name;
	private int health;
	private int maxHealth;
	private int attackDamage;
	private int attackSpeed;
	private double defense;
	private int speed;

	public Character(ImageResource imageResource, int width, int height, int centerPosX, int centerPosY) {
		super(imageResource, width, height, centerPosX, centerPosY);
	}

	public void render() {
		GraphicsContext gc = GameState.getGameMap().getGraphicsContext2D();

		gc.drawImage(ResourceManager.getImage(imageResource), -GameState.getGameMap().getMapPosX() + pos.X,
				-GameState.getGameMap().getMapPosY() + pos.Y, width, height);
	}

	public boolean isAllowRender() {
		return true;
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

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
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

}
