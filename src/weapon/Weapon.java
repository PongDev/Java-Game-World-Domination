package weapon;

import utility.ResourceManager.ImageResource;

public class Weapon {

	private ImageResource imageResourse;
	private double attackDamage;
	private double attackSpeed;

	public Weapon(ImageResource imageResource, double attackDamage, double attackSpeed) {
		this.imageResourse = imageResource;
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
	}

	public ImageResource getImageResourse() {
		return imageResourse;
	}

}