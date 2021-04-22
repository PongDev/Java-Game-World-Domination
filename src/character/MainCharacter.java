package character;

import config.Config;
import input.InputManager;
import input.Inputable;
import javafx.scene.input.KeyCode;
import logic.GameState;
import object.ObjectManager;
import render.RenderManager;
import update.Updatable;
import update.UpdateManager;
import utility.Position;
import utility.ResourceManager.ImageResource;
import weapon.Weapon;

public class MainCharacter extends Character implements Inputable, Updatable {

	public MainCharacter(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense,
			int speed, Weapon weapon, int team, int centerPosX, int centerPosY) {
		this(imageResource, width, height, name, maxHealth, defense, speed, weapon, team,
				new Position(centerPosX, centerPosY));
	}

	public MainCharacter(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense,
			int speed, Weapon weapon, int team, Position centerPos) {
		super(imageResource, width, height, name, maxHealth, defense, speed, weapon, team, centerPos);
		InputManager.addInputableObject(this);
		UpdateManager.add(this);
		RenderManager.add(this);
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

	public void update() {
		ObjectManager.collideWithBullet(this);
	}

	public boolean isRemoveFromUpdate() {
		return false;
	}

}
