package tower;

import utility.ResourceManager.ImageResource;

public class BarricadeTower extends Tower {

	public BarricadeTower(int row, int col, int team) {
		super(ImageResource.BARRIER_TOWER, null, "Barricade Tower", 30, 1, null, team, row, col);
	}

	public String getDescription() {
		return "can block enemies bullet";
	}

	public ImageResource getImage() {
		return ImageResource.BARRIER_TOWER;
	}

	public int getCost() {
		return 40;
	}

	public boolean isAllowBuy() {
		return true;
	}

}
