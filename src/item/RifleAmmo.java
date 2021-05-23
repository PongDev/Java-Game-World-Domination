package item;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

/**
 * Subclass Of Item: Rifle Ammo
 */
public class RifleAmmo extends Item {

	/**
	 * Main Constructor Of Rifle Ammo
	 */
	public RifleAmmo() {
		super("Rifle Ammo", ImageResource.AMMO_RIFLE, 75);
		this.description = "Ammo For Rifle";
		this.itemOnBuy.add(new Pair<>(ItemResource.AMMO_RIFLE, 50));
	}

}
