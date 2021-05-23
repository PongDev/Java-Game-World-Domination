package tower;

import config.Config;
import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;
import weapon.Gun;

/**
 * Subclass Of Tower: Machine Gun Tower
 */
public class MachineGunTower extends Tower {

	/**
	 * Machine Gun Tower Main Constructor
	 */
	public MachineGunTower(int row, int col, int team) {
		super(ImageResource.MACHINE_GUN_TOWER_BASE, ImageResource.MACHINE_GUN_TOWER_HEAD, "Machine Gun Tower", 400, 1,
				null, team, row, col);
		this.setWeapon(new Gun(null, 10, 5,
				team == Config.MAIN_CHARACTER_TEAM ? ImageResource.BULLET : ImageResource.ENEMY_BULLET, 7, 10, 10, team,
				team == Config.MAIN_CHARACTER_TEAM ? Config.ZINDEX_MAIN_CHARACTER : Config.ZINDEX_ENEMY, this));
		itemOnBuy.add(new Pair<>(ItemResource.MACHINE_GUN_TOWER, 1));
	}

	/**
	 * Get Machine Gun Tower Description
	 */
	public String getDescription() {
		return String.format("fast attack speed, medium damage, max hp: %d", getMaxHealth());
	}

	/**
	 * Get Machine Gun Tower Icon ImageResource
	 */
	public ImageResource getIconImageResource() {
		return ImageResource.MACHINE_GUN_TOWER;
	}

	/**
	 * Get Machine Gun Tower Cost
	 */
	public int getCost() {
		return 200;
	}

	/**
	 * Is Machine Gun Tower Allow Buy
	 */
	public boolean isAllowBuy() {
		return true;
	}

}
