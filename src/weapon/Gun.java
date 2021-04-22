package weapon;

import java.util.Date;

import object.ObjectManager;
import render.RenderManager;
import update.UpdateManager;
import utility.Position;
import utility.ResourceManager.ImageResource;

public class Gun extends Weapon {

	private BulletProperties bulletProperties;
	private long lastAttack = 0;

	public Gun(ImageResource imageResource, int attackDamage, int attackSpeed, ImageResource bulletImageResource,
			int bulletSpeed, int bulletWidth, int bulleteHeight, int team, int holderZIndex) {
		super(imageResource, attackDamage, attackSpeed);
		setBulletProperties(new BulletProperties(bulletImageResource, bulletWidth, bulleteHeight, attackDamage,
				bulletSpeed, team, holderZIndex + 1));
	}

	public BulletProperties getBulletProperties() {
		return bulletProperties;
	}

	public void setBulletProperties(BulletProperties bulletProperties) {
		this.bulletProperties = bulletProperties;
	}

	public void attack(Position centerPos, double degree) {
		if ((new Date()).getTime() - lastAttack >= 1000 / attackSpeed) {
			lastAttack = (new Date()).getTime();
			Bullet bullet = new Bullet(bulletProperties, centerPos, degree);
			UpdateManager.add(bullet);
			RenderManager.add(bullet);
			ObjectManager.addBullet(bullet);
		}
	}

}
