package weapon;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;
import utility.ResourceManager.SoundResource;

/**
 * Subclass Of Gun: AK47 Class
 */
public class AK47 extends Gun {

	/**
	 * Ak47 Constructor
	 * @param team Bullet Team
	 * @param holderZIndex Owner Z Index
	 */
	public AK47(int team, int holderZIndex) {
		super(ImageResource.GUN_AK47, 30, 2, ImageResource.BULLET, 10, 10, 10, team, holderZIndex);
		this.name = "AK47";
		this.description = String.format("%d damage, %.1f second firerate", attackDamage, 1 / attackSpeed);
		this.cost = 70;
		this.attackSound = SoundResource.GUN_AK47;
		this.attackSoundVolume = 0.2;
		this.itemResource = ItemResource.GUN_AK47;
		this.bulletUse = ItemResource.AMMO_RIFLE;
		itemOnBuy.add(new Pair<>(this.itemResource, 1));
		itemOnBuy.add(new Pair<>(this.bulletUse, 90));
	}

}
