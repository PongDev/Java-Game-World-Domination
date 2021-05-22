package character;

import config.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import logic.GameState;
import object.GameObject;
import update.Updatable;
import utility.Position;
import utility.ResourceManager;
import utility.Utility;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import weapon.Weapon;

/**
 * Subclass Of GameObject: Game Character Base Class
 */
public abstract class Character extends GameObject implements Updatable {

	/**
	 * Character Name
	 */
	private String name;

	/**
	 * Character Max Health
	 */
	private int maxHealth;

	/**
	 * Character Current Health
	 */
	private int health;

	/**
	 * Character Defense
	 */
	private double defense;

	/**
	 * Character Move Speed
	 */
	private int speed;

	/**
	 * Character Weapon Turing Degree
	 */
	protected double weaponTurningDegree;

	/**
	 * Character Current Weapon
	 */
	protected Weapon weapon;

	/**
	 * Is Character Turn Left
	 */
	protected boolean isTurnLeft = false;

	/**
	 * Is Character Destroyed
	 */
	protected boolean isDestroyed = false;

	/**
	 * Distance From Character To Map Tile
	 */
	protected int[][] distanceFromCharacter;

	/**
	 * Character Alternative Constructor
	 */
	public Character(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense,
			int speed, Weapon weapon, int team, int centerPosX, int centerPosY) {
		this(imageResource, width, height, name, maxHealth, defense, speed, weapon, team,
				new Position(centerPosX, centerPosY));
	}

	/**
	 * Character Main Constructor
	 */
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

		distanceFromCharacter = new int[GameState.getMapHeight()][GameState.getMapWidth()];
		calculateDistanceFromCharacter();
	}

	/**
	 * Update distanceFromCharacter Array
	 */
	protected void calculateDistanceFromCharacter() {
		Utility.calculateDistanceFromGameObject(this, distanceFromCharacter);
	}

	/**
	 * Get Manhattan Distance Between Character And Tile At Row, Column
	 * 
	 * @param row Tile Row
	 * @param col Tile Column
	 * @return Manhattan Distance Between Character And Tile
	 */
	public int getManhattanDistanceFromCharacter(int row, int col) {
		if (row >= 0 && col >= 0 && row < GameState.getMapHeight() && col < GameState.getMapWidth()) {
			return distanceFromCharacter[row][col];
		}
		return -1;
	}

	/**
	 * Character Render Function
	 */
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
			gc.save();
			Rotate rotate = new Rotate(-this.weaponTurningDegree * (isTurnLeft ? -1 : 1),
					-GameState.getGameMap().getMapPos().X + pos.X + (width / 2),
					-GameState.getGameMap().getMapPos().Y + pos.Y + (height / 2));
			gc.transform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(),
					rotate.getTy());
			gc.drawImage(ResourceManager.getImage(weapon.getImageResourse()),
					-GameState.getGameMap().getMapPos().X + pos.X + (isTurnLeft ? width : 0),
					-GameState.getGameMap().getMapPos().Y + pos.Y, width * (isTurnLeft ? -1 : 1), height);
			gc.restore();
		}
		if (!name.isBlank()) {
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setFill(Color.rgb(64, 64, 64));
			gc.fillText(name, -GameState.getGameMap().getMapPos().X + pos.X + (width / 2),
					-GameState.getGameMap().getMapPos().Y + pos.Y);
		}
	}

	/**
	 * Is Character Allow Render
	 */
	public boolean isAllowRender() {
		return GameState.getSceneResource() == SceneResource.PLAYING;
	}

	/**
	 * Abstract Function Return ZIndex Of Character
	 */
	public abstract int getZ();

	/**
	 * Get Character Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set Character Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get Character Health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Set Character Health
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
	 * Get Character Max Health
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * Set Character Max Health
	 */
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = Math.max(0, maxHealth);
		this.health = Math.min(this.maxHealth, this.health);
	}

	/**
	 * Get Character Defense
	 */
	public double getDefense() {
		return defense;
	}

	/**
	 * Set Character Defense
	 */
	public void setDefense(double defense) {
		this.defense = defense;
	}

	/**
	 * Get Character Speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Set Character Speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Get Character Weapon
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * Set Character Weapon
	 */
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	/**
	 * Deal Damage To This Character
	 */
	public void dealDamage(int damage) {
		setHealth(getHealth() - damage);
	}

}
