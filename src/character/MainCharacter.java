package character;

import config.Config;
import input.InputManager;
import input.Inputable;
import javafx.scene.input.KeyCode;
import logic.GameState;
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
		this.setWeapon(new Gun(ImageResource.GUN_AK47, 1, 1));
	}

	public int getZ() {
		return Config.ZINDEX_MAIN_CHARACTER;
	}

	public void processInput() {
		// W
		if (InputManager.isKeyPress(KeyCode.W)) {
			if (GameState.getGameMap().isCollide(this, 0, -getSpeed())) {
				pos.Y = ((int) (pos.Y / Config.TILE_H)) * Config.TILE_H;
			} else {
				pos.Y -= getSpeed();
			}
		}
		// A
		if (InputManager.isKeyPress(KeyCode.A)) {
			isTurnLeft = true;
			if (GameState.getGameMap().isCollide(this, -getSpeed(), 0)) {
				pos.X = ((int) (pos.X / Config.TILE_W)) * Config.TILE_W;
			} else {
				pos.X -= getSpeed();
			}
		}
		// S
		if (InputManager.isKeyPress(KeyCode.S)) {
			if (GameState.getGameMap().isCollide(this, 0, +getSpeed())) {
				pos.Y = (((int) ((pos.Y + height + getSpeed()) / Config.TILE_H)) * Config.TILE_H) - height;
			} else {
				pos.Y += getSpeed();
			}
		}
		// D
		if (InputManager.isKeyPress(KeyCode.D)) {
			isTurnLeft = false;
			if (GameState.getGameMap().isCollide(this, +getSpeed(), 0)) {
				pos.X = (((int) ((pos.X + width + getSpeed()) / Config.TILE_W)) * Config.TILE_W) - width;
			} else {
				pos.X += getSpeed();
			}
		}
	}

}
