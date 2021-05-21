package item;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public class HealthPotion extends Potion {

	public HealthPotion() {
		super("Health Potion", ImageResource.HEALTH_POTION, 20, 2);
		this.itemOnBuy.add(new Pair<>(ItemResource.HEALTH_POTION, 1));
	}

}
