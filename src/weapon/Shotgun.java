package weapon;

import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public class Shotgun extends Gun {

	public Shotgun(int team, int holderZIndex) {
		super(ImageResource.GUN_SHOTGUN, 3, 1, ImageResource.BULLET, 10, 10, 10, team, holderZIndex);
		this.name = "Shotgun";
		this.description = "3-9 damage, 1.0 second firerate, 20 ammo";
		this.cost = 50;
		this.itemResource = ItemResource.GUN_SHOTGUN;
	}

}
