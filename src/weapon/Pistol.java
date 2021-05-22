package weapon;

import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SoundResource;

public class Pistol extends Gun {

	public Pistol(int team, int holderZIndex) {
		super(ImageResource.GUN_PISTOL, 1, 1, ImageResource.BULLET, 10, 10, 10, team, holderZIndex);
		this.name = "Pistol";
		this.description = String.format("%d damage, %.1f second firerate", attackDamage, 1 / attackSpeed);
		this.cost = 0;
		this.attackSound = SoundResource.GUN_PISTOL;
		this.attackSoundVolume = 0.5;
		this.bulletUse = null;
	}

}
