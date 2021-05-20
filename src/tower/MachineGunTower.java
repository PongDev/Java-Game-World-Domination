package tower;

import config.Config;
import utility.ResourceManager.ImageResource;
import weapon.Gun;
import weapon.Weapon;

public class MachineGunTower extends Tower {

	public MachineGunTower(int row, int col, int team) {
		super(ImageResource.MACHINE_GUN_TOWER, "Machine Gun Tower", 30, 1,
				new Gun(null, 1, 5,
						team == Config.MAIN_CHARACTER_TEAM ? ImageResource.BULLET : ImageResource.ENEMY_BULLET, 5, 10,
						10, team, Config.ZINDEX_MAIN_CHARACTER),
				team, row, col);
	}

}
