package object;

import javafx.scene.canvas.GraphicsContext;
import logic.GameState;
import utility.Position;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;

public abstract class Bullet extends GameObject {

	public Bullet(ImageResource imageResource, int width, int height, int centerPosX, int centerPosY) {
		this(imageResource, width, height, new Position(centerPosX, centerPosY));
	}

	public Bullet(ImageResource imageResource, int width, int height, Position centerPos) {
		super(imageResource, width, height, centerPos);
	}

	public void render() {
		GraphicsContext gc = GameState.getGameMap().getGraphicsContext2D();

		gc.drawImage(ResourceManager.getImage(imageResource), -GameState.getGameMap().getMapPos().X + pos.X,
				-GameState.getGameMap().getMapPos().Y + pos.Y, width, height);
	}

	public boolean isAllowRender() {
		return GameState.getSceneResource() == SceneResource.PLAYING;
	}

	public abstract int getZ();

	public boolean isDestroyed() {
		return false;
	}

}
