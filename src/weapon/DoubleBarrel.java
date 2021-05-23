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

/**
 * Subclass Of Gun: DoubleBarrel Class
 */
public class DoubleBarrel extends Gun {

	/**
	 * Bullet Per Shot
	 */
	private int bulletPerShot;
	/**
	 * Bullet Dispersion
	 */
	private double bulletDispersion;

	/**
	 * DoubleBarrel Constructor
	 * 
	 * @param team         Bullet Team
	 * @param holderZIndex Owner Z Index
	 */
	public DoubleBarrel(int team, int holderZIndex) {
		super(ImageResource.GUN_DOUBLEBARREL, 20, 1, ImageResource.BULLET, 10, 10, 10, team, holderZIndex);
		this.bulletPerShot = 2;
		this.bulletDispersion = 7;
		this.name = "Double Barrel";
		this.description = String.format("%d-%d damage, %.1f second firerate", attackDamage,
				attackDamage * bulletPerShot, 1 / attackSpeed);
		this.cost = 200;
		this.attackSound = SoundResource.GUN_DOUBLEBARREL;
		this.attackSoundVolume = 0.5;
		this.itemResource = ItemResource.GUN_DOUBLEBARREL;
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
