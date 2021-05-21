package weapon;

import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public class Sniper extends Gun {

	public Sniper(int team, int holderZIndex) {
		super(ImageResource.GUN_SNIPER, 12, 1, ImageResource.BULLET, 10, 10, 10, team, holderZIndex);
		this.name = "Sniper";
		this.description = "12 damage, 1.0 second firerate, 30 ammo";
		this.cost = 75;
		this.itemResource = ItemResource.GUN_SNIPER;
	}

}
