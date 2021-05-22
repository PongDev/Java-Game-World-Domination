package weapon;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;
import utility.ResourceManager.SoundResource;

public class Sniper extends Gun {

	public Sniper(int team, int holderZIndex) {
		super(ImageResource.GUN_SNIPER, 120, 0.5, ImageResource.BULLET, 15, 10, 10, team, holderZIndex);
		this.name = "Sniper";
		this.description = String.format("%d damage, %.1f second firerate", attackDamage, 1 / attackSpeed);
		this.cost = 150;
		this.attackSound = SoundResource.GUN_SNIPER;
		this.attackSoundVolume = 0.5;
		this.itemResource = ItemResource.GUN_SNIPER;
		this.bulletUse = ItemResource.AMMO_RIFLE;
		itemOnBuy.add(new Pair<>(this.itemResource, 1));
		itemOnBuy.add(new Pair<>(this.bulletUse, 30));
	}

}
