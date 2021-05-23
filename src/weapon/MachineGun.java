package weapon;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;
import utility.ResourceManager.SoundResource;

/**
 * Subclass Of Gun: MachineGun Class
 */
public class MachineGun extends Gun {

	/**
	 * MachineGun Constructor
	 * 
	 * @param team         Bullet Team
	 * @param holderZIndex Owner Z Index
	 */
	public MachineGun(int team, int holderZIndex) {
		super(ImageResource.GUN_MACHINEGUN, 30, 5, ImageResource.BULLET, 10, 10, 10, team, holderZIndex);
		this.name = "Machine Gun";
		this.description = String.format("%d damage, %.1f second firerate", attackDamage, 1 / attackSpeed);
		this.cost = 600;
		this.attackSound = SoundResource.GUN_MACHINEGUN;
		this.attackSoundVolume = 0.5;
		this.itemResource = ItemResource.GUN_MACHINEGUN;
		this.bulletUse = ItemResource.AMMO_RIFLE;
		itemOnBuy.add(new Pair<>(this.itemResource, 1));
		itemOnBuy.add(new Pair<>(this.bulletUse, 150));
	}

}
