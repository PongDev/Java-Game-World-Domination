package weapon;

import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public class AK47 extends Gun {

	public AK47(int team, int holderZIndex) {
		super(ImageResource.GUN_AK47, 1, 2, ImageResource.BULLET, 10, 10, 10, team, holderZIndex);
		this.name = "AK47";
		this.description = String.format("%d damage, %.1f second firerate, %d ammo", attackDamage, 1 / attackSpeed,
				120);
		this.cost = 60;
		this.itemResource = ItemResource.GUN_AK47;
	}

}
