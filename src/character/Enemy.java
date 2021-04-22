package character;

import config.Config;
import object.ObjectManager;
import render.RenderManager;
import update.Updatable;
import update.UpdateManager;
import utility.Position;
import utility.ResourceManager.ImageResource;
import weapon.Weapon;

public class Enemy extends Character implements Updatable {

	public Enemy(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense, int speed,
			Weapon weapon, int team, int centerPosX, int centerPosY) {
		this(imageResource, width, height, name, maxHealth, defense, speed, weapon, team,
				new Position(centerPosX, centerPosY));
	}

	public Enemy(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense, int speed,
			Weapon weapon, int team, Position centerPos) {
		super(imageResource, width, height, name, maxHealth, defense, speed, weapon, team, centerPos);
		UpdateManager.add(this);
		RenderManager.add(this);
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
