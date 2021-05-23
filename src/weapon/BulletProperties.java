package weapon;

import object.GameObject;
import utility.ResourceManager.ImageResource;

/**
 * Properties Of Bullet Class
 */
public class BulletProperties {

	/**
	 * Bullet ImageResource
	 */
	private ImageResource imageResource;
	/**
	 * Bullet
	 */
	private int width;
	/**
	 * Bullet Width
	 */
	private int height;
	/**
	 * Bullet Height
	 */
	private int damage;
	/**
	 * Bullet Damage
	 */
	private double speed;
	/**
	 * Bullet Speed
	 */
	private int team;
	/**
	 * Bullet Team
	 */
	private int zIndex;
	/**
	 * Bullet ZIndex
	 */
	private GameObject owner;
	/**
	 * GameObject That Shoot Bullet
	 */

	/**
	 * BulletProperties Constructor
	 * @param imageResource
	 * @param width
	 * @param height
	 * @param damage
	 * @param speed
	 * @param team
	 * @param zIndex
	 * @param owner
	 */
	public BulletProperties(ImageResource imageResource, int width, int height, int damage, double speed, int team,
			int zIndex, GameObject owner) {
		this.imageResource = imageResource;
		this.width = width;
		this.height = height;
		this.damage = damage;
		this.speed = speed;
		this.team = team;
		this.zIndex = zIndex;
		this.owner = owner;
	}

	/**
	 * Getter Of ImageResource
	 * @return ImageResource
	 */
	public ImageResource getImageResource() {
		return imageResource;
	}

	/**
	 * Getter Of Width
	 * @return Width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Getter Of Height
	 * @return Height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Getter Of Damage
	 * @return Damage
	 */
	public int getDamage() {
		return damage;
	}
	/**
	 * Getter Of Speed
	 * @return Speed
	 */
	public double getSpeed() {
		return speed;
	}
	/**
	 * Getter Of Team
	 * @return Team
	 */
	public int getTeam() {
		return team;
	}
	/**
	 * Getter Of zIndex
	 * @return zIndex
	 */
	public int getzIndex() {
		return zIndex;
	}
	/**
	 * Getter Of Owner
	 * @return Owner
	 */
	public GameObject getOwner() {
		return owner;
	}
	/**
	 * Setter Of Owner
	 * @param owner
	 */
	public void setOwner(GameObject owner) {
		this.owner = owner;
	}

}
