package weapon;

import java.util.Date;

import object.ObjectManager;
import render.RenderManager;
import update.UpdateManager;
import utility.Position;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public class Shotgun extends Gun {

	private int bulletPerShot;
	private double bulletDispersion;

	public Shotgun(int team, int holderZIndex) {
		super(ImageResource.GUN_SHOTGUN, 1, 1, ImageResource.BULLET, 10, 10, 10, team, holderZIndex);
		this.bulletPerShot = 3;
		this.bulletDispersion = 30;
		this.name = "Shotgun";
		this.description = String.format("%d-%d damage, %.1f second firerate, %d ammo", attackDamage,
				attackDamage * bulletPerShot, 1 / attackSpeed, 20);
		this.cost = 50;
		this.itemResource = ItemResource.GUN_SHOTGUN;
	}

	public boolean attack(Position centerPos, double degree) {
		if ((new Date()).getTime() - lastAttack >= 1000 / attackSpeed) {
			lastAttack = (new Date()).getTime();

			for (int i = 0; i < bulletPerShot; i++) {
				Bullet bullet = new Bullet(this.getBulletProperties(), centerPos,
						degree + (Math.random() * this.bulletDispersion) - (this.bulletDispersion / 2));
				UpdateManager.add(bullet);
				RenderManager.add(bullet);
				ObjectManager.addBullet(bullet);
			}
			return true;
		} else {
			return false;
		}
	}

}
