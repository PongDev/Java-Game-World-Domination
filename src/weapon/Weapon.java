package weapon;

import item.Buyable;
import utility.Position;
import utility.ResourceManager.ImageResource;

public abstract class Weapon implements Buyable {

	private ImageResource imageResourse;
	protected int attackDamage;
	protected double attackSpeed;
	protected long lastAttack = 0;

	public Weapon(ImageResource imageResource, int attackDamage, double attackSpeed) {
		this.imageResourse = imageResource;
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
	}

	public ImageResource getImageResourse() {
		return imageResourse;
	}

	public long getLastAttack() {
		return lastAttack;
	}

	public void setLastAttack(long lastAttack) {
		this.lastAttack = lastAttack;
	}

	public abstract boolean attack(Position centerPos, double degree);

}