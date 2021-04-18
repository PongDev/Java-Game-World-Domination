package weapon;

import java.util.Date;

import render.RenderManager;
import update.UpdateManager;
import utility.Position;
import utility.ResourceManager.ImageResource;

public class Gun extends Weapon {

	private ImageResource bulletImageResource;
	private int bulletWidth, bulletHeight, holderZIndex;
	private BulletProperties bulletProperties;
	private long lastAttack = 0;

	public Gun(ImageResource imageResource, int attackDamage, int attackSpeed, ImageResource bulletImageResource,
			int bulletWidth, int bulleteHeight, int holderZIndex) {
		super(imageResource, attackDamage, attackSpeed);
		this.bulletImageResource = bulletImageResource;
		this.bulletWidth = bulletWidth;
		this.bulletHeight = bulletHeight;
		this.holderZIndex = holderZIndex;
		setBulletProperties(new BulletProperties(bulletImageResource, bulletWidth, bulleteHeight, attackDamage,
				attackSpeed, holderZIndex + 1));
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
		}
	}

}