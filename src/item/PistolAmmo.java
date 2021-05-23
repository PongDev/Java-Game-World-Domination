package item;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

/**
 * Subclass Of Item: Pistol Ammo
 */
public class PistolAmmo extends Item {

	/**
	 * Ammo Receive Amount When Buy
	 */
	private static final int receiveAmmoOnBuy = 30;

	/**
	 * Pistol Ammo Main Constructor
	 */
	public PistolAmmo() {
		super("Pistol Ammo", ImageResource.AMMO_PISTOL, 20);
		this.description = receiveAmmoOnBuy + " Ammo For Pistol";
		this.itemOnBuy.add(new Pair<>(ItemResource.AMMO_PISTOL, receiveAmmoOnBuy));
	}

}
