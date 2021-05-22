package tower;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public class BarricadeTower extends Tower {

	public BarricadeTower(int row, int col, int team) {
		super(ImageResource.BARRIER_TOWER, null, "Barricade Tower", 30, 1, null, team, row, col);
		itemOnBuy.add(new Pair<>(ItemResource.BARRIER_TOWER, 1));
	}

	public String getDescription() {
		return "can block enemies bullet";
	}

	public ImageResource getIconImageResource() {
		return ImageResource.BARRIER_TOWER;
	}

	public int getCost() {
		return 40;
	}

	public boolean isAllowBuy() {
		return true;
	}

}
