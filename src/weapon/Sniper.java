package weapon;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;
import utility.ResourceManager.SoundResource;

/**
 * Subclass Of Gun: Sniper Class
 */
public class Sniper extends Gun {

	/**
	 * Sniper Constructor
	 * 
	 * @param team         Bullet Team
	 * @param holderZIndex Owner Z Index
	 */
	public Sniper(int team, int holderZIndex) {
		super(ImageResource.GUN_SNIPER, 120, 0.75, ImageResource.BULLET, 20, 10, 10, team, holderZIndex);
		this.name = "Sniper";
		this.description = String.format("%d damage, %.1f second firerate", attackDamage, 1 / attackSpeed);
		this.cost = 900;
		this.attackSound = SoundResource.GUN_SNIPER;
		this.attackSoundVolume = 0.5;
		this.itemResource = ItemResource.GUN_SNIPER;
		this.bulletUse = ItemResource.AMMO_RIFLE;
		itemOnBuy.add(new Pair<>(this.itemResource, 1));
		itemOnBuy.add(new Pair<>(this.bulletUse, 30));
	}

}
