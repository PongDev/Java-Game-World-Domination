package weapon;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;
import utility.ResourceManager.SoundResource;

public class AK47 extends Gun {

	public AK47(int team, int holderZIndex) {
		super(ImageResource.GUN_AK47, 1, 2, ImageResource.BULLET, 10, 10, 10, team, holderZIndex);
		this.name = "AK47";
		this.description = String.format("%d damage, %.1f second firerate", attackDamage, 1 / attackSpeed);
		this.cost = 60;
		this.attackSound = SoundResource.GUN_AK47;
		this.attackSoundVolume = 0.5;
		this.itemResource = ItemResource.GUN_AK47;
		this.bulletUse = ItemResource.AMMO_RIFLE;
		itemOnBuy.add(new Pair<>(this.itemResource, 1));
		itemOnBuy.add(new Pair<>(this.bulletUse, 90));
	}

}
