package weapon;

import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public class Sniper extends Gun {

	public Sniper(int team, int holderZIndex) {
		super(ImageResource.GUN_SNIPER, 5, 1, ImageResource.BULLET, 10, 10, 10, team, holderZIndex);
		this.name = "Sniper";
		this.description = String.format("%d damage, %.1f second firerate, %d ammo", attackDamage, 1 / attackSpeed, 30);
		this.cost = 75;
		this.itemResource = ItemResource.GUN_SNIPER;
	}

}
