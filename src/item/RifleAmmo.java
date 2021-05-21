package item;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public class RifleAmmo extends Item {

	public RifleAmmo() {
		super("Rifle Ammo", ImageResource.AMMO_RIFLE, 15);
		this.description = "Ammo For Rifle";
		this.itemOnBuy.add(new Pair<>(ItemResource.AMMO_RIFLE, 50));
	}

}
