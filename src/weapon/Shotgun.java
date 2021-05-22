package weapon;

import java.util.Date;

import javafx.util.Pair;
import object.ObjectManager;
import render.RenderManager;
import sound.SoundManager;
import update.UpdateManager;
import utility.Position;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;
import utility.ResourceManager.SoundResource;

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
		this.attackSound = SoundResource.GUN_SHOTGUN;
		this.attackSoundVolume = 0.5;
		this.itemResource = ItemResource.GUN_SHOTGUN;
		this.bulletUse = ItemResource.AMMO_SHOTGUN;
		itemOnBuy.add(new Pair<>(this.itemResource, 1));
		itemOnBuy.add(new Pair<>(this.bulletUse, 40));
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
			if (attackSound != null) {
				SoundManager.playSoundEffect(attackSound, attackSoundVolume);
			}
			return true;
		} else {
			return false;
		}
	}

}
