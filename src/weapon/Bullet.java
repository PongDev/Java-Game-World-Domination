package weapon;

import character.Enemy;
import config.Config;
import javafx.scene.canvas.GraphicsContext;
import logic.GameState;
import object.GameObject;
import update.Updatable;
import utility.Position;
import utility.ResourceManager;
import utility.ResourceManager.GameObjectResource;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import utility.Utility;

public class Bullet extends GameObject implements Updatable {

	private boolean isDestroyed = false;
	private BulletProperties bulletProperties;
	private double degree = 0;

	public Bullet(BulletProperties bulletProperties, int centerPosX, int centerPosY, double degree) {
		this(bulletProperties, new Position(centerPosX, centerPosY), degree);
	}

	public Bullet(BulletProperties bulletProperties, Position centerPos, double degree) {
		super(bulletProperties.getImageResource(), bulletProperties.getWidth(), bulletProperties.getHeight(),
				centerPos);
		this.bulletProperties = bulletProperties;
		this.degree = degree;

		Position delta = new Position((Config.TILE_W / 3) * Math.cos(Math.toRadians(degree)),
				-((Config.TILE_H / 3) * Math.sin(Math.toRadians(degree))));
		if (GameState.getGameMap().isPenetrable(this, delta.X, delta.Y)) {
			pos.X += delta.X;
			pos.Y += delta.Y;
		} else {
			isDestroyed = true;
		}
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
		return bulletProperties.getzIndex();
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void update() {

		Position delta = new Position(bulletProperties.getSpeed() * Math.cos(Math.toRadians(degree)),
				-(bulletProperties.getSpeed() * Math.sin(Math.toRadians(degree))));
		if (GameState.getGameMap().isPenetrable(this, delta.X, delta.Y)) {
			pos.X += delta.X;
			pos.Y += delta.Y;
		} else {
			isDestroyed = true;
		}
		for (GameObject g : ResourceManager.gameObjects) {
		     if (Utility.isObjectCollide(this,g) && g.getClass() == Enemy.class) {
		    	 System.out.println("(Bullet) hit enemy");
		    	 isDestroyed = true;
		     } 
		}
	}

	public boolean isRemoveFromUpdate() {
		return isDestroyed;
	}

	/*
	 * public boolean isCollide(double posX, double posY) { return posX >= pos.X &&
	 * posY >= pos.Y && posX <= (pos.X + width) && posY <= (pos.Y + height); }
	 * 
	 * public boolean isCollide(GameObject gameObject) { return
	 * isCollide(gameObject.getPos().X, gameObject.getPos().Y) ||
	 * isCollide(gameObject.getPos().X + gameObject.getWidth() - 1,
	 * gameObject.getPos().Y) || isCollide(gameObject.getPos().X,
	 * gameObject.getPos().Y + gameObject.getHeight() - 1) ||
	 * isCollide(gameObject.getPos().X + gameObject.getWidth() - 1,
	 * gameObject.getPos().Y + gameObject.getHeight() - 1); }
	 */

}
