package character;

import config.Config;
import input.InputManager;
import input.Inputable;
import javafx.scene.input.KeyCode;
import logic.GameState;
import weapon.Bullet;
import weapon.BulletProperties;
import render.RenderManager;
import update.UpdateManager;
import utility.Position;
import utility.ResourceManager.ImageResource;
import weapon.Gun;

public class MainCharacter extends Character implements Inputable {

	public MainCharacter(ImageResource imageResource, int width, int height, int centerPosX, int centerPosY) {
		this(imageResource, width, height, new Position(centerPosX, centerPosY));
	}

	public MainCharacter(ImageResource imageResource, int width, int height, Position centerPos) {
		super(imageResource, width, height, centerPos);
		InputManager.addInputableObject(this);
		this.setSpeed(Config.MAIN_CHARACTER_INITIAL_SPD);
		this.setWeapon(
				new Gun(ImageResource.GUN_AK47, 1, 5, ImageResource.BULLET, 10, 10, 10, Config.ZINDEX_MAIN_CHARACTER));
	}

	public int getZ() {
		return Config.ZINDEX_MAIN_CHARACTER;
	}

	public void processInput() {
		// W
		if (InputManager.isKeyPress(KeyCode.W)) {
			if (GameState.getGameMap().isWalkable(this, 0, -getSpeed())) {
				pos.Y -= getSpeed();
			} else {
				pos.Y = ((int) (pos.Y / Config.TILE_H)) * Config.TILE_H;
			}
		}
		// A
		if (InputManager.isKeyPress(KeyCode.A)) {
			isTurnLeft = true;
			if (GameState.getGameMap().isWalkable(this, -getSpeed(), 0)) {
				pos.X -= getSpeed();
			} else {
				pos.X = ((int) (pos.X / Config.TILE_W)) * Config.TILE_W;
			}
		}
		// S
		if (InputManager.isKeyPress(KeyCode.S)) {
			if (GameState.getGameMap().isWalkable(this, 0, +getSpeed())) {
				pos.Y += getSpeed();
			} else {
				pos.Y = (((int) ((pos.Y + height + getSpeed()) / Config.TILE_H)) * Config.TILE_H) - height;
			}
		}
		// D
		if (InputManager.isKeyPress(KeyCode.D)) {
			isTurnLeft = false;
			if (GameState.getGameMap().isWalkable(this, +getSpeed(), 0)) {
				pos.X += getSpeed();
			} else {
				pos.X = (((int) ((pos.X + width + getSpeed()) / Config.TILE_W)) * Config.TILE_W) - width;
			}
		}
		// Mouse Click
		if (InputManager.isLeftMousePress()) {
			double degree = Math.toDegrees(Math.atan2((Config.SCREEN_H / 2) - InputManager.getMousePos().Y,
					InputManager.getMousePos().X - (Config.SCREEN_W / 2)));
			weapon.attack(getCenterPos(), degree);
		}
		isTurnLeft = (InputManager.getMousePos().X < Config.SCREEN_W / 2);

	}

}
