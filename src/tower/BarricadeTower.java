package tower;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

/**
 * Subclass Of Tower: Barricade Tower - Wall With Health
 */
public class BarricadeTower extends Tower {

	/**
	 * Barricade Tower Main Constructor
	 */
	public BarricadeTower(int row, int col, int team) {
		super(ImageResource.BARRIER_TOWER, null, "Barricade Tower", 30, 1, null, team, row, col);
		itemOnBuy.add(new Pair<>(ItemResource.BARRIER_TOWER, 1));
	}

	/**
	 * Get Barricade Tower Description
	 */
	public String getDescription() {
		return "can block enemies bullet";
	}

	/**
	 * Get Barricade Tower Icon ImageResource
	 */
	public ImageResource getIconImageResource() {
		return ImageResource.BARRIER_TOWER;
	}

	/**
	 * Get Barricade Tower Cost
	 */
	public int getCost() {
		return 40;
	}

	/**
	 * Is Barricade Tower Allow Buy
	 */
	public boolean isAllowBuy() {
		return true;
	}

}
