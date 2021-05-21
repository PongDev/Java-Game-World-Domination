package tower;

import config.Config;
import utility.ResourceManager.ImageResource;
import weapon.Gun;

public class BarricadeTower extends Tower {

	public BarricadeTower(int row, int col, int team) {
		super(ImageResource.BARRIER_TOWER, null, "Barricade Tower", 30, 1,
				new Gun(null, 0, 0,
						team == Config.MAIN_CHARACTER_TEAM ? ImageResource.BULLET : ImageResource.ENEMY_BULLET, 0, 0, 0,
						team, team == Config.MAIN_CHARACTER_TEAM ? Config.ZINDEX_MAIN_CHARACTER : Config.ZINDEX_ENEMY),
				team, row, col);
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
