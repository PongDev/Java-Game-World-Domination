package character;

import config.Config;
import input.Inputable;
import logic.GameState;
import utility.Position;
import utility.ResourceManager.ImageResource;

public class MainCharacter extends Character implements Inputable {

	public MainCharacter(ImageResource imageResource, int width, int height, int centerPosX, int centerPosY) {
		super(imageResource, width, height, centerPosX, centerPosY);
	}

	public MainCharacter(ImageResource imageResource, int width, int height, Position pos) {
		super(imageResource, width, height, pos);
	}

	public int getZ() {
		return Config.ZINDEX_MAIN_CHARACTER;
	}

	public void processInput() {
		// W
		if (true) {
			if (GameState.getGameMap().isCollide(this, 0, -getSpeed())) {
				pos.Y = ((int) (pos.Y / Config.TILE_H)) * Config.TILE_H;
			} else {
				pos.Y -= getSpeed();
			}
		}
		// A
		if (true) {
			if (GameState.getGameMap().isCollide(this, -getSpeed(), 0)) {
				pos.X = ((int) (pos.X / Config.TILE_W)) * Config.TILE_W;
			} else {
				pos.X -= getSpeed();
			}
		}
		// S
		if (true) {
			if (GameState.getGameMap().isCollide(this, 0, +getSpeed())) {
				pos.Y = (((int) (pos.Y / Config.TILE_H)) * (Config.TILE_H + 1)) - height;
			} else {
				pos.Y += getSpeed();
			}
		}
		// D
		if (true) {
			if (GameState.getGameMap().isCollide(this, +getSpeed(), 0)) {
				pos.X = (((int) (pos.X / Config.TILE_W)) * (Config.TILE_W + 1)) - width;
			} else {
				pos.X += getSpeed();
			}
		}
	}

}
