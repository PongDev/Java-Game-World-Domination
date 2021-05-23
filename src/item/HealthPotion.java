package item;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

/**
 * Subclass Of Potion: Health Potion
 */
public class HealthPotion extends Potion {

	/**
	 * Health Potion Main Constructor
	 */
	public HealthPotion() {
		super("Health Potion", ImageResource.HEALTH_POTION, 80, 20);
		this.itemOnBuy.add(new Pair<>(ItemResource.HEALTH_POTION, 1));
	}

}
