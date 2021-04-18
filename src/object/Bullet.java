package object;

import javafx.scene.canvas.GraphicsContext;
import logic.GameState;
import update.Updatable;
import utility.Position;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;

public class Bullet extends GameObject implements Updatable {

	private boolean isDestroyed = false;
	private double speed = 0;
	private double degree = 0;
	private int zIndex = 0;

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

	public int getZ() {
		return zIndex;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void update() {
		if (GameState.getGameMap().isPenetrable(this, 0, 0)) {
			pos.X += speed * Math.cos(Math.toRadians(degree));
			pos.Y -= speed * Math.sin(Math.toRadians(degree));
		} else {
			isDestroyed = true;
		}
	}

	public boolean isRemoveFromUpdate() {
		return isDestroyed;
	}

	public void setBulletParameter(double speed, double degree, int zIndex) {
		this.speed = speed;
		this.degree = degree;
		this.zIndex = zIndex;
	}

	public boolean isCollide(double posX, double posY) {
		return posX >= pos.X && posY >= pos.Y && posX <= (pos.X + width) && posY <= (pos.Y + height);
	}

	public boolean isCollide(GameObject gameObject) {
		return isCollide(gameObject.getPos().X, gameObject.getPos().Y)
				|| isCollide(gameObject.getPos().X + gameObject.getWidth() - 1, gameObject.getPos().Y)
				|| isCollide(gameObject.getPos().X, gameObject.getPos().Y + gameObject.getHeight() - 1)
				|| isCollide(gameObject.getPos().X + gameObject.getWidth() - 1,
						gameObject.getPos().Y + gameObject.getHeight() - 1);
	}

}
