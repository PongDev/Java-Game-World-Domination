package weapon;

import object.GameObject;
import utility.ResourceManager.ImageResource;

public class BulletProperties {

	private ImageResource imageResource;
	private int width;
	private int height;
	private int damage;
	private double speed;
	private int team;
	private int zIndex;
	private GameObject owner;

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

	public ImageResource getImageResource() {
		return imageResource;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getDamage() {
		return damage;
	}

	public double getSpeed() {
		return speed;
	}

	public int getTeam() {
		return team;
	}

	public int getzIndex() {
		return zIndex;
	}

	public GameObject getOwner() {
		return owner;
	}

	public void setOwner(GameObject owner) {
		this.owner = owner;
	}

}
