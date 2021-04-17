package character;

import javafx.scene.canvas.GraphicsContext;
import logic.GameState;
import render.Renderable;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;

public abstract class Character implements Renderable {

	private ImageResource imageResource;
	private int posX, posY, centerPosX, centerPosY, width, height;

	public Character(ImageResource imageResource, int width, int height, int centerPosX, int centerPosY) {
		this.imageResource = imageResource;
		this.centerPosX = centerPosX;
		this.centerPosY = centerPosY;
		this.width = width;
		this.height = height;
		calculateCharacterPos();
	}

	public void calculateCharacterPos() {
		posX = centerPosX - (width / 2);
		posY = centerPosY - (height / 2);
	}

	public void render() {
		GraphicsContext gc = GameState.getGameMap().getGraphicsContext2D();

		gc.drawImage(ResourceManager.getImage(imageResource), -GameState.getGameMap().getMapPosX() + posX,
				-GameState.getGameMap().getMapPosY() + posY, width, height);
	}

	public boolean isAllowRender() {
		return true;
	}

	public boolean isDestroyed() {
		return false;
	}

	public abstract int getZ();

}
