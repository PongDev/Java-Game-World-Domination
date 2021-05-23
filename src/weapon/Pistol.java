package weapon;

import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SoundResource;

/**
 * Subclass Of Gun: Pistol Class
 */
public class Pistol extends Gun {

	/**
	 * Pistol Constructor
	 * 
	 * @param team         Bullet Team
	 * @param holderZIndex Owner Z Index
	 */
	public Pistol(int team, int holderZIndex) {
		super(ImageResource.GUN_PISTOL, 20, 1, ImageResource.BULLET, 10, 10, 10, team, holderZIndex);
		this.name = "Pistol";
		this.description = String.format("%d damage, %.1f second firerate", attackDamage, 1 / attackSpeed);
		this.cost = 0;
		this.attackSound = SoundResource.GUN_PISTOL;
		this.attackSoundVolume = 0.5;
		this.bulletUse = null;
	}

}
