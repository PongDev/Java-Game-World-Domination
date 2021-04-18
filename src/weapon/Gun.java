package weapon;

import render.RenderManager;
import update.UpdateManager;
import utility.Position;
import utility.ResourceManager.ImageResource;

public class Gun extends Weapon {

	private BulletProperties bulletProperties;

	public Gun(ImageResource imageResource, int attackDamage, int attackSpeed) {
		super(imageResource, attackDamage, attackSpeed);
	}

	public Gun(ImageResource imageResource, int attackDamage, int attackSpeed, BulletProperties bulletProperties) {
		this(imageResource, attackDamage, attackSpeed);
		setBulletProperties(bulletProperties);
	}

	public BulletProperties getBulletProperties() {
		return bulletProperties;
	}

	public void setBulletProperties(BulletProperties bulletProperties) {
		this.bulletProperties = bulletProperties;
	}

	public void attack(Position centerPos, double degree) {
		Bullet bullet = new Bullet(bulletProperties, centerPos, degree);

		UpdateManager.add(bullet);
		RenderManager.add(bullet);
	}

}
