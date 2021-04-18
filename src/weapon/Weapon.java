package weapon;

import utility.Position;
import utility.ResourceManager.ImageResource;

public abstract class Weapon {

	private ImageResource imageResourse;
	protected int attackDamage;
	protected double attackSpeed;

	public Weapon(ImageResource imageResource, int attackDamage, double attackSpeed) {
		this.imageResourse = imageResource;
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
	}

	public ImageResource getImageResourse() {
		return imageResourse;
	}

	public abstract void attack(Position centerPos, double degree);

}