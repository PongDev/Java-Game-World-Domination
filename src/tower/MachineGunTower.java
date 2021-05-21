package tower;

import config.Config;
import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;
import weapon.Gun;

public class MachineGunTower extends Tower {

	public MachineGunTower(int row, int col, int team) {
		super(ImageResource.MACHINE_GUN_TOWER_BASE, ImageResource.MACHINE_GUN_TOWER_HEAD, "Machine Gun Tower", 30, 1,
				null, team, row, col);
		this.setWeapon(new Gun(null, 1, 5,
				team == Config.MAIN_CHARACTER_TEAM ? ImageResource.BULLET : ImageResource.ENEMY_BULLET, 5, 10, 10, team,
				team == Config.MAIN_CHARACTER_TEAM ? Config.ZINDEX_MAIN_CHARACTER : Config.ZINDEX_ENEMY, this));
		itemOnBuy.add(new Pair<>(ItemResource.MACHINE_GUN_TOWER, 1));
	}

	public String getDescription() {
		return "fast attack speed, medium damage";
	}

	public ImageResource getImage() {
		return ImageResource.MACHINE_GUN_TOWER;
	}

	public int getCost() {
		return 40;
	}

	public boolean isAllowBuy() {
		return true;
	}

}
