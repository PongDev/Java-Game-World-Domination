package item;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

/**
 * Subclass Of Item: Shotgun Ammo
 */
public class ShotgunAmmo extends Item {

	/**
	 * Ammo Receive Amount When Buy
	 */
	private static final int receiveAmmoOnBuy = 20;

	/**
	 * Main Constructor Of Shotgun Ammo
	 */
	public ShotgunAmmo() {
		super("Shotgun Ammo", ImageResource.AMMO_SHOTGUN, 50);
		this.description = receiveAmmoOnBuy + " Ammo For Shotgun";
		this.itemOnBuy.add(new Pair<>(ItemResource.AMMO_SHOTGUN, receiveAmmoOnBuy));
	}

}
