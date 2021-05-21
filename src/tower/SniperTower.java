package tower;

import config.Config;
import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;
import weapon.Gun;

public class SniperTower extends Tower {

	public SniperTower(int row, int col, int team) {
		super(ImageResource.SNIPER_TOWER_BASE, ImageResource.SNIPER_TOWER_HEAD, "Sniper Tower", 30, 1, null, team, row,
				col);
		this.setWeapon(new Gun(null, 100, 0.25,
				team == Config.MAIN_CHARACTER_TEAM ? ImageResource.BULLET : ImageResource.ENEMY_BULLET, 10, 10, 10,
				team, team == Config.MAIN_CHARACTER_TEAM ? Config.ZINDEX_MAIN_CHARACTER : Config.ZINDEX_ENEMY, this));
		itemOnBuy.add(new Pair<>(ItemResource.SNIPER_TOWER, 1));
	}

	public String getDescription() {
		return "low attack speed, very high damage";
	}

	public ImageResource getImage() {
		return ImageResource.SNIPER_TOWER;
	}

	public int getCost() {
		return 40;
	}

	public boolean isAllowBuy() {
		return true;
	}

}
