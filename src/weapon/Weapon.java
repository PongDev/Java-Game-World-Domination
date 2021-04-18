package weapon;

import object.GameObject;
import utility.Position;
import utility.ResourceManager.ImageResource;

public class Weapon {

	private ImageResource imageResourse;
	private int attackDamage;
	private int attackSpeed;

	public Weapon(ImageResource imageResource, int attackDamage, int attackSpeed) {
		this.imageResourse = imageResource;
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
	}

}