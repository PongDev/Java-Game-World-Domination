package character;

import config.Config;
import input.InputManager;
import logic.GameState;
import object.ObjectManager;
import render.RenderManager;
import update.Updatable;
import update.UpdateManager;
import utility.Position;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
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
		if (GameState.getSceneResource() == SceneResource.PLAYING) {
			ObjectManager.collideWithBullet(this);
			double degree = Math
					.toDegrees(Math.atan2(
							(-GameState.getGameMap().getMapPos().Y + pos.Y + (height / 2)) - (Config.SCREEN_H / 2),
							(Config.SCREEN_W / 2) - (-GameState.getGameMap().getMapPos().X + pos.X + (width / 2))))
					+ (Math.random() * Config.ENEMY_DISPERSION * (Math.random() <= 0.5 ? 1 : -1));
			weapon.attack(getCenterPos(), degree);
		}
	}
}
