package character;

import config.Config;
import object.ObjectManager;
import update.Updatable;
import utility.Position;
import utility.ResourceManager.ImageResource;
import weapon.Weapon;

public class Enemy extends Character implements Updatable {

	public Enemy(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense, int speed,
			Weapon weapon, int centerPosX, int centerPosY) {
		this(imageResource, width, height, name, maxHealth, defense, speed, weapon,
				new Position(centerPosX, centerPosY));
	}

	public Enemy(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense, int speed,
			Weapon weapon, Position centerPos) {
		super(imageResource, width, height, name, maxHealth, defense, speed, weapon, centerPos);
	}

	public int getZ() {
		return Config.ZINDEX_MAIN_CHARACTER - 1;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public boolean isRemoveFromUpdate() {
		return isDestroyed;
	}

	public void update() {
		ObjectManager.collideWithBullet(this);
	}
}
