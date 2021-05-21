package item;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public class PistolAmmo extends Item {

	public PistolAmmo() {
		super("Pistol Ammo", ImageResource.AMMO_PISTOL, 5);
		this.description = "Ammo For Pistol";
		this.itemOnBuy.add(new Pair<>(ItemResource.AMMO_PISTOL, 30));
	}

}
