package weapon;

import utility.ResourceManager.ImageResource;

public class BulletProperties {

	private ImageResource imageResource;
	private int width;
	private int height;
	private int damage;
	private double speed;
	private int zIndex;

	public BulletProperties(ImageResource imageResource, int width, int height, int damage, double speed, int zIndex) {
		this.imageResource = imageResource;
		this.width = width;
		this.height = height;
		this.damage = damage;
		this.speed = speed;
		this.zIndex = zIndex;
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

	public int getzIndex() {
		return zIndex;
	}

}