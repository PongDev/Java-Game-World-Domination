package character;

import config.Config;
import utility.Position;
import utility.ResourceManager.ImageResource;

public class Enemy extends Character {

	public Enemy(ImageResource imageResource, int width, int height, int centerPosX, int centerPosY) {
		this(imageResource, width, height, new Position(centerPosX, centerPosY));
	}

	public Enemy(ImageResource imageResource, int width, int height, Position centerPos) {
		super(imageResource, width, height, centerPos);
	}

	public int getZ() {
		return Config.ZINDEX_MAIN_CHARACTER - 1;
	}
}
