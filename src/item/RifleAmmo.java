package item;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

/**
 * Subclass Of Item: Rifle Ammo
 */
public class RifleAmmo extends Item {

	/**
	 * Ammo Receive Amount When Buy
	 */
	private static final int receiveAmmoOnBuy = 50;

	/**
	 * Main Constructor Of Rifle Ammo
	 */
	public RifleAmmo() {
		super("Rifle Ammo", ImageResource.AMMO_RIFLE, 75);
		this.description = receiveAmmoOnBuy + " Ammo For Rifle";
		this.itemOnBuy.add(new Pair<>(ItemResource.AMMO_RIFLE, receiveAmmoOnBuy));
	}

}
