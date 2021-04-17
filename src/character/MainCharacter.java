package character;

import config.Config;
import utility.Position;
import utility.ResourceManager.ImageResource;

public class MainCharacter extends Character {

	public MainCharacter(ImageResource imageResource, int width, int height, int centerPosX, int centerPosY) {
		super(imageResource, width, height, centerPosX, centerPosY);
	}

	public MainCharacter(ImageResource imageResource, int width, int height, Position pos) {
		super(imageResource, width, height, pos);
	}

	public int getZ() {
		return Config.ZINDEX_MAIN_CHARACTER;
	}

}
