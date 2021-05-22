package item;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

/**
 * Subclass Of Item: Shotgun Ammo
 */
public class ShotgunAmmo extends Item {

	/**
	 * Main Constructor Of Shotgun Ammo
	 */
	public ShotgunAmmo() {
		super("Shotgun Ammo", ImageResource.AMMO_SHOTGUN, 10);
		this.description = "Ammo For Shotgun";
		this.itemOnBuy.add(new Pair<>(ItemResource.AMMO_SHOTGUN, 20));
	}

}
