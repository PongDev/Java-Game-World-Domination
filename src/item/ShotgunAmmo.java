package item;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public class ShotgunAmmo extends Item {

	public ShotgunAmmo() {
		super("Shotgun Ammo", ImageResource.AMMO_SHOTGUN, 10);
		this.description = "Ammo For Shotgun";
		this.itemOnBuy.add(new Pair<>(ItemResource.AMMO_SHOTGUN, 20));
	}

}
