package weapon;

import config.Config;
import javafx.scene.canvas.GraphicsContext;
import logic.GameState;
import object.GameObject;
import update.Updatable;
import utility.Position;
import utility.ResourceManager;
import utility.ResourceManager.SceneResource;

/**
 * Subclass Of GameObject: Bullet Class
 */
public class Bullet extends GameObject implements Updatable {

	/**
	 * Is Bullet Destroyed
	 */
	private boolean isDestroyed = false;
	/**
	 * Bullet Properties
	 */
	private BulletProperties bulletProperties;
	/**
	 * Degree That Bullet Get Shot
	 */
	private double degree = 0;

	/**
	 * Bullet Alternative Constructor
	 * @param bulletProperties
	 * @param centerPosX Bullet Center X Position
	 * @param centerPosY Bullet Center Y Position
	 * @param degree
	 */
	public Bullet(BulletProperties bulletProperties, int centerPosX, int centerPosY, double degree) {
		this(bulletProperties, new Position(centerPosX, centerPosY), degree);
	}
	/**
	 * Bullet Main Constructor
	 * @param bulletProperties 
	 * @param centerPos Bullet Center Position
	 * @param degree
	 */
	public Bullet(BulletProperties bulletProperties, Position centerPos, double degree) {
		super(bulletProperties.getImageResource(), bulletProperties.getWidth(), bulletProperties.getHeight(),
				centerPos);
		this.bulletProperties = bulletProperties;
		this.degree = degree;

		Position delta = new Position((Config.TILE_W / 3) * Math.cos(Math.toRadians(degree)),
				-((Config.TILE_H / 3) * Math.sin(Math.toRadians(degree))));
		if (GameState.getGameMap().isPenetrable(this, delta.X, delta.Y)) {
			pos.X += delta.X;
			pos.Y += delta.Y;
		} else {
			isDestroyed = true;
		}
	}

	/**
	 * Bullet Render Function
	 */
	public void render() {
		GraphicsContext gc = GameState.getGameMap().getGraphicsContext2D();

		gc.drawImage(ResourceManager.getImage(imageResource), -GameState.getGameMap().getMapPos().X + pos.X,
				-GameState.getGameMap().getMapPos().Y + pos.Y, width, height);
	}
	/**
	 * Is Bullet Allow Render
	 */
	public boolean isAllowRender() {
		return GameState.getSceneResource() == SceneResource.PLAYING;
	}

	/**
	 * Getter Of Z Index
	 */
	public int getZ() {
		return bulletProperties.getzIndex();
	}
	/**
	 * Getter Of IsDestroyed
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}
	/**
	 * Bullet Moving Function
	 */
	public void update() {
		if (!GameState.isPause()) {
			Position delta = new Position(bulletProperties.getSpeed() * Math.cos(Math.toRadians(degree)),
					-(bulletProperties.getSpeed() * Math.sin(Math.toRadians(degree))));
			if (GameState.getGameMap().isPenetrable(this, delta.X, delta.Y)
					&& !GameState.getGameMap().isCollideTeamTower(this, delta.X, delta.Y)) {
				pos.X += delta.X;
				pos.Y += delta.Y;
			} else {
				isDestroyed = true;
			}
		}
	}

	public boolean isRemoveFromUpdate() {
		return isDestroyed;
	}

	public int getTeam() {
		return bulletProperties.getTeam();
	}

	public int getDamage() {
		return bulletProperties.getDamage();
	}

	public void destroy() {
		isDestroyed = true;
	}

	public GameObject getOwner() {
		return bulletProperties.getOwner();
	}

	public void dealDamage(int damage) {
		// Bullet Is Immutable
		return;
	}

	public void onDestroyed() {
		// Bullet Do Nothing On Destroyed
		return;
	}

}
