package tower;

import config.Config;
import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;
import weapon.Gun;

/**
 * Subclass Of Tower: Sniper Tower
 */
public class SniperTower extends Tower {

	/**
	 * Machine Gun Tower Main Constructor
	 */
	public SniperTower(int row, int col, int team) {
		super(ImageResource.SNIPER_TOWER_BASE, ImageResource.SNIPER_TOWER_HEAD, "Sniper Tower", 300, 1, null, team, row,
				col);
		this.setWeapon(new Gun(null, 100, 0.5,
				team == Config.MAIN_CHARACTER_TEAM ? ImageResource.BULLET : ImageResource.ENEMY_BULLET, 25, 10, 10,
				team, team == Config.MAIN_CHARACTER_TEAM ? Config.ZINDEX_MAIN_CHARACTER : Config.ZINDEX_ENEMY, this));
		itemOnBuy.add(new Pair<>(ItemResource.SNIPER_TOWER, 1));
	}

	/**
	 * Get Machine Gun Tower Description
	 */
	public String getDescription() {
		return String.format("%d damage, %.1f second firerate, hp %d", this.getWeapon().getAttackDamage(),
				1 / this.getWeapon().getAttackSpeed(), getMaxHealth());
	}

	/**
	 * Get Machine Gun Tower Icon ImageResource
	 */
	public ImageResource getIconImageResource() {
		return ImageResource.SNIPER_TOWER;
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
