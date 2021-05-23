package item;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

/**
 * Subclass Of Item: Pistol Ammo
 */
public class PistolAmmo extends Item {

	/**
	 * Pistol Ammo Main Constructor
	 */
	public PistolAmmo() {
		super("Pistol Ammo", ImageResource.AMMO_PISTOL, 20);
		this.description = "Ammo For Pistol";
		this.itemOnBuy.add(new Pair<>(ItemResource.AMMO_PISTOL, 30));
	}

}
