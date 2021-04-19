package character;

import config.Config;
import object.GameObject;
import update.Updatable;
import utility.Position;
import utility.ResourceManager;
import utility.Utility;
import utility.ResourceManager.ImageResource;
import weapon.Bullet;

public class Enemy extends Character implements Updatable {

	private boolean isDestroyed = false;

	public Enemy(ImageResource imageResource, int width, int height, int centerPosX, int centerPosY) {
		this(imageResource, width, height, new Position(centerPosX, centerPosY));
	}

	public Enemy(ImageResource imageResource, int width, int height, Position centerPos) {
		super(imageResource, width, height, centerPos);
	}

	public int getZ() {
		return Config.ZINDEX_MAIN_CHARACTER - 1;
	}

	@Override
	public boolean isDestroyed() {
		return isDestroyed;
	}

	public boolean isRemoveFromUpdate() {
		return isDestroyed;
	}

	public void update() {
		for (int i = 0; i <= ResourceManager.gameObjects.size() - 1; i++) {
			if (Utility.isObjectCollide(ResourceManager.gameObjects.get(i), this)
					&& ResourceManager.gameObjects.get(i).getClass() == Bullet.class) {
				System.out.println("Bullet hit (enemy)");
				ResourceManager.gameObjects.remove(this);
				isDestroyed = true;
			}
		}
	}
}
