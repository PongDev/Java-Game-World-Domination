package weapon;

import utility.ResourceManager.ImageResource;

public class Pistol extends Gun {

	public Pistol(int team, int holderZIndex) {
		super(ImageResource.GUN_PISTOL, 1, 1, ImageResource.BULLET, 10, 10, 10, team, holderZIndex);
		this.name = "Pistol";
		this.description = String.format("%d damage, %.1f second firerate, %d ammo", attackDamage, 1 / attackSpeed, 12);
		this.cost = 0;
		this.bulletUse = null;
	}

}
